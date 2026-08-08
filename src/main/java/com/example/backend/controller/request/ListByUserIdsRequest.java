package com.example.backend.controller.request;

import java.util.List;

public record ListByUserIdsRequest(
        List<String> userIds
) {
}
