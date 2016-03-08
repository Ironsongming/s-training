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


####<h4 id = "code_framework">代码概要架构<h4>
1.	业务对象  

|名称|作用| 
|:--|:--|
|domain|数据库模型|  
|entity|业务层基本模型|
|info|DTO|
|params|DTO|
|caps|持久化层操作| 
|core|业务层|  
|service|业务接口层|
|reference|连接模型|
|page|接口层|
 
2.	作用域





####<h4 id = "api_list">接口列表<h4>
1. [测试相关](#test_api_list)

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


####<h4 id = "test_api_list">测试相关接口列表<h4>
1. [接口1](#test_api_1)

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


####<h4 id = "test_api_1">测试接口1<h4>
1. [v1](#test_api_1_v1)

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

####<h4 id = "test_api_1_v1">测试接口1 v1<h4>
##### url 
 
 - /test/aaa/bbb/ccc/ddd
 - api_version = 5
 - status = finished

##### params      
| 参数      | 类型   |  例子     | 是否必须  |
| -------- |--------| -------- | -----    |  
| userName | String | songming | required |
| age      | int    | 12       |          |

##### return   
```json  
{
	"status": 1,
	"ts": 1450871051856
} 
```

