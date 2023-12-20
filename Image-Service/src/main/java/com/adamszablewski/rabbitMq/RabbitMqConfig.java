package com.adamszablewski.rabbitMq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    public static final String USER_DELETED_QUEUE = "UserDeletionQueue";
    public static final String IMAGE_DELETED_QUEUE = "deleteImage";
    //public static final String TEXT_MESSAGE_QUEUE = "textMessageQueue";
    public static final String MESSAGE_MEASSAGE_QUEUE = "MessageQueue";
    public static final String EXCHANGE_NAME = "BookingMessageExchange";
//    @Bean
//    public Queue queue(){
//        return new Queue(TEXT_MESSAGE_QUEUE);
//    }
    @Bean
    public Queue userDeletionQueue() {
        return new Queue(USER_DELETED_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindUserDeletionQueue(@Qualifier("userDeletionQueue") Queue userDeletionQueue, TopicExchange messageExchange) {
        return BindingBuilder.bind(userDeletionQueue)
                .to(messageExchange)
                .with("udk");
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Include the JavaTimeModule for LocalDate support
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*"); // Allow mapping for all packages
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}
