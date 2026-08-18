package com.example.backend.controller;

import com.example.backend.controller.request.CreateUserRequest;
import com.example.backend.controller.request.ListByUserIdsRequest;
import com.example.backend.controller.request.UpdateNameRequest;
import com.example.backend.controller.response.UpdateNameResponse;
import com.example.backend.controller.response.UserResponse;
import com.example.core.service.UserService;
import com.example.core.service.input.CreateUserInput;
import com.example.core.service.input.UpdateNameInput;
import com.example.core.service.output.UpdateNameOutput;
import com.example.core.service.output.UserOutput;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@RequestBody CreateUserRequest request) {
    String name = request.name();
    log.debug("ユーザを作成します。ユーザ名 = {}", name);

    CreateUserInput input = new CreateUserInput(name);
    UserOutput output = userService.create(input);

    String id = output.id();
    log.debug("ユーザを作成しました。ID = {}, ユーザ名 = {}", id, name);

    return new UserResponse(id, name);
  }

  @GetMapping
  public List<UserResponse> list() {
    log.debug("ユーザ一覧を取得します。");

    List<UserOutput> outputs = userService.list();
    log.debug("ユーザ一覧を取得しました。件数 = {}", outputs.size());

    return outputs.stream().map(UserResponse::from).toList();
  }

  @PostMapping("/search")
  public List<UserResponse> listByUserIds(@RequestBody ListByUserIdsRequest request) {
    List<String> userIds = request.userIds();
    log.debug("複数ユーザを取得します。件数 = {}", userIds.size());

    List<UserOutput> outputs = userService.listByUserIds(userIds);
    log.debug("複数ユーザを取得しました。件数 = {}", outputs.size());

    return outputs.stream().map(UserResponse::from).toList();
  }

  @GetMapping("/{id}")
  public UserResponse get(@PathVariable String id) {
    log.debug("ユーザを取得します。ID = {}", id);

    UserOutput output = userService.get(id);
    log.debug("ユーザを取得しました。ID = {}, ユーザ名 = {}", id, output.name());

    return UserResponse.from(output);
  }

  @PatchMapping("/{id}")
  public UpdateNameResponse updateName(
      @PathVariable String id, @RequestBody UpdateNameRequest request) {
    String name = request.name();
    log.debug("ユーザを更新します。ID = {}, 更新後ユーザ名 = {}", id, name);

    UpdateNameInput input = new UpdateNameInput(id, name);
    UpdateNameOutput output = userService.updateName(input);
    log.debug("ユーザを更新しました。ID = {}, 更新前ユーザ名 = {}, 更新後ユーザ名 = {}", id, output.beforeName(), name);

    return new UpdateNameResponse(id, output.beforeName(), name);
  }

  @DeleteMapping("/{id}")
  public UserResponse delete(@PathVariable String id) {
    log.debug("ユーザを削除します。ID = {}", id);

    UserOutput output = userService.delete(id);
    log.debug("ユーザを削除しました。ID = {}, ユーザ名 = {}", id, output.name());

    return new UserResponse(id, output.name());
  }
}
