//package com.adamszablewski;
//
//
//import com.adamszablewski.rabbitMq.RabbitMqProducer;
//import com.adamszablewski.utils.Dao;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase
//@DataJpaTest(properties = "spring.config.name=application-test")
//public class DaoTest {
//
//    Dao dao;
//    @Mock
//    TaskRepository taskRepository;
//    @Mock
//    AppointmentRepository appointmentRepository;
//    @Mock
//    FacilityRepository facilityRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    RabbitMqProducer rabbitMqProducer;
//    @Mock
//    ClientRepository clientRepository;
//
//
//    @BeforeEach
//    void setup(){
//        dao = new Dao(taskRepository, appointmentRepository, facilityRepository, userRepository,
//                rabbitMqProducer, clientRepository);
//    }
//    @Test
//    void deleteTaskTest(){
//        Task task = Task.builder().build();
//
//
//    }
//
//}
