package com.adamszablewski.rabbitMq.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToMany
    @JsonIgnoreProperties("messages")
    private List<Conversation> conversations;
    private String message;
    private String sender;
    private List<Long> receivers;
    private LocalDateTime dateSent;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receivers=" + receivers +
                ", dateSent=" + dateSent +
                '}';
    }
}
