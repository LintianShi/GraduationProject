package dslabs.basicpaxos;

import com.google.common.collect.Lists;
import dslabs.framework.Address;
import dslabs.framework.Client;
import dslabs.framework.ByzantineCorruption;
import dslabs.framework.testing.StateGenerator;
import dslabs.framework.testing.StateGenerator.StateGeneratorBuilder;
import dslabs.framework.testing.Workload;
import dslabs.framework.testing.junit.BaseJUnitTest;
import dslabs.framework.testing.junit.PrettyTestName;
import dslabs.framework.testing.junit.RunTests;
import dslabs.framework.testing.junit.SearchTests;
import dslabs.framework.testing.junit.TestPointValue;
import dslabs.framework.testing.junit.UnreliableTests;
import dslabs.framework.testing.runner.RunState;
import dslabs.framework.testing.search.Search;
import dslabs.framework.testing.search.SearchResults;
import dslabs.framework.testing.search.SearchState;
import dslabs.kvstore.KVStore;
import dslabs.kvstore.KVStoreWorkload;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import static dslabs.framework.testing.StatePredicate.ALL_RESULTS_SAME;
import static dslabs.framework.testing.StatePredicate.CLIENTS_DONE;
import static dslabs.framework.testing.StatePredicate.NONE_DECIDED;
import static dslabs.framework.testing.StatePredicate.RESULTS_OK;
import static dslabs.framework.testing.search.SearchResults.EndCondition.INVARIANT_VIOLATED;
import static dslabs.kvstore.KVStoreWorkload.APPENDS_LINEARIZABLE;
import static dslabs.kvstore.KVStoreWorkload.append;
import static dslabs.kvstore.KVStoreWorkload.appendDifferentKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.appendResult;
import static dslabs.kvstore.KVStoreWorkload.appendSameKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.differentKeysInfiniteWorkload;
import static dslabs.kvstore.KVStoreWorkload.get;
import static dslabs.kvstore.KVStoreWorkload.getResult;
import static dslabs.kvstore.KVStoreWorkload.put;
import static dslabs.kvstore.KVStoreWorkload.putGetWorkload;
import static dslabs.kvstore.KVStoreWorkload.putOk;
import static dslabs.kvstore.KVStoreWorkload.putWorkload;
import static dslabs.kvstore.KVStoreWorkload.simpleWorkload;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.apache.commons.lang3.tuple.MutablePair;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicPaxosTest extends BaseJUnitTest {
    @Override
    public void setupTest() {
        super.setupTest();
        builder = StateGenerator.builder();
        builder.workloadSupplier(KVStoreWorkload.emptyWorkload());
    }

    static Address[] servers(int numServers) {
        final Address[] servers = new Address[numServers];
        for (int i = 0; i < numServers; i++) {
            servers[i] = server(i + 1);
        }
        return servers;
    }

    private static void setupBuilder(StateGeneratorBuilder builder,
                                     Address[] servers) {
        builder.serverSupplier(a -> new BasicPaxosNode(a, servers.clone()));
        builder.clientSupplier(a -> new BasicPaxosNode(a, servers.clone()));
    }

    static StateGeneratorBuilder builder(Address[] servers) {
        StateGeneratorBuilder builder = StateGenerator.builder();
        builder.workloadSupplier(KVStoreWorkload.emptyWorkload());
        setupBuilder(builder, servers);
        return builder;
    }

    private Client[] setupStates(int numServers) {
        setupBuilder(builder, servers(numServers));

        runState = new RunState(builder.build());
        initSearchState = new SearchState(builder.build());

        Client[] clients = new Client[numServers];
        int i = 0;
        for (Address server : servers(numServers)) {
            clients[i++] = runState.addClient(server);
        }
        return clients;
    }


    /* Tests */

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Basic Test actually wrong but right")
    @Category(RunTests.class)
    @TestPointValue(5)
    public void test01BasicTest() throws InterruptedException {
        Client[] clients = setupStates(3);
        
        runSettings.partition(server(1), server(2));
        runState.start(runSettings);
        
        sendCommandAndCheck(clients[0], new Proposal(12), new Consensus(12));

        Thread.sleep(300);
        runState.stop();
        runSettings.resetNetwork();
        runSettings.partition(server(2), server(3));
        
        Address subNodeAddress = Address.subAddress(server(2), "byzantine-test");
        ByzantineCorruption subNode = new ByzantineCorruption(subNodeAddress);
        runState.addByzantine(server(2), subNode);
        
        subNode.setCorruptTarget("maxBal", () -> {return new MutablePair<Integer, Integer>(-1, -1);});
        subNode.setCorruptTarget("maxVBal", () -> {return new MutablePair<Integer, Integer>(-1, -1);});
        subNode.setCorruptTarget("maxVVal", () -> {return null;});        
        subNode.corruptData(2, 200);

        runState.start(runSettings);
        Thread.sleep(1000);

        sendCommandAndCheck(clients[2], new Proposal(14), new Consensus(14));  //actually wrong but right
        //sendCommandAndCheck(clients[2], new Proposal(14), new Consensus(12)); //actually right but wrong
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Basic Test actually right but wrong")
    @Category(RunTests.class)
    @TestPointValue(5)
    public void test02BasicTest() throws InterruptedException {
        Client[] clients = setupStates(3);
        
        runSettings.partition(server(1), server(2));
        runState.start(runSettings);
        
        sendCommandAndCheck(clients[0], new Proposal(12), new Consensus(12));

        Thread.sleep(300);
        runState.stop();
        runSettings.resetNetwork();
        runSettings.partition(server(2), server(3));
        
        Address subNodeAddress = Address.subAddress(server(2), "byzantine-test");
        ByzantineCorruption subNode = new ByzantineCorruption(subNodeAddress);
        runState.addByzantine(server(2), subNode);
        
        subNode.setCorruptTarget("maxBal", () -> {return new MutablePair<Integer, Integer>(-1, -1);});
        subNode.setCorruptTarget("maxVBal", () -> {return new MutablePair<Integer, Integer>(-1, -1);});
        subNode.setCorruptTarget("maxVVal", () -> {return null;});        
        subNode.corruptData(2, 200);

        runState.start(runSettings);
        Thread.sleep(1000);

        //sendCommandAndCheck(clients[2], new Proposal(14), new Consensus(14));  //actually wrong but right
        sendCommandAndCheck(clients[2], new Proposal(14), new Consensus(12)); //actually right but wrong
    }
}
