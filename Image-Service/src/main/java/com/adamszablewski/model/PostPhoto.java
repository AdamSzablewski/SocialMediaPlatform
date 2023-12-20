package com.adamszablewski.model;

import com.adamszablewski.model.interfaces.ImageIdentifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPhoto implements ImageIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long postId;
    private String multimediaId;
    private long userId;
    @OneToOne(cascade = CascadeType.ALL)
    @Lob
    private ImageData image;

}
