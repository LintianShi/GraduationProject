package dslabs.clientserver;

import dslabs.atmostonce.AMOCommand;
import dslabs.atmostonce.AMOResult;
import dslabs.atmostonce.AMOApplication;
import dslabs.kvstore.KVStore;
import dslabs.kvstore.KVStore.SingleKeyCommand;
import dslabs.kvstore.KVStore.KVStoreResult;
import dslabs.clientserver.Reply;
import dslabs.clientserver.Request;
import dslabs.framework.Message;
import dslabs.framework.Address;
import dslabs.framework.Application;
import dslabs.framework.Node;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Simple server that receives requests and returns responses.
 *
 * See the documentation of {@link Node} for important implementation notes.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class SimpleServer extends Node {
    // Your code here...
    private final AMOApplication<Application> app;
    /* -------------------------------------------------------------------------
        Construction and Initialization
       -----------------------------------------------------------------------*/
    public SimpleServer(Address address, Application app, boolean amo) { 
        super(address);
        // Your code here...
        this.app = new AMOApplication<>(app, amo);
    }

    @Override
    public void init() {
        // No initialization necessary
    }

    /* -------------------------------------------------------------------------
        Message Handlers
       -----------------------------------------------------------------------*/
    private void handleRequest(Request m, Address sender) {
        // Your code here...
        AMOResult p = app.execute(m.command());
        send(new Reply(p), sender); 
    }
}
