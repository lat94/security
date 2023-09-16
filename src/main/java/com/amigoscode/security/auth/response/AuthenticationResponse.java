package com.amigoscode.security.auth.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
