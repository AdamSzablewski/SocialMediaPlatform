package com.adamszablewski.utils;



import com.adamszablewski.model.Person;
import com.adamszablewski.dtos.PersonDto;
import com.adamszablewski.interfaces.Identifiable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class EntityUtils {
    public static  <T extends Identifiable> Set<Long> convertObjectListToIdSet(Collection<T> collection){
        return collection.stream()
                .map(Identifiable::getId)
                .collect(Collectors.toSet());
    }
    public static <T extends Identifiable> Long convertObjectToId(T entity){
        return entity.getId();
    }

    public PersonDto mapPersonToDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .birthDate(person.getBirthDate())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }
}
