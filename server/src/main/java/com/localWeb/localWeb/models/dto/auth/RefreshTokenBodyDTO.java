package com.localWeb.localWeb.models.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenBodyDTO {
    private String refreshToken;
}
