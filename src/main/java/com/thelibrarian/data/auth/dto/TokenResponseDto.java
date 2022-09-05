package com.thelibrarian.data.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
}
