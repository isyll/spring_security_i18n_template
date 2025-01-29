package com.example.demo.features.settings.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.features.settings.dto.SettingValue;
import com.example.demo.features.settings.models.Setting;

@Mapper(componentModel = "spring")
public interface SettingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    void updateSettingFromSettingValue(
            SettingValue settingValue, @MappingTarget Setting setting);

}
