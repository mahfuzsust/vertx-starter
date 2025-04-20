package com.example.vertx_proto.services.impl;

import com.example.vertx_proto.*;
import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.services.UserService;
import com.google.protobuf.Empty;
import io.vertx.core.Future;

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Future<CreateUserResponse> createUser(CreateUserRequest user) {
		return userRepository.save(new User(user.getName()))
			.compose(user1 -> {
				CreateUserResponse response = CreateUserResponse.newBuilder()
					.setId(user1.getId())
					.build();
				return Future.succeededFuture(response);
			});
	}

	@Override
	public Future<Empty> updateUser(UpdateUserRequest user) {
		return userRepository.update(new User(user.getName()), user.getId())
			.compose(user1 -> {
				Empty response = Empty.newBuilder().build();
				return Future.succeededFuture(response);
			});
	}

	@Override
	public Future<GetUserResponse> getUser(GetUserRequest getUserRequest) {
		return userRepository.findById(getUserRequest.getId())
			.compose(user -> {
				GetUserResponse response = GetUserResponse.newBuilder()
					.setName(user.getName())
					.build();
				return Future.succeededFuture(response);
			});
	}

	@Override
	public Future<Empty> deleteUser(DeleteUserRequest deleteUserRequest) {
		return userRepository.deleteById(deleteUserRequest.getId())
			.compose(v -> {
				Empty response = Empty.newBuilder().build();
				return Future.succeededFuture(response);
			});
	}
}
