package com.adamszablewski.rabbitMqConsumer;


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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    public static final String SALES_ORDER_CONFIRMATION_QUEUE = "salesOrderQueue";
    public static final String USER_DELETED_QUEUE = "UserDeletionQueue";
    public static final String FRIEND_REQUEST_QUEUE = "FriendRequestQueue";
    public static final String TEXT_MESSAGE_QUEUE = "textMessageQueue";
    public static final String MESSAGE_MEASSAGE_QUEUE = "MessageQueue";
    public static final String EXCHANGE_NAME = "messageExchange";
//    @Bean
//    public Queue queue(){
//        return new Queue(TEXT_MESSAGE_QUEUE, false);
//    }
//    @Bean
//    public Queue salesOrderConfirmationQueue() {
//        return new Queue(SALES_ORDER_CONFIRMATION_QUEUE, false);
//    }
//    @Bean
//    public TopicExchange exchange(){
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue())
//                .to(exchange())
//                .with("test1");
//    }
//    @Bean
//    public Binding bindingSalesOrderQueue(Queue salesOrderQueue, TopicExchange messageExchange) {
//        return BindingBuilder.bind(salesOrderQueue)
//                .to(messageExchange)
//                .with("order.confirmation");
//    }
//
//    @Bean
//    public Binding bindingTextMessageQueue(Queue textMessageQueue, TopicExchange messageExchange) {
//        return BindingBuilder.bind(textMessageQueue)
//                .to(messageExchange)
//                .with("text.message");
//    }
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        typeMapper.setTrustedPackages("*"); // Allow mapping for all packages
//        converter.setJavaTypeMapper(typeMapper);
//        return converter;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter);
//        return rabbitTemplate;
//    }
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
}
