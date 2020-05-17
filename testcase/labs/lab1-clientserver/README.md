# StableStorage和Recovery 在RPC上的实验
## 无StableStorage无Recovery的简单RPC
* Client向Server发送请求，包含了Command即远程调用指令，以及Client所记录的SequenceNum和Client的unique id。
* Client发送请求后，则处在等待结果的状态。如果等待超时则重新发送请求。
* Server接收到请求后，首先查看<sequence, unique_id>是否已经在记录中
* 如果不在，则根据Command进行函数调用，将结果Result以<Result, sequence>的形式返回给Client。同时将结果以<<sequence, unique_id>, Result>的形式保存到Log中。
* 如果在，则直接将Log中保存的结果返回。
* 以上可以保证Server不会重复执行某个请求。
## 无StableStorage有Recovery的简单RPC
* 发生crash之前一切正常
* 当Node发生crash，再restart之后，原来维护的SequenceNum丢失，再次发生新的请求时，sequenceNum会发生重复，Server会直接回复之前保存在Log中旧操作的结果。
## 有StableStorage有Recovery的简单RPC
* Client中添加reboot number信息
* 每次启动前首先从StableStorage中读取reboot number，并且加一之后再写入StableStorage。
* Client每次发送请求时，除了发送Client所记录的SequenceNum和Client的unique id之外，还要发送Client当前的reboot number。保证每一个请求都是独一的。
## 实验验证
* 在labs/lab1-clientserver/tst/dslabs/clientserver/ClientServerRecoveryTest.java里
* 首先让一个Client依次发送如下请求
  > put("key1", "v1a"), get("key1"), put("key2", "v2a"),
                    get("key2"), put("key1", "v1b"), get("key1"),
                    append("key3", "v3a"), put("key3", "v3b"),
                    append("key3", "v3c"), append("key3", "v3d"),
                    append("key4", "v4"), append("key4", "v4"), get("key4"),
                    get("key5")
* 然后restart该Node，再执行以下请求
  > get("key1")                    
* 此时正确的返回结果应该为 getResult("v1b")
* 如果没有使用StableStorage，返回的结果应该是第一个操作put("key1", "v1a")的返回结果putOk()。
