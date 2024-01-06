package com.adamszablewski.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<Post> posts = new ArrayList<>();
    private long userId;
}
