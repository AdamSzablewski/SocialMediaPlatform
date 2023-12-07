package com.adamszablewski.rabbitMq;


import com.adamszablewski.classes.FriendRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.adamszablewski.rabbitMq.RabbitMqConfig.EXCHANGE_NAME;


@Service
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendFriendRequest(FriendRequest friendRequest){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "frq", friendRequest);
    }
}
