package com.joboffers.domain.login.dto;

public record RegistrationResultDto(
        String id,
        boolean isCreated,
        String username
) {
}
