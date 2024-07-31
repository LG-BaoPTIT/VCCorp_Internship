package com.ite.authservice.event.messages;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class LockAccountNotification {
    private String email;
    private String name;
}
