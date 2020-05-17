# 网络功能实验
## Duplicate
* 如果开启Duplicate功能，使用一个CLient对Server进行append操作。由于消息会出现Duplicate，所以一个key可能会被append两次，造成结果错误。
* 使用at-most-once机制后不会再出现这种错误。
## Disorder
* 乱序不好使用具体算法执行的正确与否进行验证。目前采用打印Log的方式来验证乱序功能的效果。
### 原因
* 目前框架中Disorder功能的实现是shuffle洗牌 inbuf(channel)里堆积的消息
* 因为DSLabs中的Client只能串行执行，上一个Command没有收到结果之前不能够发生下一个Command。所以难以用一个Client按序发送消息，使Server处产生Command堆积的效果。
* 可以用多个Client发送Command使得Server处出现消息堆积，但是无法保证多个Client之间发送消息的顺序，因此也无法证明Server取消息的顺序是乱序。
* DSLabs中Server的本质是Node，没有提供外界操控测试的接口，无法只使用Server进行测试
### DSLabs中日志
* DSLabs使用Lombok库的Log注解实现日志输出功能
* 当一个Node的某个handle函数被调用的时候，会将被handle的消息及其发送者接收者写入日志并输出
* 格式为.format("Message(%s -> %s, %s)", sender, destination, message)
* 因为DSLabs中的Client是串行的，所有handle函数都带锁，因此日志的顺序可以看作消息接收的顺序
### 案例
* 使用Ping-Pong case.
* Client向Server发送一条Ping消息，Server收到后按序发送3条带序号的Pong消息
* 观察日志，查看Client处理3条Pong消息的顺序和Pong消息的序号顺序是否一致
* 进一步延伸：将Ping-Pong响应算法设计成发送Ping的Client必须收到Server按序的Pong才算成功



