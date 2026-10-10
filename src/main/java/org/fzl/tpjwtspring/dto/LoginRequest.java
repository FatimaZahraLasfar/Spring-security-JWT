package org.fzl.tpjwtspring.dto;

public record LoginRequest(
        String email,
        String password
) {
}
