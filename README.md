# 简介
* 本项目对DSLabs框架中Runner部分做出了一些改进和扩展。主要贡献有：
* 增加了两种网络错误，乱序和重复。
* 添加了一个StableStorage模块
* 实现了拜占庭错误注入功能
# 编译
* 在主目录下使用ant all编译框架代码
* 在testcase目录下使make编译测试样例代码
# 测试
## 网络错误实验
* python run-test.py --lab 0  乱序错误实验
* python run-test.py --lab 1 --part 1  重复错误实验
## StableStorage实验
* python run-test.py --lab 1 --part 2  RPC系统正确性测试
* python run-test.py --lab 1 --part 3  Crash-Restart造成没有Stable Storage的RPC系统发送错误
* python run-test.py --lab 1 --part 4  使用Stable Storage的RPC在Crash-Restart下依然保持正确
## 拜占庭错误注入实验
* python run-test.py --lab 2 注入拜占庭错误使得client接受了一个错误共识
