package dslabs.clientserver;

import dslabs.framework.Command;
import dslabs.framework.Result;
import dslabs.framework.Message;
import dslabs.atmostonce.AMOCommand;
import dslabs.atmostonce.AMOResult;
import dslabs.kvstore.KVStore.KVStoreResult;
import dslabs.kvstore.KVStore.SingleKeyCommand;
import lombok.Data;

@Data
class Request implements Message {
	private final AMOCommand command;
}

@Data
class Reply implements Message {
	private final AMOResult result;
}

