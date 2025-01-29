package com.example.demo.features.settings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.repository.SettingRepository;
import com.example.demo.i18n.I18nUtil;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    I18nUtil i18nUtil;

    public Setting getSetting(String key) {
        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            String message = i18nUtil.getMessage("error.setting_not_found");
            throw new ResourceNotFoundException(message);
        }
        setting.setDescription(getDescription(key));
        return setting;
    }

    public String getDescription(String key) {
        return i18nUtil.getMessage("settings.description." + key);
    }
}
