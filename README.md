# OauthTool

这是第三方认证登陆并获取用户基本信息的小工具类...

微博相关文档 : http://open.weibo.com/  

微信相关文档 : https://open.weixin.qq.com  

QQ相关文档   : http://connect.qq.com/  


Oauth的流程是:
以微信的流程为例..  

用户先访问我们给的一个链接..该链接跳转到微信,微信重定向到我们设置的callback地址..  

当我们的callback地址接收到请求时,我们还会接收到微信传来的code值..  

接着我们带着code以及相关的appid,appserect去获取token...  

然后我们就可以通过token来调用相关的接口了..  


