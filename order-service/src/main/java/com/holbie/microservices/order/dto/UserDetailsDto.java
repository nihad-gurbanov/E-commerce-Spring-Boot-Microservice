package com.holbie.microservices.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    String email;
    String firstName;
    String lastName;
}
