package com.example.demo.model.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.model.enums.SettingCategory;
import com.example.demo.model.enums.SettingType;

public record SettingValue(
    String key,
    Object value,
    SettingCategory category,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) SettingType type) {}
