package com.adamszablewski.repository;

import com.adamszablewski.classes.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);

    void deleteByEmail(String userEmail);

    boolean existsByEmail(String email);


}
