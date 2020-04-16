# jetpack学习github搭建指导文档

1. 如果没有github账号，请打开github的注册网址，然后注册一个github账号

  > https://github.com/join?source=login
  >
  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/github_create_acount.png)

2. 项目地址

  > https://github.com/zerovip123/jetpack_practise.git

3. 如果没有安装git，则需要下载git,并安装

  > https://git-scm.com/downloads
  >
  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/git.png)

4.  git-ssh配置

- 设置git的user name 和email

  ```bash
  $ git config --global user.name "zero"
  $ git config --global user.email "zerovip@163.com"
  ```

- 生成密钥

  ```bash
  $ ssh-keygen -t rsa -C "zerovip@163.com"
  ```

  > 连续3个回车。如果不需要密码的话。
  > 最后得到了两个文件：`id_rsa`和`id_rsa.pub`

- 添加密钥到ssh-agent

  ```bash
  $ eval "$(ssh-agent -s)"
  Agent pid 946
  ```

- 添加生成的 SSH key 到 ssh-agent
  ```bash
  $ ssh-add ~/.ssh/id_rsa
  ```

- 登录github,添加ssh

  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/github_setting_1.png)
  >
  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/github_setting_2.png)
  >
  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/github_setting_3.png)

5. 测试

```bash
 $ ssh -T git@github.com
```
  > ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/github_test.png)


6. 拉取项目
	```bash
    $ git clone https://github.com/zerovip123/jetpack_practise.git
   ```
   ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/git_1.png)

7.  创建新分支，完成作业
-  建议每位学员自己创建分支，并且以QQ号为分支名，以便老师知道是谁提交的作业

  ```bash
  //创建新分支
  git checkout -b [branchname]
  ```
  
  ![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/git_2.png)

8. 在自己的新分支上完成作业，然后提交代码

   ```bash
   //添加代码
   git add .
   //commit
   git commit -m "日志信息"
   ```

   

![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/git_3.png)

9. 完成作业后把各自的分支推送到github,老师就可以检查同学们的作业
```bash
git push origin [branch name]
```
![](github%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.assets/git_4.png)










