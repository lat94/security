package com.amigoscode.security.auth.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {
}
