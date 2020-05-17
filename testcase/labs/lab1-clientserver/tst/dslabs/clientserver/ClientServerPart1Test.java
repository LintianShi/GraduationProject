package dslabs.clientserver;

import dslabs.framework.Client;
import dslabs.framework.testing.junit.PrettyTestName;
import dslabs.framework.testing.junit.RunTests;
import dslabs.framework.testing.junit.TestPointValue;
import dslabs.framework.testing.junit.UnreliableTests;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import static dslabs.framework.testing.StatePredicate.RESULTS_OK;
import static dslabs.kvstore.KVStoreWorkload.APPENDS_LINEARIZABLE;
import static dslabs.kvstore.KVStoreWorkload.appendDifferentKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.appendSameKeyWorkload;
import static dslabs.kvstore.KVStoreWorkload.get;
import static dslabs.kvstore.KVStoreWorkload.simpleWorkload;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class ClientServerPart1Test extends ClientServerBaseTest {

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test01SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.05);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test02SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.1);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test03SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.15);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test04SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.2);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }
 
    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test05SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.25);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test06SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.3);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test07SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.35);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test08SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.4);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test09SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.45);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test10SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.5);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test11SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.55);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test12SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.6);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test13SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.65);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test14SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.7);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test15SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.75);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test16SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.8);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test17SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.85);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test18SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.9);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 30 * 1000)
    @PrettyTestName("Single client append")
    @Category({RunTests.class, UnreliableTests.class})
    @TestPointValue(20)
    public void test19SingleClientAppendUnreliable()
            throws InterruptedException {
        int numRounds = 4;

        runState.addClientWorker(client(1),
                appendDifferentKeyWorkload(numRounds));
        runSettings.networkDuplicateRate(0.95);
        runState.run(runSettings);
        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }
}
