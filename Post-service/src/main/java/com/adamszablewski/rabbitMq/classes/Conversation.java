package com.adamszablewski.rabbitMq.classes;


import com.adamszablewski.model.UserClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private UserClass user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "conversation_messages",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private List<Message> messages;

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", userId=" + user +
                '}';
    }
}
