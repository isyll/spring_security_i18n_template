package com.example.demo.dto.response;

import com.example.demo.model.Setting;
import java.util.List;

public record GroupedSettings(
    List<Setting> general,
    List<Setting> security,
    List<Setting> notification,
    List<Setting> ui,
    List<Setting> integration,
    List<Setting> billing,
    List<Setting> academic) {}
