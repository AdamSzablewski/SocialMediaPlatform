package com.adamszablewski.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class FriendListDto {
    private long id;
    private Set<FriendDto> friends;
}
