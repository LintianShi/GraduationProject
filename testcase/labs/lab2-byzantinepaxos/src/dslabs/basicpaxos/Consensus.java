package dslabs.basicpaxos;

import dslabs.framework.Result;
import lombok.Data;

@Data
public class Consensus implements Result {
    private final Integer value;
}