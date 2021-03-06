package com.yuanfang.mq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

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
       // Destination destination=session.createQueue("yuanfang");
        MessageProducer producer=session.createProducer(null);
        //不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i = 0; i < 5; i++) {
            sendMsg(session,producer,"我是生产者："+i);
            System.out.println("我是生产者："+i);
        }
    }

    static  public  void  sendMsg(Session session,MessageProducer producer,String i) throws JMSException {
        TextMessage textMessage=session.createTextMessage("hello activemq "+i);
        Topic wuqi = session.createTopic("wuqi");
        producer.send(wuqi,textMessage);
    }

}
