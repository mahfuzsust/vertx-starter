package com.example.vertx.services;

import com.example.vertx.*;
import com.google.protobuf.Empty;
import io.vertx.core.Future;

public interface UserService {
	Future<CreateUserResponse> createUser(CreateUserRequest user);

	Future<Empty> updateUser(UpdateUserRequest user);

	Future<GetUserResponse> getUser(GetUserRequest getUserRequest);

	Future<Empty> deleteUser(DeleteUserRequest deleteUserRequest);
}
