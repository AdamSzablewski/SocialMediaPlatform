package com.adamszablewski.rabbitMq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String USER_DELETED_QUEUE = "UserDeletionQueue";
    public static final String FRIEND_REQUEST_QUEUE = "FriendRequestQueue";

    public static final String EXCHANGE_NAME = "SocialApp";

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
