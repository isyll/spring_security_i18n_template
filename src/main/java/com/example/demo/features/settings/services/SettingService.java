package com.example.demo.features.settings.services;

import static com.example.demo.core.utils.IterableUtils.iterableToList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.exceptions.ResourceNotFoundException;
import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.dto.mapper.SettingMapper;
import com.example.demo.features.settings.models.Setting;
import com.example.demo.features.settings.repository.SettingRepository;
import com.example.demo.i18n.I18nUtil;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private I18nUtil i18nUtil;

    @Autowired
    private SettingMapper settingMapper;

    public Setting updateSetting(String key, Object value) {
        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            String message = i18nUtil.getMessage("error.setting_not_found");
            throw new ResourceNotFoundException(message);
        }
        setting.setValue(value);
        if (!setting.checkType()) {
            String message = i18nUtil.getMessage("error.setting_type_is_invalid");
            throw new BadRequestException(message);
        }
        settingRepository.save(setting);
        addDescriptionAndName(setting);
        return setting;
    }

    public List<Setting> updateSettings(List<SettingValue> settingValues) {
        List<Setting> settings = new ArrayList<>();
        settingValues.forEach((settingValue) -> {
            String key = settingValue.getKey();
            if (key == null)
                return;
            Setting setting = settingRepository.findByKey(key);
            if (setting == null)
                return;
            settingMapper.updateSettingFromSettingValue(settingValue, setting);
            if (!setting.checkType())
                return;
            settingRepository.save(setting);
            settings.add(setting);
            addDescriptionAndName(setting);
        });
        return settings;
    }

    public Setting getSetting(String key) {
        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            String message = i18nUtil.getMessage("error.setting_not_found");
            throw new ResourceNotFoundException(message);
        }
        addDescriptionAndName(setting);
        return setting;
    }

    public List<Setting> getAllSettings() {
        List<Setting> settings = iterableToList(settingRepository.findAll());
        settings.forEach(this::addDescriptionAndName);
        return settings;
    }

    public List<Setting> getAllSettingsInKeys(List<String> keys) {
        List<Setting> settings = settingRepository.findByKeyIn(keys);
        settings.forEach(this::addDescriptionAndName);
        return settings;
    }

    public void addDescriptionAndName(Setting setting) {
        String key = setting.getKey();
        setting.setDescription(getDescription(key));
        setting.setName(getName(key));
    }

    private String getDescription(String key) {
        return i18nUtil.getMessage("settings.description." + key);
    }

    private String getName(String key) {
        return i18nUtil.getMessage("settings.name." + key);
    }
}
