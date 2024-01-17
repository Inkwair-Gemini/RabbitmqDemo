package com.example.receiver.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RabbitmqReceiver {
    static final Logger LOG = LoggerFactory.getLogger(RabbitmqReceiver.class);

    /**
     * 消费者监听，绑定交换机、队列、路由键
     */
    @RabbitListener(queues = "directQueue")
    public void receiveDirectMsg(Map message) {
        //接收消息message
        LOG.info("Direct模式消费者收到消息: " + message.toString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "topicFirstQueue")
    public void receiveTopicFirstMsg(Map message) {
        //接收消息message
        LOG.info("Topic模式(topicFirstQueue)消费者收到消息: " + message.toString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "topicSecondQueue")
    public void receiveTopicSecondMsg(Map message) {
        //接收消息message
        LOG.info("Topic模式(topicSecondQueue)消费者收到消息: " + message.toString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutFirstQueue")
    public void receiveFanoutFirstMsg(Map message) {
        //接收消息message
        LOG.info("Fanout模式(fanoutFirstQueue)消费者收到消息: " + message.toString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutSecondQueue")
    public void receiveFanoutSecondMsg1(Map message) {
        //接收消息message
        LOG.info("Fanout模式(fanoutSecondQueue1)消费者收到消息: " + message.toString());
    }
}