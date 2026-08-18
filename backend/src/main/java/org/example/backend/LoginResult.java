package org.example.backend;

public record LoginResult(String token, boolean isAdmin) {}