package com.yuanfang.mq.queue;

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
        /**
         * 1、Boolean.FALSE（不开启事务） 判断是否开启事务
         * 2、Session.AUTO_ACKNOWLEDGE（自动签收） 判断手动签收（CLIENT_ACKNOWLEDGE）还是自动签收
         */
        Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Destination destination=session.createQueue("yuanfang");
        MessageProducer producer=session.createProducer(destination);
        //不持久化     DeliveryMode.PERSISTENT=持久化
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 5; i++) {
            sendMsg(session,producer,"我是生产者："+i);
            if(i==2){
                int a=i/0;
            }
            session.commit();
            System.out.println("我是生产者："+i);
        }
    }

    static  public  void  sendMsg(Session session,MessageProducer producer,String i) throws JMSException {
        TextMessage textMessage=session.createTextMessage("hello activemq "+i);
        producer.send(textMessage);
    }

}
