package com.example.demo.controllers;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.core.payload.PaginationResponse;
import com.example.demo.core.utils.CustomProperties;
import com.example.demo.core.utils.StringUtils;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "API to manage users.")
public class UserController {

  @Autowired private CustomProperties customProperties;

  @Autowired private UserService userService;

  @Operation(summary = "Get paginated list of users")
  @GetMapping
  @Secured({"SHOW_USER_DATA", "SHOW_USERS_LIST"})
  public ResponseEntity<ApiResponse<PaginationResponse<User>>> getUsers(
      @RequestParam(name = "page", defaultValue = "1") String pageStr,
      @RequestParam(name = "size", defaultValue = "0") String sizeStr,
      @RequestParam(defaultValue = "key,asc") String sort) {
    int page = StringUtils.tryParseInt(sizeStr, 0);
    int size = StringUtils.tryParseInt(pageStr, 1);
    page = page <= 0 ? 1 : page;
    if (size <= 0) {
      size = customProperties.getPaginationLimit();
    }
    String[] sortParams = sort.split(",");
    PaginationResponse<User> response = userService.findUsers(page, size, sortParams);
    return ApiResponse.success(response).toResponseEntity();
  }

  @Operation(summary = "Get user details")
  @GetMapping("/{id:\\d+}")
  @Secured({"SHOW_USER_DATA"})
  public String getUserData() {
    return "";
  }
}
