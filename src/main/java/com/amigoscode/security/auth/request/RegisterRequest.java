package com.amigoscode.security.auth.request;

import lombok.Builder;

@Builder
public record RegisterRequest(String firstname, String lastName, String email, String password) {
}
