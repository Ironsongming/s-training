##S-Training 文档

####目录
1. [基本环境](#base_config)
2. [代码概要架构](#code_framework)
3. [接口列表](#api_list)



####<h4 id = "base_config">基本环境<h4>
1. [mysql 5.5.48](http://www.mysql.com)
2. [redis 3.0.7](http://redis.io)
3. [httpsqs 1.7](http://zyan.cc/httpsqs_1_2)
4. [Tomcat 8.0](http://tomcat.apache.org)
5. [nginx 1.8](http://nginx.org)
6. [zookeeper 3.4.6](http://zookeeper.apache.org)

<h4 id = "list"><h4>
####<h4 id = "code_framework">代码概要架构<h4>
1.	业务对象  

	|名称|作用|规范|  
	|:--|:--|:---------------------------|  	
	|domain|数据库模型|类型与数据库表一致，不涉及任何业务具体要求，用于数据库基本操作|  
	|entity|业务层基本模型|业务层具体操作对象|  
	|info|DTO|接口层基本操作对象，只能向外输出|  
	|params|DTO|接口层基本操作对象，只能向内输入 _tips：当输入参数个数较少，不应使用params_|  
	|caps|持久化层操作|数据库基本操作，不涉及具体业务，只允许条件选择，不允许分页操作|  
	|core|业务层|业务具体实现|  
	|service|业务接口层|只向外暴露服务接口，不做任何处理|  
	|reference|接口层－业务层 连接模型|连接对象|  
	|page|接口层|对客户端暴露接口，兼容接口的升级|  




<h4 id = "catalog"><h4>
####<h4 id = "api_list">接口列表<h4>
1. [用户相关](#user_api_list)
2. [通讯相关](#contact_api_list)
3. [健身相关](#training_api_list)
4. [文章相关](#article_api_list)
5. [动态相关](#moment_api_list)

####<h4 id = "user_api_list">用户相关接口列表<h4>
1. [用户手机注册接口](#UserCreatePage)
2. [用户手机登陆接口](#PhoneLoginPage)
3. [用户更新资料接口](#UserUpdatePage)
4. [用户详情接口](#UserDetailPage)
5. [用户好友列表接口](#FollowListPage)
6. [用户积分排行榜接口](#UserRankPage)

####<h4 id = "contact_api_list">通讯相关接口列表<h4>
1. [添加好友接口](#FollowPage)
2. [删除好友接口](#UnFollowPage)
3. [创建群接口](#GroupCreatePage)
4. [群详情接口](#GroupDetailPage)
5. [群列表接口](#GroupListPage)
6. [删除群接口](#GroupQuitPage) 

####<h4 id = "training_api_list">健身相关接口列表<h4>
1. [创建健身任务接口](#TrainingCreatePage)
2. [健身任务详情接口](#TrainingDetailPage)
3. [结束健身任务接口](#TrainingFinishPage)
4. [健身任务列表接口](#TrainingListPage)
5. [创建健身任务预加载接口](#TrainingPreCreatePage)
6. [开启健身任务接口](#TrainingStartPage)

####<h4 id = "article_api_list">文章相关接口列表<h4>
1. [创建文章](#ArticleCreatePage)
2. [删除文章](#ArticleDeletePage)
3. [文章详情](#ArticleDetailPage)
4. [推荐文章列表](#ArticleRecommedListPage)
5. [用户文章列表](#ArticleUserListPage)
6. [创建文章评论](#ArticleCommentCreatePage)
7. [删除文章评论](#ArticleCommentDeletePage)
8. [点赞／灭文章](#ArticleLikeCreatePage)

####<h4 id = "training_api_list">动态相关接口列表<h4>
1. [创建动态](#MomentCreatePage)
2. [转发动态](#MomentTransferPage)
3. [删除动态](#MomentDeletePage)
4. [动态详情](#MomentDetailPage)
5. [动态列表](#MomentListPage)
6. [用户动态列表](#MomentUserListPage)
7. [创建动态评论](#MomentReplyCreatePage)
8. [删除动态评论](#MomentReplyDeletePage)
9. [动态点赞／取消点赞](#MomentLikeCreatePage)

####<h4 id = "UserCreatePage">用户手机注册接口<h4>
##### url 
 
 - /user/account/create
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| phone | String | 13512371174 | required | 11位注册手机|
| password | String    | songming | required| 12位登陆密码|

##### return   
```json  
{
  "key": "123461291691941629719",
  "userID": 100312312,
  "ts": 1461498240688,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "PhoneLoginPage">用户手机登陆接口<h4>
##### url 
 
 - /user/login/phone
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| phone | String | 13512371174 | * | 11位注册手机|
| password | String    | songming | *| 12位登陆密码|

##### return  
```json  
{
  "key": "123461291691941629719",
  "userID": 100312312,
  "ts": 1461498240688,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "UserDetailPage">用户详情接口<h4>
##### url 
 
 - /user/detail
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
| target_user_id | long | 212312313 | * | 用户ID|

##### return  
```json  
{
  "user": {
    "userID": 1123131,
    "username": "chensongming",
    "signNature": "helloworld",
    "phone": "13581372272",
    "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
    "status": 1,
    "rank": 100,
    "score": 3000,
    "canEdit": 1
  },
  "ts": 1461502571545,
  "status": 1
}
```
**user.status： 状态 1.正常 2.删除**
[回到目录](#catalog)

####<h4 id = "UserUpdatePage">用户更新资料接口<h4>
##### url 
 
 - /user/detail/update
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
| username | String | chensongming |  | 用户名|
| password | String | 212312313 |  | 用户密码|
| sign_nature | String | 212312313 |  | 用户签名|
| avatar | String | 212312313.jpg |  |用户头像上传的文件名|


##### return  
```json  
{
  "ts": 1461503997138,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "FollowListPage">用户好友列表接口<h4>
##### url 
 
 - /user/list/follow
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|

##### return  
```json  
{
  "users": [
    {
      "userID": 10000000,
      "username": "陈松铭LA",
      "signNature": "helloworld",
      "phone": "13580122279",
      "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
      "rank": 0,
      "score": 0
    },
    {
      "userID": 10000002,
      "username": "",
      "signNature": "该用户很懒，什么也没留下",
      "phone": "13512377777",
      "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
      "rank": 0,
      "score": 0
    }
  ],
  "start": "",
  "more": 0,
  "ts": 1461504372343,
  "status": 1
}
```
[回到目录](#catalog)
####<h4 id = "UserRankPage">用户积分排行榜接口<h4>
##### url 
 
 - /user/list/rank
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|

##### return  
```json  
{
  "users": [
    {
      "userID": 10000000,
      "username": "陈松铭LA",
      "signNature": "helloworld",
      "phone": "13580122279",
      "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
      "rank": 0,
      "score": 0
    },
    {
      "userID": 10000002,
      "username": "",
      "signNature": "该用户很懒，什么也没留下",
      "phone": "13512377777",
      "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
      "rank": 0,
      "score": 0
    }
  ],
  "start": "",
  "more": 0,
  "ts": 1461504372343,
  "status": 1
}
```
[回到目录](#catalog)


####<h4 id = "FollowPage">添加好友接口<h4>
##### url 
 
 - /contact/follow
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|target_id|long|1231231231231|*|添加好友的ID|

##### return  
```json  
{
  "ts": 1461504372343,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "UnFollowPage">删除好友接口<h4>
##### url 
 
 - /contact/unfollow
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|target_id|long|1231231231231|*|添加好友的ID|

##### return  
```json  
{
  "ts": 1461504372343,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "GroupCreatePage">创建群接口<h4>
##### url 
 
 - /contact/group/create
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|member_ids|String|1231231231231，12311，1312|*|成员ID集合，用逗号分割,要包含创建者ID|

##### return  
```json  
{
  "group": {
    "groupID": 9,
    "groupName": "陈松铭LA",
    "memberCount": 2,
    "members": [
      {
        "userID": 10000000,
        "username": "陈松铭LA",
        "signNature": "helloworld",
        "phone": "13512372279",
        "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
        "rank": 0,
        "score": 0
      },
      {
        "userID": 10000001,
        "username": "",
        "signNature": "该用户很懒，什么也没留下",
        "phone": "18218705450",
        "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
        "rank": 0,
        "score": 0
      }
    ]
  },
  "ts": 1461505899740,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "GroupDetailPage">群详情接口<h4>
##### url 
 
 - /contact/group/detail
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|group_id|long| 12311|*|群ID|

##### return  
```json  
{
  "group": {
    "groupID": 9,
    "groupName": "陈松铭LA",
    "memberCount": 2,
    "members": [
      {
        "userID": 10000000,
        "username": "陈松铭LA",
        "signNature": "helloworld",
        "phone": "13512372279",
        "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
        "rank": 0,
        "score": 0
      },
      {
        "userID": 10000001,
        "username": "",
        "signNature": "该用户很懒，什么也没留下",
        "phone": "18218705450",
        "avatar": "http://www.baidu.com/load?fileName=20160408163248q2bMAFpUK6hJYzgPT7ji.png",
        "rank": 0,
        "score": 0
      }
    ]
  },
  "ts": 1461505899740,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "GroupListPage">群列表接口<h4>
##### url 
 
 - /contact/group/list
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|

##### return  
```json  
{
  "groups": [
    {
      "groupID": 8,
      "groupName": "chensongming",
      "memberCount": 2,
    },
    {
      "groupID": 9,
      "groupName": "陈松铭LA",
      "memberCount": 2,
    }
  ],
  "start": "",
  "more": 0,
  "ts": 1461506485569,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "GroupQuitPage">删除群接口<h4>
##### url 
 
 - /contact/group/quit
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|group_id|long| 12311|*|群ID|

##### return  
```json  
{
  "ts": 1461506485569,
  "status": 1
}
```
[回到目录](#catalog)


####<h4 id = "TrainingCreatePage">创建健身任务接口<h4>
##### url 
 
 - /training/create
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|type|int| 12311|*|运动类型|
|location|int| 12311|*|运动地点类型|
|drinking|int| 12311|*|运动饮料类型|
|gear|int| 12311|*|运动护具类型|
|begin_at|long| 12311|*|开始时间|
|preset_count|int| 12311|*|预设个数|
|preset_group|int| 12311|*|预设组数|
|per_breaktime|int| 12311|*|预设平均休息时间|

##### return  
```json  
{
  "training": {
    "trainingID": 9,
    "userID": 10000000,
    "type": 1,
    "beginAt": 1461470371000,
    "finishAt": 0,
    "presetCount": 80,
    "presetGroup": 8,
    "perBreakTime": 30,
    "location": 1,
    "drinking": 1,
    "gear": 1,
    "actualCount": 0,
    "actualGroup": 0,
    "actualBreakTime": 0,
    "actualConsumTime": 0,
    "status": 1,
    "createAt": 1461507067000,
    "content": "运动目标: \n在 8 组内完成 80 个 伏地挺身;\n由于你在 室外/操场 环境中，并平均休息 30 秒;\n同时你选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n根据你实际的完成结果来最终评定你的得分",
    "title": "伏地挺身 #8G-80",
    "isSuccess": 0,
    "items": []
  },
  "ts": 1461507067597,
  "status": 1
}
```
**training.status 状态：1.创建 2.开始 3.结束**
**training.isSuccess 0:未达成目标 1:达成**

[回到目录](#catalog)

####<h4 id = "TrainingDetailPage">健身任务详情接口<h4>
##### url 
 
 - /training/detail
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|training_id|long| 12311|*|运动任务ID|


##### return  
```json  
{
  "training": {
    "trainingID": 9,
    "userID": 10000000,
    "type": 1,
    "beginAt": 1461470371000,
    "finishAt": 1461507554000,
    "presetCount": 80,
    "presetGroup": 8,
    "perBreakTime": 30,
    "location": 1,
    "drinking": 1,
    "gear": 1,
    "actualCount": 80,
    "actualGroup": 10,
    "actualBreakTime": 300,
    "actualConsumTime": 500,
    "status": 3,
    "createAt": 1461507067000,
    "content": "运动预设目标: \n在 8 组内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
    "title": "伏地挺身 #8G-80",
    "isSuccess": 1,
    "items": [
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      },
      {
        "count": 8,
        "breakTime": 30
      }
    ]
  },
  "ts": 1461507592571,
  "status": 1
}
```
**training.status 状态：1.创建 2.开始 3.结束**
**training.isSuccess 0:未达成目标 1:达成**

[回到目录](#catalog)

####<h4 id = "TrainingFinishPage">结束健身任务接口<h4>
##### url 
 
 - /training/finish
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|training_id|long| 12311|*|运动任务ID|
|consum_time|int| 12311|*|实际总耗时：秒|
|training_items|int| 8-30,8-30,8-30,8-30,8-30,8-30,8-30,8-30,8-30,8-30 |*|实际每组个数及休息时间 个数－休息时间，用逗号隔开|

##### return  
```json  
{
  "ts": 1461507554128,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "TrainingListPage">健身任务列表接口<h4>
##### url 
 
 - /training/list
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|target_user_id|long| 12311|*|目标userID|
|start|long| 12311|*|分页起始页，首页为0|
|count|int| 20  |*|每页大小|

##### return  
```json  
{
  "trainings": [
    {
      "trainingID": 9,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461470371000,
      "finishAt": 1461507554000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461507067000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 8,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461474495000,
      "finishAt": 1461474502000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461474481000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 7,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461473681000,
      "finishAt": 1461473687000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461473673000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 6,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461473632000,
      "finishAt": 1461473637000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461473626000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 5,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461473096000,
      "finishAt": 1461473105000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461473087000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 4,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461473027000,
      "finishAt": 1461473050000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461473003000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 3,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461472424000,
      "finishAt": 1461472874000,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 80,
      "actualGroup": 10,
      "actualBreakTime": 300,
      "actualConsumTime": 500,
      "status": 3,
      "createAt": 1461472105000,
      "content": "运动预设目标: \n在 8 内完成 80 个 伏地挺身;\n选择 室外/操场 环境，计划平均休息 30 秒;\n选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 1,
      "items": []
    },
    {
      "trainingID": 2,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461470371000,
      "finishAt": 0,
      "presetCount": 80,
      "presetGroup": 8,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 0,
      "actualGroup": 0,
      "actualBreakTime": 0,
      "actualConsumTime": 0,
      "status": 1,
      "createAt": 1461471753000,
      "content": "运动目标: \n在 8 内完成 80 个 伏地挺身;\n由于你在 室外/操场 环境中，并平均休息 30 秒;\n同时你选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n根据你实际的完成结果来最终评定你的得分",
      "title": "伏地挺身 #8G-640",
      "isSuccess": 0,
      "items": []
    },
    {
      "trainingID": 1,
      "userID": 10000000,
      "type": 1,
      "beginAt": 1461470371000,
      "finishAt": 0,
      "presetCount": 80,
      "presetGroup": 30,
      "perBreakTime": 30,
      "location": 1,
      "drinking": 1,
      "gear": 1,
      "actualCount": 0,
      "actualGroup": 0,
      "actualBreakTime": 0,
      "actualConsumTime": 0,
      "status": 1,
      "createAt": 1461470431000,
      "content": "运动目标: \n在 30 内完成 80 个 伏地挺身;\n由于你在 室外/操场 环境中，并平均休息 30 秒;\n同时你选择使用 NIKE 护具和 肌肉科技 作为能量补充;\n根据你实际的完成结果来最终评定你的得分",
      "title": "伏地挺身 #30G-2400",
      "isSuccess": 0,
      "items": []
    }
  ],
  "start": "1",
  "more": 0,
  "ts": 1461507939642,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "TrainingPreCreatePage">创建健身任务预加载接口<h4>
##### url 
 
 - /training/start
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|

##### return  
```json  
{
  "types": {
    "1": "伏地挺身",
    "2": "引体向上",
    "3": "仰卧起坐",
    "4": "胸部推举",
    "5": "飞鸟",
    "6": "肩部推举",
    "7": "坐姿划船",
    "8": "腿部外弯",
    "9": "腿部内弯",
    "10": "腿部推蹬",
    "11": "腿部伸展",
    "12": "三头肌训练",
    "13": "二头肌训练"
  },
  "locations": {
    "1": "室外/操场",
    "2": "室内/健身房",
    "3": "室内/自卑健身器材"
  },
  "drinkings": {
    "1": "肌肉科技",
    "2": "其他蛋白粉",
    "3": "脱脂牛奶",
    "4": "功能性饮料",
    "5": "无"
  },
  "gears": {
    "1": "NIKE",
    "2": "McDavid",
    "3": "Decathlon",
    "4": "无"
  },
  "ts": 1461508158283,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "TrainingStartPage">开启健身任务接口<h4>
##### url 
 
 - /training/pre/create
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|training_id|long| 12311|*|运动任务ID|

##### return  
```json  
{
  "ts": 1461508348477,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "ArticleCreatePage">创建文章<h4>
##### url 
 
 - /article/create
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|title|String| 12311|*|文章标题|
|content|long| 12311|*|文章内容|

##### return  
```json  
{
  "ts": 1461508348477,
  "status": 1
}
```
[回到目录](#catalog)

####<h4 id = "ArticleDeletePage">删除文章<h4>
##### url 
 
 - /article/delete
 - api_version = 1
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  | 描述 |
| -------- |--------| -------- | -----    | -------|
| key | String | 31319381038103018301830 | * | 用户key|
|article_id|long| 12311|*|文章ID|

##### return  
```json  
{
  "ts": 1461508348477,
  "status": 1
}
```
[回到目录](#catalog)