package com.example.backend.controller.response;

import com.example.core.service.output.UserOutput;

public record UserResponse(
        String id,
        String name
) {

    public static UserResponse from(UserOutput output) {
        return new UserResponse(output.id(), output.name());
    }
}
