package dslabs.atmostonce;

import dslabs.framework.Command;
import lombok.Data;

@Data
public final class AMOCommand implements Command {
    // Your code here...
    private final Command command;
    private final int reboot;
    private final int sequenceNum;
    private final int addressID;

    public String sequence() {
        return Integer.toString(reboot) + ", " + Integer.toString(sequenceNum);
    }
}
