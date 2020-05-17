# init
## Basic Paxos
* 目的：通过corrupt篡改节点的状态从而影响节点的行为，让一个节点成为拜占庭问题里的叛徒
* 过程：
* 在有三个节点server1, server2, server3的情况下
* 首先对网络进行分区{server1, server2}, {server3}
* 然后server1提出提议{value = 12}，并得到通过
* 改变网络分区为{server1}, {server2, server3}
* 接着使用Byzantine corruption注入server2，使server2记录maxBal，maxVBal，maxVVal被篡改为从未收到过任何节点消息的状态
* server3提出提议{value = 14}
* 因为server2之前保存的数据被corrupt，因此会接受server3的提议，server3的提议也得到了通过
* 最后共识算法的Safety被破坏
## Ping-Pong
* 简单测试Arbitrary Message的功能
## Client-Server RPC
* Arbitrary Message干扰了RPC的正常运行
