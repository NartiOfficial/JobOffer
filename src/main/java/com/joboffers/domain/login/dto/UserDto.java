package com.joboffers.domain.login.dto;

public record UserDto(
        String id,
        String password,
        String username
) {
}
