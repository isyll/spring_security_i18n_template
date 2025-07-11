package com.example.demo.repository;

import com.example.demo.model.School;
import com.example.demo.model.Setting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

  List<Setting> findByKeyIn(List<String> keys);

  Setting findByKey(String key);

  List<Setting> findBySchool(School school);

  Optional<Setting> findBySchoolAndKey(School school, String key);
}
