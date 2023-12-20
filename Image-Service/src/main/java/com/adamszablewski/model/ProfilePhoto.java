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
public class ProfilePhoto implements ImageIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private String multimediaId;
    @OneToOne(cascade = CascadeType.ALL)
    @Lob
    private ImageData image;

    @Override
    public String getMultimediaId() {
        return multimediaId;
    }
}
