package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
