package com.dgsw.environment.dto.request;

public record UpdatePasswordRequest(String oldPassword, String newPassword) {
}
