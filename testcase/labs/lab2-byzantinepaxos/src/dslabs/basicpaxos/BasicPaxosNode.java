package dslabs.basicpaxos;

import com.google.common.collect.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;

import dslabs.framework.Address;
import dslabs.framework.Client;
import dslabs.framework.Application;
import dslabs.framework.Node;
import dslabs.framework.Command;
import dslabs.framework.Result;
import dslabs.framework.Message;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Data;

public class BasicPaxosNode extends Node implements Client {
    private final Address[] servers;
    private MutablePair<Integer, Integer> maxBal;
    private MutablePair<Integer, Integer> maxVBal;
    private Integer maxVVal;
    private boolean decided;
    private Integer proposal;
    private Integer addressID;
    private Integer quorum;

    private final HashMap<Integer, BasicPaxosPromise> receivedPromise = new HashMap<>();
    private final HashMap<Integer, BasicPaxosAccepted> receivedAccepted = new HashMap<>();

    public BasicPaxosNode(Address address, Address[] servers) {
        super(address);
        this.servers = servers;
        this.maxBal = new MutablePair<>(-1, address.toString().hashCode());
        this.maxVBal = new MutablePair<>(-1, address.toString().hashCode());
        this.maxVVal = null;
        this.decided = false;
        this.proposal = null;
        this.addressID = address.toString().hashCode();
        this.quorum = servers.length / 2 + 1;
    }

    @Override
    public void init() {
        // Your code here...
    }

    private void proposal() {
        BasicPaxosPrepare prepare = new BasicPaxosPrepare(new ImmutablePair<Integer, Integer>(maxBal.getLeft() + 1, addressID));
        receivedPromise.clear();
        myBroadcast(prepare, servers);
    }

    private void handleBasicPaxosPrepare(BasicPaxosPrepare m, Address sender) {
        if (compare(m.bal(), maxBal) > 0) {
            updateMaxBal(m.bal());
            send(new BasicPaxosPromise(maxBal, maxVBal, maxVVal, addressID), sender);
        }
    }

    private void handleBasicPaxosPromise(BasicPaxosPromise m, Address sender) {
        if (!maxBal.equals(m.maxBal())) {
            return;
        }
        receivedPromise.put(m.addressID(), m);
        if (receivedPromise.size() >= quorum) {
            Pair<Integer, Integer> maxNum = new ImmutablePair<>(-2, addressID);
            Integer maxVal = null;
            for (Entry<Integer, BasicPaxosPromise> entry : receivedPromise.entrySet()) {
                if (compare(entry.getValue().maxVBal(), maxNum) > 0) {
                    maxVal = entry.getValue().maxVVal();
                    maxNum = entry.getValue().maxVBal();
                }
            }
            if (maxVal == null) {
                receivedPromise.clear();
                myBroadcast(new BasicPaxosAccept(maxBal, proposal), servers);   
            } else {
                receivedPromise.clear();
                myBroadcast(new BasicPaxosAccept(maxBal, maxVal), servers);
            }
        }
    }

    private void handleBasicPaxosAccept(BasicPaxosAccept m, Address sender) {
        if (compare(m.bal(), maxBal) >= 0) {
            updateMaxBal(m.bal());
            updateMaxVBal(m.bal());
            updateMaxVVal(m.maxVVal());
            send(new BasicPaxosAccepted(maxVBal, maxVVal, addressID), sender);
        }
    }

    private synchronized void handleBasicPaxosAccepted(BasicPaxosAccepted m, Address sender) {
        if (!maxBal.equals(m.maxVBal())) {
            return;
        }
        receivedAccepted.put(m.addressID(), m);
        if (receivedAccepted.size() >= quorum) {
            receivedAccepted.clear();
            decided = true;
            notify();
        }
    }

    @Override
    public synchronized void sendCommand(Command command) {
        if (command instanceof Proposal) {
            proposal = ((Proposal)command).value();
            proposal();
        }
    }

    @Override
    public synchronized boolean hasResult() {
        return decided;
    }

    @Override
    public synchronized Result getResult() throws InterruptedException {
        while (decided == false) {
            wait();
        }

        return new Consensus(maxVVal);
    }


    //UTILS---------------------------------------------------
    private int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        if (a.getLeft() > b.getLeft()) {
            return 1;
        } else if (a.getLeft() < b.getLeft()) {
            return -1;
        } else {
            if (a.getRight() > b.getRight()) {
                return 1;
            } else if (a.getRight() < b.getRight()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void updateMaxBal(Pair<Integer, Integer> newMaxBal) {
        maxBal.setLeft(newMaxBal.getLeft());
        maxBal.setRight(newMaxBal.getRight());
    }

    private void updateMaxVBal(Pair<Integer, Integer> newMaxVBal) {
        maxVBal.setLeft(newMaxVBal.getLeft());
        maxVBal.setRight(newMaxVBal.getRight());
    }

    private void updateMaxVVal(Integer newMaxVVal) {
        maxVVal = newMaxVVal;
    }

    private void myBroadcast(Message message, Address[] to) {
        handleMessage(message, address(), address());
        for (Address a : to) {
            if (!a.equals(address())) {
                send(message, a);
            }
        }
    }
}