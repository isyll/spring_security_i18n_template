package com.example.demo.dto.mapper;

import com.example.demo.model.Setting;
import com.example.demo.model.helper.SettingValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SettingMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "school", ignore = true)
  void updateSettingFromSettingValue(SettingValue settingValue, @MappingTarget Setting setting);
}
