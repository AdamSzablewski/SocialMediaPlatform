package com.adamszablewski.model;

import com.adamszablewski.model.interfaces.ImageIdentifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagePhoto implements ImageIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String multimediaId;
    private Set<Long> users;
    @OneToOne(cascade = CascadeType.ALL)
    @Lob
    private ImageData image;

}
