package com.example.demo.controller;

import com.example.demo.dto.pagination.PaginationParams;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PaginationResponse;
import com.example.demo.dto.search.UserLookup;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "API to manage users.")
public class UserController {

  @Autowired private UserService userService;

  @Operation(
      summary = "Retrieve users with pagination and sorting",
      description =
          "Returns a paginated list of users based on page number, page size, and sort parameters.")
  @GetMapping
  //  @Secured({"SHOW_USER_DATA", "SHOW_USERS_LIST"})
  public ResponseEntity<ApiResponse<PaginationResponse<User>>> getUsers(PaginationParams params) {
    PaginationResponse<User> response = userService.findUsers(params);
    return ApiResponse.success(response).toResponseEntity();
  }

  @Operation(
      summary = "Retrieve user details by unique identifier",
      description =
          """
        Returns full details of a user identified by one of the following:
        - `id` (UUID or Base62-encoded string)
        - `email`
        - `phone`

        At least one of these fields must be provided. If multiple are provided, priority is:
        1. `id`
        2. `email`
        3. `phone`
        """)
  @GetMapping("/lookup")
  // @Secured({"SHOW_USER_DATA"})
  public ResponseEntity<ApiResponse<User>> getUserData(@ParameterObject UserLookup lookup) {
    User user = userService.lookupUser(lookup);
    return ApiResponse.success(user).toResponseEntity();
  }
}
