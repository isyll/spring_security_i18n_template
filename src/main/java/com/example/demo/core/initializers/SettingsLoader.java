package com.example.demo.core.initializers;

import static com.example.demo.core.constants.AppSettings.settingValues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.dto.mapper.SettingMapper;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.repository.SettingRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SettingsLoader {
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
        log.info("Application settings loaded successfully");
    }
}
