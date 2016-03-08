##S-Training 文档

####目录
1. [基本环境](#base_config)
2. [接口列表](#api_list)



####<h4 id = "base_config">基本环境<h4>
1. [mysql 5.5.48](http://www.mysql.com)
2. [redis 3.0.7](http://redis.io)
3. [httpsqs 1.7](http://zyan.cc/httpsqs_1_2)
4. [Tomcat 8.0](http://tomcat.apache.org)
5. [nginx 1.8](http://nginx.org)


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

