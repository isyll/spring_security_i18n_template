package com.example.demo.features.settings.dto;

import com.example.demo.features.settings.models.SettingValueType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettingValue {

    private String key;
    private Object value;
    private SettingValueType valueType;
}
