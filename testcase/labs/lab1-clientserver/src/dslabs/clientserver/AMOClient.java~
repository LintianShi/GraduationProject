package dslabs.clientserver;

import dslabs.atmostonce.AMOCommand;
import dslabs.atmostonce.AMOResult;
import dslabs.kvstore.KVStore.SingleKeyCommand;
import dslabs.kvstore.KVStore.KVStoreResult;
import dslabs.framework.Address;
import dslabs.framework.Client;
import dslabs.framework.Command;
import dslabs.framework.Node;
import dslabs.framework.Result;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static dslabs.clientserver.ClientTimer.CLIENT_RETRY_MILLIS;

/**
 * Simple client that sends requests to a single server and returns responses.
 *
 * See the documentation of {@link Client} and {@link Node} for important
 * implementation notes.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class RebootClient extends Node implements Client {
    private final Address serverAddress;

    // Your code here...
    private SingleKeyCommand singleKeyCommand;
    private KVStoreResult kvStoreResult;
    private int sequence;
    private Integer reboot;

    /* -------------------------------------------------------------------------
        Construction and Initialization
       -----------------------------------------------------------------------*/
    public RebootClient(Address address, Address serverAddress) {
        super(address);
        this.serverAddress = serverAddress;
        this.sequence = 0;
        this.reboot = 0;
    }

    @Override
    public synchronized void init() {
        Object m;
        if ((m = read("reboot")) == null) {
            reboot = 0;
            write("reboot", (Object)reboot);
        } else {
            reboot = (Integer) m;
            reboot++;
            write("reboot", (Object)reboot);
        }
    }

    /* -------------------------------------------------------------------------
        Client Methods
       -----------------------------------------------------------------------*/
    @Override
    public synchronized void sendCommand(Command command) {
        // Your code here...
        if (!(command instanceof SingleKeyCommand)) {
            throw new IllegalArgumentException();
        }
        SingleKeyCommand s = (SingleKeyCommand) command;
        singleKeyCommand = s;
        kvStoreResult = null;
        send(new Request(new AMOCommand(s, reboot, sequence, this.address().toString().hashCode())), serverAddress);
        set(new ClientTimer(new AMOCommand(s, reboot, sequence, this.address().toString().hashCode())), CLIENT_RETRY_MILLIS);
    }

    @Override
    public synchronized boolean hasResult() {
        // Your code here...
        return kvStoreResult != null;
    }

    @Override
    public synchronized Result getResult() throws InterruptedException {
        // Your code here...
        while (kvStoreResult == null) {
            wait();
        }
        sequence++;
        return kvStoreResult;
    }

    /* -------------------------------------------------------------------------
        Message Handlers
       -----------------------------------------------------------------------*/
    private synchronized void handleReply(Reply m, Address sender) {
        // Your code here...
        if (m.result().sequenceNum() == sequence) {
            kvStoreResult = (KVStoreResult) m.result().result();
            notify();
        }
    }

    /* -------------------------------------------------------------------------
        Timer Handlers
       -----------------------------------------------------------------------*/
    private synchronized void onClientTimer(ClientTimer t) {
        // Your code here...
        if (singleKeyCommand != null && t.singleKeyCommand().sequenceNum() == sequence && kvStoreResult == null) {
            send(new Request(new AMOCommand(singleKeyCommand, reboot, sequence, this.address().toString().hashCode())), serverAddress);
            set(t, CLIENT_RETRY_MILLIS);
        }    
    }
}
