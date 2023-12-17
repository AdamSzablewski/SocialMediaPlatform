package com.adamszablewski.model;

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
public class MessagePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "imageIdentity")
    private String imageId;
    private Set<Long> users;
    @OneToOne(cascade = CascadeType.ALL)
    @Lob
    private ImageData image;

}
