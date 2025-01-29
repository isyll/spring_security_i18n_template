package com.example.demo.core.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.dto.mapper.SettingMapper;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.models.SettingValueType;
import com.example.demo.features.settings.repository.SettingRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SettingsLoader {

    private final SettingValue[] settingValues = {
            new SettingValue("school_name", "", SettingValueType.String),
            new SettingValue("school_address", "", SettingValueType.String),
            new SettingValue("school_phone", "", SettingValueType.String),
            new SettingValue("school_email", "", SettingValueType.String),
            new SettingValue("current_academic_year", "", SettingValueType.String),
            new SettingValue("language", "fr", SettingValueType.String),
            new SettingValue("timezone", "Africa/Dakar", SettingValueType.String),
            new SettingValue("max_login_attempts", 3, SettingValueType.Integer),
            new SettingValue("enable_email_notifications", false, SettingValueType.Boolean),
            new SettingValue("backup_frequency", "daily", SettingValueType.String),
    };

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingMapper settingMapper;

    @PostConstruct
    public void load() {
        log.info("Loading application settings...");
        try {
            for (SettingValue settingValue : settingValues) {
                Setting setting = settingRepository.findByKey(settingValue.getKey());
                if (setting == null) {
                    setting = new Setting();
                }
                settingMapper.updateSettingFromSettingValue(settingValue, setting);
                settingRepository.save(setting);
            }
        } catch (Exception e) {
            log.error("Error loading application settings: ", e.getMessage());
        }
    }
}
