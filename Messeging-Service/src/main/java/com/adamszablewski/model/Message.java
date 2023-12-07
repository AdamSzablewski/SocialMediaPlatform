package com.adamszablewski.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JsonIgnoreProperties("messages")
    private Conversation conversation;
    @Column(name = "recipient_id")
    private Long recipient;
    private String instanceId;
    private Long owner;
    private String message;
    private Long sender;
    private String imageId;
    private LocalDateTime dateSent;




}
