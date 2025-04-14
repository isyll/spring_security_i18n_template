package com.example.demo.controllers;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.models.Role;
import com.example.demo.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Role API", description = "API to manage roles.")
@RestController
@RequestMapping("/access/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get a role by id", description = "This endpoint retrieves a role by its unique identifier.")
    @GetMapping("/{id:\\d+}")
    @Secured({ "VIEW_ROLES" })
    public ResponseEntity<ApiResponse<Role>> getRole(@PathVariable Long id) {
        Role role = roleService.findRoleById(id);
        return ApiResponse.success(role).toReponseEntity();
    }
}
