package com.sc.auth.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserDetails {

    private String userId;
    private String username;
    private  String roles;
}
