package dslabs.basicpaxos;

import dslabs.framework.Command;
import lombok.Data;

@Data
public class Proposal implements Command {
    private final Integer value;
}