package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.enums.EPermission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(
      """
      SELECT DISTINCT p.name FROM Role r
      JOIN r.permissions p
      JOIN r.users u
      WHERE u.id = :userId AND r.school.schoolNumber = :schoolId""")
  List<EPermission> findAllPermissionNamesByUserIdAndSchoolId(
      @Param("userId") Long userId, @Param("schoolId") String schoolId);
}
