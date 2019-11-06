Linux下profile和bashrc区别
1. /etc/profile
用来设置系统环境参数，比如$PATH. 这里面的环境变量是对系统内所有用户生效的。

2. /etc/bashrc
这个文件设置系统bash shell相关的东西，对系统内所有用户生效。只要用户运行bash命令，那么这里面的东西就在起作用。

3. ~/.bash_profile
用来设置一些环境变量，功能和/etc/profile 类似，但是这个是针对用户来设定的，也就是说，你在/home/user1/.bash_profile 中设定了环境变量，那么这个环境变量只针对 user1 这个用户生效.

4. ~/.bashrc
作用类似于/etc/bashrc, 只是针对用户自己而言，不对其他用户生效。
另外/etc/profile中设定的变量(全局)的可以作用于任何用户,而~/.bashrc等中设定的变量(局部)只能继承/etc/profile中的变量,他们是”父子”关系.

> 注！
> ~/.bash_profile 是交互式、login 方式进入 bash 运行的，意思是只有用户登录时才会生效。
> ~/.bashrc 是交互式 non-login 方式进入 bash 运行的，用户不一定登录，只要以该用户身份运行> > 命令行就会读取该文件

5. profile文件

1.1 profile文件的作用

profile(/etc/profile)，用于设置系统级的环境变量和启动程序，在这个文件下配置会对所有用户生效。

当用户登录(login)时，文件会被执行，并从/etc/profile.d目录的配置文件中查找shell设置。

1.2 在profile中添加环境变量

一般不建议在/etc/profile文件中添加环境变量，因为在这个文件中添加的设置会对所有用户起作用。

当必须添加时，我们可以按以下方式添加：

如，添加一个HOST值为linuxprobe.com的环境变量：

export HOST=linuxprobe.com

添加时，可以在行尾使用;号，也可以不使用。

一个变量名可以对应多个变量值，多个变量值需要使用:进行分隔。

添加环境变量后，需要重新登录才能生效，也可以使用source命令强制立即生效：

source /etc/profile

查看是否生效可以使用echo命令：

$ echo $HOST

linuxprobe.com

6. bashrc文件

bashrc文件用于配置函数或别名。bashrc文件有两种级别：

系统级的位于/etc/bashrc、用户级的位于~/.bashrc，两者分别会对所有用户和当前用户生效。

bashrc文件只会对指定的shell类型起作用，bashrc只会被bash shell调用。

7. bash_profile文件

bash_profile只对单一用户有效，文件存储位于~/.bash_profile，该文件是一个用户级的设置，可以理解为某一个用户的profile目录下。

这个文件同样也可以用于配置环境变量和启动程序，但只针对单个用户有效。

和profile文件类似，bash_profile也会在用户登录(login)时生效，也可以用于设置环境变理。

但与profile不同，bash_profile只会对当前用户生效。

8. 差异总结

4.1 首先读入全局环境变量设定档/etc/profile，然后根据其内容读取额外的设定的文档，如/etc/profile.d和/etc/inputrc;

4.2 根据不同使用者帐号，于其家目录内读取~/.bash_profile;

读取失败则会读取~/.bash_login;

再次失败则读取~/.profile(这三个文档设定基本上无差别，仅读取上有优先关系);

4.3 最后，根据用户帐号读取~/.bashrc。（对于预装的linux系统选择《Linux就该这么学》书籍提供的，可免费下载。）

至于~/.profile与~/.bashrc都具有个性化定制功能，但~/.profile可以设定本用户专有的路径、环境变量等，它只能登入的时候执行一次。

~/.bashrc也是某用户专有设定文档，可以设定路径、命令别名，每次shell script的执行都会使用它一次。

这三种文件类型的差异用一句话表述就是：

/etc/profile，/etc/bashrc 是系统全局环境变量设定;~/.profile，~/.bashrc用户家目录下的私有环境变量设定。

当登入系统时候获得一个shell进程时，其读取环境设定档如下：

![](.\assets\shell.webp)