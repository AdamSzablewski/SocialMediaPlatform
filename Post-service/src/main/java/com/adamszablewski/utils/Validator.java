//package com.adamszablewski.utils;
//
//import com.adamszablewski.classes.Person;
//import com.adamszablewski.exceptions.UserAlreadyExistException;
//import com.adamszablewski.repository.PersonRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class Validator {
//    private final PersonRepository personRepository;
//    public boolean validateEmail(String email){
//        if (personRepository.existsByEmail(email)){
//            throw new UserAlreadyExistException();
//        }
//        return !email.startsWith("@") && !email.endsWith("@") && email.contains("@");
//    }
//    public boolean validatePersonValues(Person person){
//        return validateEmail(person.getEmail()) ;
//
//    }
//}
