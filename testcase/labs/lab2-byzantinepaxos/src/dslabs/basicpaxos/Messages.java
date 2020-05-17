package dslabs.basicpaxos;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import dslabs.framework.Address;
import dslabs.framework.Command;
import dslabs.framework.Result;
import dslabs.framework.Message;
import dslabs.atmostonce.AMOCommand;
import dslabs.atmostonce.AMOResult;
import lombok.Data;

@Data
final class BasicPaxosPrepare implements Message {
    private final Pair<Integer, Integer> bal;
}

@Data
final class BasicPaxosPromise implements Message {
	private final Pair<Integer, Integer> maxBal;
	private final Pair<Integer, Integer> maxVBal;
    private final Integer maxVVal;
    private final Integer addressID;
}

@Data
final class BasicPaxosAccept implements Message {
	private final Pair<Integer, Integer> bal;
	private final Integer maxVVal;
}

@Data
final class BasicPaxosAccepted implements Message {
	private final Pair<Integer, Integer> maxVBal;
    private final Integer maxVVal;
    private final Integer addressID;
}
