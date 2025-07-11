package com.example.demo.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.PaginatedResponse;
import com.example.demo.dto.filter.UserLookup;
import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "User API", value = "/users")
@Tag(name = "User API", description = "API to manage users.")
@RequiredArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;

  @Operation(
      summary = "Retrieve users with pagination and sorting",
      description =
          "Returns a paginated list of users based on page number, page size, and sort parameters.")
  @GetMapping
  @Secured({"SHOW_USERS", "MANAGE_USERS"})
  public ResponseEntity<PaginatedResponse<User>> getUsers(
      @ParameterObject @Valid PaginationParams params) {
    return ok(userService.findUsers(params));
  }

  @Operation(
      summary = "Retrieve user details by unique identifier",
      description =
          """
              Returns full details of a user identified by one of the following:
              - `id`
              - `registration_number`

              At least one of these fields must be provided. If multiple are provided, priority is:
              1. `id`
              2. `registration_number`""")
  @GetMapping("/lookup")
  @Secured({"SHOW_USERS", "MANAGE_USERS"})
  public ResponseEntity<ApiResponse<User>> getUserData(@ParameterObject UserLookup lookup) {
    return ok(userService.lookup(lookup));
  }
}
