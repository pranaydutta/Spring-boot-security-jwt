package com.sc.auth.model;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    private String token;

}

