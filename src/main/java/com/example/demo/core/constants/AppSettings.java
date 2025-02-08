package com.example.demo.core.constants;

import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.models.SettingType;

public class AppSettings {
    public static final SettingValue[] settingValues = {
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
}
