# apiCheck
ApiCheck旨在为各个基于Spring应用提供一个内部外部的接口测试和验证。

# 接入方式
- springboot应用直接引入jar包依赖如下
```
<dependency>
     <groupId>cn.fireface.check</groupId>
     <artifactId>core-springboot</artifactId>
     <version>1.0-SNAPSHOT</version>
</dependency>
```
- springMVC和struts应用直接引入jar包依赖如下
```
<dependency>
     <groupId>cn.fireface.check</groupId>
     <artifactId>core</artifactId>
     <version>1.0-SNAPSHOT</version>
</dependency>
```
- IP限制
ipaCheck默认没有ip限制的，但是有一些应用上线之后是外网环境，不希望暴露apiCheck，可以配置IP，在固定的ip上才能访问apiCheck，添加IP限制的方式如下  
创建文件：WEB-INF/api/check/config.properties  
创建属性：api.check.pass.ip 对应值就是IP，多个IP用英文逗号分隔，如：api.check.pass.ip=127.0.0.1,127.0.0.2,127.0.0.3  
有这个文件和属性配置之后，apiCheck会启动IP限制，即只有在配置的ip上才能访问aipCheck  
- 访问方式
应用的域名后加上/check/index.html即可访问，即web_url_domain/check/index.html  
如：crm.fireface.cn/check/index.html

