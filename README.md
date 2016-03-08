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
|名称|作用|规范|  
|--|--|--|  
|domain|数据库模型|类型与数据库表一致，不涉及任何业务具体要求，用于数据库基本操作|  
|entity|业务层基本模型|业务层具体操作对象|  
|info|DTO|接口层基本操作对象，只能向外输出|  
|params|DTO|接口层基本操作对象，只能向内输入 _tips：当输入参数个数较少，不应使用params_|  
|caps|持久化层操作|数据库基本操作，不涉及具体业务，只允许条件选择，不允许分页操作|  
|core|业务层|业务具体实现|  
|service|业务接口层|只向外暴露服务接口，不做任何处理|  
|reference|接口层－业务层 连接模型|连接对象|  
|page|接口层|对客户端暴露接口，兼容接口的升级|  
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

