//package com.adamszablewski;
//
//import com.adamszablewski.exceptions.EmployeeAlreadyCreatedException;
//import com.adamszablewski.model.UserClass;
//import com.adamszablewski.repository.EmployeeRepository;
//import com.adamszablewski.repository.UserRepository;
//import com.adamszablewski.service.EmployeeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalTime;
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
//public class EmployeeServiceTest {
//
//    EmployeeService employeeService;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    EmployeeRepository employeeRepository;
//
//    @BeforeEach
//    void setup(){
//        employeeService = new EmployeeService(userRepository, employeeRepository);
//    }
//    @Test
//    void makeEmployeeFromUserTest_shouldAddEmployeeToUser(){
//        UserClass user = UserClass.builder()
//                .id(0L)
//                .build();
//        Employee employee = Employee.builder()
//                .user(user)
//                .startTime(LocalTime.of(9, 0))
//                .endTime(LocalTime.of(17, 0))
//                .build();
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        employeeService.makeEmployeeFromUser(user.getId());
//
//        verify(employeeRepository).save(eq(employee));
//    }
//    @Test
//    void makeEmployeeFromUserTest_shouldThrowException(){
//        UserClass user = UserClass.builder()
//                .id(0L)
//                .build();
//        Employee employee = Employee.builder()
//                .user(user)
//                .startTime(LocalTime.of(9, 0))
//                .endTime(LocalTime.of(17, 0))
//                .build();
//        user.setEmployee(employee);
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        assertThrows(EmployeeAlreadyCreatedException.class, ()->{
//            employeeService.makeEmployeeFromUser(user.getId());
//        });
//    }
//}
