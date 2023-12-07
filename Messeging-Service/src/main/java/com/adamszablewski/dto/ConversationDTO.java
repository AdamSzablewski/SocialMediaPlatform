package com.adamszablewski.dto;



import com.adamszablewski.model.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConversationDTO {

    private long id;
    private long ownerId;
    private long recipientId;
    private List<MessageDTO> messages;

}
