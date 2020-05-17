package dslabs.pingpong;

import dslabs.framework.Address;
import dslabs.framework.Command;
import dslabs.framework.Result;
import dslabs.framework.testing.LocalAddress;
import dslabs.framework.testing.StateGenerator;
import dslabs.framework.testing.StateGenerator.StateGeneratorBuilder;
import dslabs.framework.testing.Workload;
import dslabs.framework.testing.junit.BaseJUnitTest;
import dslabs.framework.testing.junit.PrettyTestName;
import dslabs.framework.testing.junit.RunTests;
import dslabs.framework.testing.junit.SearchTests;
import dslabs.framework.testing.junit.UnreliableTests;
import dslabs.framework.testing.runner.RunState;
import dslabs.framework.testing.search.Search;
import dslabs.framework.testing.search.SearchState;
import dslabs.framework.testing.utils.SerializableFunction;
import dslabs.pingpong.PingApplication.Ping;
import dslabs.pingpong.PingApplication.Pong;
import java.util.Objects;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import static dslabs.framework.testing.StatePredicate.CLIENTS_DONE;
import static dslabs.framework.testing.StatePredicate.RESULTS_OK;
import static dslabs.framework.testing.search.SearchResults.EndCondition.INVARIANT_VIOLATED;
import static dslabs.framework.testing.search.SearchResults.EndCondition.SPACE_EXHAUSTED;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class PingTest extends BaseJUnitTest {
    static final Address sa = new LocalAddress("pingserver");

    private static final class PingParser implements
            SerializableFunction<Pair<String, String>, Pair<Command, Result>> {
        @Override
        public Pair<Command, Result> apply(
                @NonNull Pair<String, String> commandAndResultString) {
            return new ImmutablePair<>(
                    new Ping(commandAndResultString.getValue()),
                    new Pong(commandAndResultString.getValue()));
        }
    }

    static StateGeneratorBuilder builder() {
        StateGeneratorBuilder builder = StateGenerator.builder();
        builder.serverSupplier(a -> {
            if (!Objects.equals(a, sa)) {
                throw new IllegalArgumentException();
            }
            return new PingServer(sa);
        });
        builder.clientSupplier(a -> new PingClient(a, sa));
        builder.workloadSupplier(Workload.emptyWorkload());

        return builder;
    }

    @Before
    public void setup() {
        builder = builder();

        runState = new RunState(builder.build());
        runState.addServer(sa);

        initSearchState = new SearchState(builder.build());
        initSearchState.addServer(sa);
    }

    static Workload repeatedPings(int numPings) {
        return Workload.builder().parser(new PingParser())
                       .commandStrings("ping-%i").resultStrings("ping-%i")
                       .numTimes(numPings).build();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test01DisorderPing1() throws InterruptedException {
        runSettings.networkDisorderRate(0.05);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test02DisorderPing2() throws InterruptedException {
        runSettings.networkDisorderRate(0.2);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test03DisorderPing3() throws InterruptedException {
        runSettings.networkDisorderRate(0.35);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test04DisorderPing4() throws InterruptedException {
        runSettings.networkDisorderRate(0.5);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test05DisorderPing5() throws InterruptedException {
        runSettings.networkDisorderRate(0.65);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test06DisorderPing6() throws InterruptedException {
        runSettings.networkDisorderRate(0.8);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }

    @Test(timeout = 5 * 1000)
    @PrettyTestName("Single client ping test")
    @Category({RunTests.class})
    public void test07DisorderPing7() throws InterruptedException {
        runSettings.networkDisorderRate(0.95);
        Workload workload =
                Workload.builder().commands(new Ping("Hello, World!"))
                        .results(new Pong("Pass")).build();
        runState.addClientWorker(client(1), workload);

        runState.run(runSettings);

        runSettings.addInvariant(RESULTS_OK);
        assertRunInvariantsHold();
    }
}
