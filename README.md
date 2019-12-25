验证Spring boot RabbitMq方案

  
验证：
   
    (1) 执行distribute-message-demo 端口8080
   
    (2) 验证Direct Exchange
        curl http://localhost:8080/static/send
        Queue/Exchange/Consumer都是启动时就建立好的
        可以看到日志打印，消费者成功收到消息并打印
   
    (3) 验证Topic Exchange
        Queue/Exchange/Consumer都是动态创建的
        curl http://localhost:8080/dynamic/createconsumer
        创建消费者
        
        curl http://localhost:8080/dynamic/send
        发送消息
        可以看到日志打印，消费者成功收到消息并打印
   
 
