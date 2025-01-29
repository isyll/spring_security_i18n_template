package com.example.demo.core.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.dto.mapper.SettingMapper;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.models.SettingType;
import com.example.demo.features.settings.repository.SettingRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SettingsLoader {

    private final SettingValue[] settingValues = {
            new SettingValue("school_name", "", SettingType.String),
            new SettingValue("school_address", "", SettingType.String),
            new SettingValue("school_phone", "", SettingType.String),
            new SettingValue("school_email", "", SettingType.String),
            new SettingValue("current_academic_year", "", SettingType.String),
            new SettingValue("language", "fr", SettingType.String),
            new SettingValue("timezone", "Africa/Dakar", SettingType.String),
            new SettingValue("max_login_attempts", 3, SettingType.Integer),
            new SettingValue("enable_email_notifications", false, SettingType.Boolean),
            new SettingValue("backup_frequency", "daily", SettingType.String),
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
