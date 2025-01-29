package com.example.demo.features.settings.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.features.settings.models.Setting;

@Repository
public interface SettingRepository extends CrudRepository<Setting, Long> {

    Setting findByKey(String key);

    boolean existsByKey(String key);
}
