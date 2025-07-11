package com.example.demo.service;

import com.example.demo.dto.mapper.SettingMapper;
import com.example.demo.dto.response.GroupedSettings;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Setting;
import com.example.demo.model.helper.SettingValue;
import com.example.demo.repository.SettingRepository;
import com.example.demo.utils.Translator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsService extends BaseService {

  private final SettingRepository settingRepository;
  private final SettingMapper settingMapper;
  private final Translator translator;

  public Setting updateSetting(String key, Object value) {
    Setting setting =
        Optional.ofNullable(settingRepository.findByKey(key))
            .orElseThrow(
                () -> new ResourceNotFoundException(translator.t("error.setting_not_found")));

    setting.setValue(value);

    if (setting.isInvalidType()) {
      throw new BadRequestException(translator.t("error.setting_type_is_invalid"));
    }

    settingRepository.save(setting);

    return setting;
  }

  public List<Setting> updateSettings(List<SettingValue> settingValues) {
    List<Setting> updatedSettings = new ArrayList<>();
    if (settingValues == null || settingValues.isEmpty()) {
      return updatedSettings;
    }

    for (SettingValue settingValue : settingValues) {
      String key = settingValue.key();
      if (key == null) {
        continue;
      }

      Setting setting = settingRepository.findByKey(key);
      if (setting == null) {
        continue;
      }

      settingMapper.updateSettingFromSettingValue(settingValue, setting);

      if (setting.isInvalidType()) {
        continue;
      }

      settingRepository.save(setting);
      updatedSettings.add(setting);
    }

    return updatedSettings;
  }

  public List<Setting> getSettings() {
    return settingRepository.findBySchool(currentSchool());
  }

  public GroupedSettings getSettingsGroupedByCategory() {
    List<Setting> settings = settingRepository.findBySchool(currentSchool());

    List<Setting> general = new ArrayList<>();
    List<Setting> security = new ArrayList<>();
    List<Setting> notification = new ArrayList<>();
    List<Setting> ui = new ArrayList<>();
    List<Setting> academic = new ArrayList<>();
    List<Setting> billing = new ArrayList<>();
    List<Setting> integration = new ArrayList<>();

    settings.forEach(
        setting -> {
          switch (setting.getCategory()) {
            case GENERAL -> general.add(setting);
            case SECURITY -> security.add(setting);
            case NOTIFICATION -> notification.add(setting);
            case UI -> ui.add(setting);
            case ACADEMIC -> academic.add(setting);
            case BILLING -> billing.add(setting);
            case INTEGRATION -> integration.add(setting);
          }
        });

    return new GroupedSettings(general, security, notification, ui, integration, billing, academic);
  }
}
