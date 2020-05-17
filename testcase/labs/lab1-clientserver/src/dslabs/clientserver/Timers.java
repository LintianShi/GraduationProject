package dslabs.clientserver;

import dslabs.kvstore.KVStore.SingleKeyCommand;
import dslabs.framework.Timer;
import dslabs.atmostonce.AMOCommand;
import dslabs.atmostonce.AMOResult;
import lombok.Data;

@Data
final class ClientTimer implements Timer {
    static final int CLIENT_RETRY_MILLIS = 100;

    // Your code here...
    private final AMOCommand singleKeyCommand;
}
