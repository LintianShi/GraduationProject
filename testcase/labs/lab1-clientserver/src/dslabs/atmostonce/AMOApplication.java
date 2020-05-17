package dslabs.atmostonce;

import dslabs.framework.Application;
import dslabs.framework.Command;
import dslabs.framework.Result;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import java.util.HashMap;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public final class AMOApplication<T extends Application>
        implements Application {
    @Getter @NonNull private final T application;
    private final boolean AMOMode;

    // Your code here...
    private HashMap<String, Result> log = new HashMap<>();
    private String getUniqueSeq(String seq, int id) {
        return seq + ", " + Integer.toString(id);
    }
    @Override
    public AMOResult execute(Command command) {
        if (!(command instanceof AMOCommand)) {
            throw new IllegalArgumentException();
        }

        AMOCommand amoCommand = (AMOCommand) command;
        
        // Your code here...
        if (AMOMode) {
            if (alreadyExecuted(amoCommand)) {
                return new AMOResult(log.get(getUniqueSeq(amoCommand.sequence(), amoCommand.addressID())), amoCommand.sequenceNum());
            }
            Result r = application.execute(amoCommand.command());
            log.put(getUniqueSeq(amoCommand.sequence(), amoCommand.addressID()), r);
            return new AMOResult(r, amoCommand.sequenceNum());
        } else {
            Result r = application.execute(amoCommand.command());
            return new AMOResult(r, amoCommand.sequenceNum());
        }    
    }

    public Result executeReadOnly(Command command) {
        if (!command.readOnly()) {
            throw new IllegalArgumentException();
        }

        if (command instanceof AMOCommand) {
            return execute(command);
        }

        return application.execute(command);
    }

    public boolean alreadyExecuted(AMOCommand amoCommand) {
        // Your code here...
        if (log.containsKey(getUniqueSeq(amoCommand.sequence(), amoCommand.addressID()))) { 
            return true;
        } else {
            return false;
        }
    }
}
