//package com.adamszablewski;
//
//import com.adamszablewski.exceptions.ClientAlreadyCreatedException;
//import com.adamszablewski.model.UserClass;
//import com.adamszablewski.repository.ClientRepository;
//import com.adamszablewski.repository.UserRepository;
//import com.adamszablewski.service.ClientService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase
//@DataJpaTest(properties = "spring.config.name=application-test")
//public class ClientServiceTest {
//
//    ClientService clientService;
//    @Mock
//    ClientRepository clientRepository;
//    @Mock
//    UserRepository userRepository;
//
//    @BeforeEach
//    void setup(){
//        clientService = new ClientService(clientRepository, userRepository);
//    }
//
//    @Test
//    void makeClientFromUserTest_shouldCreateClient(){
//        UserClass user = UserClass.builder()
//                .id(0L)
//                .build();
//        Client client = Client.builder()
//                .user(user)
//                .build();
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        clientService.makeClientFromUser(user.getId());
//
//        verify(clientRepository).save(eq(client));
//
//    }
//    @Test
//    void makeClientFromUserTest_shouldThrowAlreadyCreatedError(){
//        Client client = Client.builder()
//                .id(0L)
//                .build();
//        UserClass user = UserClass.builder()
//                .id(0L)
//                .client(client)
//                .build();
//
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        assertThrows(ClientAlreadyCreatedException.class, ()->{
//            clientService.makeClientFromUser(user.getId());
//        });
//
//    }
//
//}
