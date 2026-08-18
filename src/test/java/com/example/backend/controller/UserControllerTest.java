package com.example.backend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.backend.controller.request.CreateUserRequest;
import com.example.backend.controller.request.ListByUserIdsRequest;
import com.example.backend.controller.request.UpdateNameRequest;
import com.example.backend.controller.response.UpdateNameResponse;
import com.example.backend.controller.response.UserResponse;
import com.example.core.service.UserService;
import com.example.core.service.output.UpdateNameOutput;
import com.example.core.service.output.UserOutput;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired private UserController userController;

  @MockitoBean private UserService userService;

  @Test
  void create() {
    when(userService.create(any())).thenReturn(new UserOutput("1", "test"));

    CreateUserRequest request = new CreateUserRequest("test");
    UserResponse response = userController.create(request);
    assertEquals("1", response.id());
    assertEquals("test", response.name());
  }

  @Test
  void list() {
    when(userService.list()).thenReturn(List.of(new UserOutput("1", "test")));

    List<UserResponse> response = userController.list();
    assertEquals(1, response.size());
  }

  @Test
  void listByUserIds() {
    when(userService.listByUserIds(any())).thenReturn(List.of(new UserOutput("1", "test")));

    ListByUserIdsRequest request = new ListByUserIdsRequest(List.of("1"));
    List<UserResponse> response = userController.listByUserIds(request);
    assertEquals(1, response.size());
  }

  @Test
  void get() {
    when(userService.get("1")).thenReturn(new UserOutput("1", "test"));

    UserResponse response = userController.get("1");
    assertEquals("1", response.id());
    assertEquals("test", response.name());
  }

  @Test
  void updateName() {
    when(userService.updateName(any())).thenReturn(new UpdateNameOutput("1", "before", "after"));

    UpdateNameRequest request = new UpdateNameRequest("after");
    UpdateNameResponse response = userController.updateName("1", request);
    assertEquals("1", response.id());
    assertEquals("before", response.beforeName());
    assertEquals("after", response.afterName());
  }

  @Test
  void delete() {
    when(userService.delete(any())).thenReturn(new UserOutput("1", "deleted"));

    UserResponse response = userController.delete("1");
    assertEquals("1", response.id());
    assertEquals("deleted", response.name());
  }
}
