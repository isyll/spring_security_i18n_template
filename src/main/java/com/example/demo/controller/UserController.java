package com.example.demo.controller;

import com.example.demo.core.payload.ApiResponse;
import com.example.demo.core.payload.PaginationResponse;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "API to manage users.")
public class UserController {

  @Autowired private UserService userService;

  @Operation(
      summary = "Retrieve users with pagination and sorting",
      description =
          "Returns a paginated list of users based on page number, page size, and sort parameters.")
  @GetMapping("/all")
  //  @Secured({"SHOW_USER_DATA", "SHOW_USERS_LIST"})
  public ResponseEntity<ApiResponse<PaginationResponse<User>>> getUsers(PaginationParams params) {
    log.info("params : {}, {}, {}", params.getPage(), params.getSize(), params.getSort());
    PaginationResponse<User> response = userService.findUsers(params);
    return ApiResponse.success(response).toResponseEntity();
  }

  @Operation(
      summary = "Retrieve user details by ID",
      description = "Returns the full details of a specific user identified by their unique ID.")
  @GetMapping("/{id:\\d+}")
  // @Secured({"SHOW_USER_DATA"})
  public ResponseEntity<ApiResponse<User>> getUserData(@PathVariable Long id) {
    User user = userService.findById(id);
    return ApiResponse.success(user).toResponseEntity();
  }
}
