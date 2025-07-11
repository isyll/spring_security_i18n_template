package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SchoolCodeSuggestion(
    String code, @JsonProperty("already_exists") boolean alreadyExists) {}
