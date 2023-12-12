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
    public static final String FRIEND_REQUEST_QUEUE = "FriendRequestQueue";
    public static final String MESSAGE_MEASSAGE_QUEUE = "MessageQueue";
    public static final String EXCHANGE_NAME = "SocialApp";

    @Bean
    public Queue friendRequestQueue() {
        return new Queue(FRIEND_REQUEST_QUEUE);
    }
    @Bean
    public Queue messageObjectQueue() {
        return new Queue(MESSAGE_MEASSAGE_QUEUE);
    }
    @Bean
    public Binding bindFriendRequestQueue(@Qualifier("friendRequestQueue") Queue friendRequestQueue,
                                          @Qualifier("exchange") TopicExchange messageExchange) {
        return BindingBuilder.bind(friendRequestQueue)
                .to(messageExchange)
                .with("frq");
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue())
//                .to(exchange())
//                .with("test1");
//    }
//    @Bean
//    public Binding bindFriendRequestQueue(@Qualifier("friendRequestQueue") Queue friendRequestQueue, TopicExchange messageExchange) {
//        return BindingBuilder.bind(friendRequestQueue)
//                .to(messageExchange)
//                .with("frq");
//    }
//    @Bean
//    public Binding bindingSalesOrderQueue(@Qualifier("appointmentQueue") Queue appointmentQueue, TopicExchange messageExchange) {
//        return BindingBuilder.bind(appointmentQueue)
//                .to(messageExchange)
//                .with("alley");
//    }


//    @Bean
//    public Binding bindingTextMessageQueue(@Qualifier("queue") Queue textMessageQueue, TopicExchange messageExchange) {
//        return BindingBuilder.bind(textMessageQueue)
//                .to(messageExchange)
//                .with("text.message");
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

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}
