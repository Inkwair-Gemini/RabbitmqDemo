package com.example.toServer.service;

import com.example.toServer.model.Announcement;
import com.example.toServer.model.News;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//1.此交换机名不存在，则declareExchange会创建并使用该交换机，若该交换机已存在，直接使用该交换机，队列或绑定关系同理
//2.创建交换机，队列以及绑定关系都需要RabbitAdmin进行declare才会正确的存储到rabbitmq中

@Service
public class NewsService {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    RabbitAdmin rabbitAdmin;

    public void sendFanoutMsg(Announcement announcement){
        rabbitTemplate.convertAndSend("AnnouncementFanoutExchange", null, announcement);
    }
    public void sendMessage(News news){
        String receiver = news.getReceiver();
        rabbitTemplate.convertAndSend("NewsFanoutExchange", null, news);
    }
    public void createAndBindQueue(String clientId) {
        FanoutExchange exchange1 = ExchangeBuilder.fanoutExchange("AnnouncementFanoutExchange").durable(true).build();
        rabbitAdmin.declareExchange(exchange1);

        FanoutExchange exchange2 = ExchangeBuilder.fanoutExchange("NewsFanoutExchange").durable(true).build();
        rabbitAdmin.declareExchange(exchange2);

        Queue newsqueue = new Queue("news" + clientId);
        rabbitAdmin.declareQueue(newsqueue);

        Queue announcementqueue = new Queue("announcement" + clientId);
        rabbitAdmin.declareQueue(announcementqueue);

        Binding bind1 = BindingBuilder.bind(announcementqueue).to(exchange1);
        rabbitAdmin.declareBinding(bind1);

        Binding bind2 = BindingBuilder.bind(newsqueue).to(exchange2);
        rabbitAdmin.declareBinding(bind2);
    }
}
