package com.uday.authservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse<T> {

    private boolean success;

    private String message;

    private T data;

}
