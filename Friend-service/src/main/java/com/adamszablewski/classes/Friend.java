package com.adamszablewski.classes;

import com.adamszablewski.interfaces.Identifiable;
import com.adamszablewski.interfaces.UserResource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Friend implements Identifiable, UserResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    @OneToOne
    private FriendList friendList;
    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }

//    @Override
//    public long getUserId() {
//        return userId;
//    }
}
