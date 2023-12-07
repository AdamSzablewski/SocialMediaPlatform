package com.adamszablewski.util.rabbitMqConsumer;

public class RabitMqVariables {

    //@Value("${rabbitmq.queue.name}")
    public static final String QUEUE = "salesOrders";
    //@Value(("${rabbitmq.exchange.name}"))
    public static String EXCHANGE = "salesOrdersExchange";


    public static String ROUTING_KEY = "test1";
}
