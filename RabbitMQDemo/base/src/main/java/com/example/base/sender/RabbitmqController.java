package com.example.base.sender;

import com.example.base.utils.getNowTime;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RabbitmqController {

    @Resource
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Test message, hello world!";
        String createTime = getNowTime.getNowTime();
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //convert:转换
        rabbitTemplate.convertAndSend("directExchange", "directRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicFirstMessage")
    public String sendTopicFirstMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: first";
        String createTime = getNowTime.getNowTime();
        Map<String, Object> firstMap = new HashMap<>();
        firstMap.put("messageId", messageId);
        firstMap.put("messageData", messageData);
        firstMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.first", firstMap);
        return "ok";
    }

    @GetMapping("/sendTopicSecondMessage")
    public String sendTopicSecondMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: second";
        String createTime = getNowTime.getNowTime();
        Map<String, Object> secondMap = new HashMap<>();
        secondMap.put("messageId", messageId);
        secondMap.put("messageData", messageData);
        secondMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.second", secondMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: all";
        String createTime = getNowTime.getNowTime();
        Map<String, Object> fanoutMap = new HashMap<>();
        fanoutMap.put("messageId", messageId);
        fanoutMap.put("messageData", messageData);
        fanoutMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, fanoutMap);
        return "ok";
    }
    //error
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonely DirectExchange test message ";
        String createTime = getNowTime.getNowTime();
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("directExchange", "directRouting2", map);
        return "ok";
    }
}