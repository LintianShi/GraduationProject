package dslabs.clientserver;

import dslabs.framework.Client;
import dslabs.framework.testing.ClientWorker;
import dslabs.framework.testing.junit.PrettyTestName;
import dslabs.framework.testing.junit.RunTests;
import dslabs.framework.testing.junit.SearchTests;
import dslabs.framework.testing.junit.TestPointValue;
import dslabs.framework.testing.junit.UnreliableTests;
import dslabs.framework.testing.search.Search;
import dslabs.kvstore.KVStoreWorkload;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import static dslabs.framework.testing.StatePredicate.CLIENTS_DONE;
import static dslabs.framework.testing.StatePredicate.NONE_DECIDED;
import static dslabs.framework.testing.StatePredicate.RESULTS_OK;
import static dslabs.framework.testing.search.SearchResults.EndCondition.INVARIANT_VIOLATED;
import static dslabs.framework.testing.search.SearchResults.EndCondition.SPACE_EXHAUSTED;
import static dslabs.kvstore.KVStoreWorkload.APPENDS_LINEARIZABLE;
import static dslabs.kvstore.KVStoreWorkload.appendAppendGet;
import static dslabs.kvstore.KVStoreWorkload.appendDifferentKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.appendSameKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.differentKeysInfiniteWorkload;
import static dslabs.kvstore.KVStoreWorkload.put;
import static dslabs.kvstore.KVStoreWorkload.putAppendGetWorkload;
import static dslabs.kvstore.KVStoreWorkload.putOk;
import static dslabs.kvstore.KVStoreWorkload.get;
import static dslabs.kvstore.KVStoreWorkload.getResult;
import static dslabs.kvstore.KVStoreWorkload.simpleWorkload;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class ClientServerRecoveryTest1 extends ClientServerAMOTest {
    @Test(timeout = 50 * 1000)
    @PrettyTestName("Single client recovery, actually right")
    @TestPointValue(100)
    @Category({RunTests.class, UnreliableTests.class})
    public void test01Recovery1()
            throws InterruptedException {
        runSettings.networkDeliverRate(0.8);

        runState.addClientWorker(client(1),simpleWorkload);
        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();

        runState.restartNode(client(1));
        ClientWorker client = runState.clientWorker(client(1));
        client.addCommand(get("key1"), getResult("v1b"));  //actually right, but wrong in test
        //client.addCommand(get("key1"), putOk());  //actually wrong, but right in test
        runState.run(runSettings);
        assertRunInvariantsHold();
    }

    @Test(timeout = 50 * 1000)
    @PrettyTestName("Single client recovery, actually wrong")
    @TestPointValue(100)
    @Category({RunTests.class, UnreliableTests.class})
    public void test02Recovery2()
            throws InterruptedException {
        runSettings.networkDeliverRate(0.8);

        runState.addClientWorker(client(1),simpleWorkload);
        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();

        runState.restartNode(client(1));
        ClientWorker client = runState.clientWorker(client(1));
        //client.addCommand(get("key1"), getResult("v1b"));  //actually right, but wrong in test
        client.addCommand(get("key1"), putOk());  //actually wrong, but right in test
        runState.run(runSettings);
        assertRunInvariantsHold();
    }
}
