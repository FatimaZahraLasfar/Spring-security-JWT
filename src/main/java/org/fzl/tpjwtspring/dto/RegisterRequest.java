package org.fzl.tpjwtspring.dto;

public record RegisterRequest(
        String email,
        String password
) {
}
