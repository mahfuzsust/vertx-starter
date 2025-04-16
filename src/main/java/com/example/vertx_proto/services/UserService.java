package com.example.vertx_proto.services;

import com.example.vertx_proto.DeleteUserRequest;
import com.example.vertx_proto.GetUserRequest;
import com.example.vertx_proto.GetUserResponse;
import com.example.vertx_proto.SaveUserRequest;
import com.google.protobuf.Empty;
import io.vertx.core.Future;

public interface UserService {
	Future<Empty> createUser(SaveUserRequest user);

	Future<Empty> updateUser(SaveUserRequest user);

	Future<GetUserResponse> getUser(GetUserRequest getUserRequest);

	Future<Empty> deleteUser(DeleteUserRequest deleteUserRequest);
}
