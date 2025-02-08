package com.example.demo.features.settings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.example.demo.features.settings.models.SettingType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettingValue {

    private String key;
    private Object value;
    @JsonProperty(access = Access.READ_ONLY)
    private SettingType valueType;
}
