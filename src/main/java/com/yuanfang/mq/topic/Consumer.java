package com.yuanfang.mq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) throws JMSException {
        //获取mq连接工程
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //创建连接
        Connection connection=connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话工厂
        Session session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        //创建主题
        Destination destination=session.createTopic("wuqi");
        MessageConsumer consumer = session.createConsumer(destination);

        while (true){
            //监听消息
            TextMessage receive = (TextMessage)consumer.receive();
            if (receive!=null){
                String text = receive.getText();
                System.out.println("消费者获取到的信息："+text);
            }else{
                break;
            }
        }
        System.out.println("消费者消费完成");

    }
}
