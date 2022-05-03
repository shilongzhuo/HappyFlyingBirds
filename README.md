# Happy Flying birds1.0

#### 介绍
大一下学期javaswing实验项目

#### 实现功能
flying birds小游戏（登录注册，游戏，积分，排行榜）
简单的游戏聊天室（群聊，传文件，换肤）
#### 部署
1、开发平台和环境：操作系统环境Windows 10；开发平台IntelliJ IDEA 2019.3.1 x64；数据库MySQL 5.5.40；

2、项目导入，导入源代码文件夹中Happy Flying Birds文件。

3、安装“3rdLibs”文件夹中substance.jar组件、mysql-connector-java-5.1.37.jar组件

4、substance.jar组件安装和配置
在源代码开发环境中配置substance.jar组件，方法：
project-->Project Structure->Modules->+->substance.jar->OK

5、mysql-connector-java-5.1.37.jar组件安装和配置
在源代码开发环境中配置mysql-connector-java-5.1.37.jar组件，方法：
project-->Project Structure->Modules->+->mysql-connector-java-5.1.37.jar->OK

6、数据库配置
导入数据库脚本，用户名为root和密码为zhuoshilong ，数据库名为user等。
（注：如果MySQL数据库版本较高，为8.0版本，组件应添加mysql-connector-java-8.0.17.jar，Jdbcs文件中的String driver = "com.mysql.jdbc.Driver";
应该为String driver = "com.mysql.cj.jdbc.Driver"; 同时账号和密码应为本地MySQL数据库设置的用户名和密码。

7、系统运行
首先运行SeverMain文件启动服务器，然后运行MyWindow文件，即可启动项目，初始用户名可以自己注册，也可从数据库中选取，输入账号，密码，
或游客登录，即可体验本游戏，且在账号模式中可以体验我们的社区聊天模式，初始账号为1，密码为123，也可注册选取头像。

#### 启动
首先运行ServerMain打开聊天服务器
接着运行MyWindow，即可体验功能