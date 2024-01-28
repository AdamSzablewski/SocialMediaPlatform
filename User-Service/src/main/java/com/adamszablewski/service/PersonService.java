package com.adamszablewski.service;

import com.adamszablewski.model.Person;
import com.adamszablewski.dtos.PersonDto;
import com.adamszablewski.exceptions.IncompleteDataException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.feign.SecurityServiceClient;
import com.adamszablewski.kafka.KafkaMessagePublisher;
import com.adamszablewski.repository.PersonRepository;
import com.adamszablewski.utils.EntityUtils;
import com.adamszablewski.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final EntityUtils entityUtils;
    private final Validator validator;
    private final SecurityServiceClient securityServiceClient;
    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    public PersonDto getPerson(long userId) {
        return entityUtils.mapPersonToDto(personRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new));
    }
    public PersonDto getPerson(String email) {
        return entityUtils.mapPersonToDto(personRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new));
    }
    public void deleteUser(Long userId) {
        personRepository.deleteById(userId);
        kafkaMessagePublisher.sendUserDeletedMessage(userId);

    }
    public void createUser(Person person) {
        boolean valuesValidated = validator.validatePersonValues(person);
        if (!valuesValidated){
            throw new IncompleteDataException();
        }
        String hashedPassword = securityServiceClient.hashPassword(person.getPassword());
        Person newPerson = Person.builder()
                .birthDate(person.getBirthDate())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .username(person.getUsername())
                .email(person.getEmail())
                .password(hashedPassword)
                .build();
        personRepository.save(newPerson);
    }
    public String getUsernameFromId(long userId) {
        Person user = personRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);
        return user.getUsername();
    }
    public long getUserIdForUsername(String email) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);
        return person.getId();
    }
    public String getHashedPassword(String username) {
        Person person = personRepository.findByEmail(username)
                .orElseThrow(NoSuchUserException::new);
        return person.getPassword();
    }
    public String getPhoneNumber(long userId) {
        Person person = personRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);
        return person.getPhoneNumber();
    }
    public void resetPassword(String password, long userId) {
        Person person = personRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);
        person.setPassword(password);
        personRepository.save(person);
    }
}
