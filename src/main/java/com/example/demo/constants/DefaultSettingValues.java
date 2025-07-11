package com.example.demo.constants;

import com.example.demo.model.enums.SettingCategory;
import com.example.demo.model.enums.SettingType;
import com.example.demo.model.helper.SettingValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultSettingValues {

  public final SettingValue[] settingValues = {
    new SettingValue("school_name", "", SettingCategory.GENERAL, SettingType.String),
    new SettingValue("school_address", "", SettingCategory.GENERAL, SettingType.String),
    new SettingValue("school_phone", "", SettingCategory.GENERAL, SettingType.String),
    new SettingValue("school_email", "", SettingCategory.GENERAL, SettingType.String),
    new SettingValue("current_academic_year", "", SettingCategory.ACADEMIC, SettingType.String),
    new SettingValue("language", "fr", SettingCategory.UI, SettingType.String),
    new SettingValue("timezone", "Africa/Dakar", SettingCategory.UI, SettingType.String),
    new SettingValue("max_login_attempts", 3, SettingCategory.SECURITY, SettingType.Integer),
    new SettingValue(
        "enable_email_notifications", false, SettingCategory.NOTIFICATION, SettingType.Boolean),
    new SettingValue("backup_frequency", "daily", SettingCategory.SECURITY, SettingType.String)
  };
}
