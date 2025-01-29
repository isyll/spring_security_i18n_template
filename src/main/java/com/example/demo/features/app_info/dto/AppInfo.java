package com.example.demo.features.app_info.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppInfo {

    private String name;
    private String version;
    private String description;
    @JsonProperty("supported_locales")
    private List<String> supportedLocales;
}
