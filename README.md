# JiayuBlog
Java course design.
develop by superxc.com 20160701.

###已经实现的功能
* 微博发表
* 用户之间相互关注，取关。
* 通过用户昵称模糊查找用户
* 可以修改用户头像，昵称，自定义头像预留了，但没有实现
* 显示用户的关注数，粉丝数，微博数
* 可以评论，点赞，至于转发功能，还没有实现

###本程序一共包含19个类。
× BlogComparator.java//比较器，用于微博按时间排序
× Blog.java//微博类
× BlogStdTime.java//微博标准时间，本软件所用到的时间统一使用这个格式
× HeadModifyHandle.java//头像修改处理器
* HeadModifyView.java//头像修改视图
* JiayuBlog.java//主类
* JiayuDB.java//封装的数据库操作类
* LabelPanel.java//把标签嵌到面板的类
* LoginHandle.java//登录处理器
* LoginView.java//登录视图
* MainHandle.java//主处理器
* MainView.java//主视图
* RegHandle.java//注册处理器
* RegView.java//注册视图
* User.java//用户类
* XCBlogPanel.java//微博框
* XCBlogPlHandle.java//微博框事件处理
* XCCheckLabel.java//文本检查标签
* XCUserPanel.java//用户框

**数据库是superxc**
**img目录下存放的是软件运行必备的图片**

以下是数据库用的表:
user表 用于用户信息的维护, 查询, 登录

create table userInfo(uid int primary key, userName char(20), 
                     userPasswd char(20), nickName char(20), phoneNumber char(20),
                     profile varchar(100), followCnt int, fansCnt int, microCnt int, headImage varchar(500));

（已经创建）
headImage如果是有效的url则为用户自定义的头像, 为数字则为系统内建头像.

follow表 用于记录用户之间的关注关系.(fan 关注star), 关注的时间
create table follow(fanId int, starId int, followTime varchar(50))
microBlog表 用于记录所有用户发的微博(已经创建)
create table microBlog(blogId int primary key, uid int, blogContent varchar(128),  
                       commentCnt int, imageUrl varchar(500),rePostCnt int, postTime varchar(50), niceCnt int);

commentBlog表 用于记录微博的评论(已经创建)
create table commentBlog(blogId int, uid int, commentConent varchar(64));
udi指示了谁评论的,

sysBlog 表用于记录系统信息：(已经创建)
create table sysBlog(pName char(50) ,pValue varchar(200), lValue int);
属性名，参数1, 参数2
'userCnt', '',1000    记录用户数，用户id依赖这个，从1001开始有效
'blogCnt','',1000    记录微博数， 微博id依赖这个， 从1001开始有效
