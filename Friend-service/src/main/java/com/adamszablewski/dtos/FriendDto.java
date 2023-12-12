package com.adamszablewski.dtos;

import com.adamszablewski.classes.FriendList;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FriendDto {
    private long id;
    private long userId;
    private String username;
    private long friendListId;
}
