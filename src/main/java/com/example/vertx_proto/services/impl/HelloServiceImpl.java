package com.example.vertx_proto.services.impl;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.services.HelloService;
import io.vertx.core.Future;

public class HelloServiceImpl implements HelloService {
	private final UserRepository userRepository;
	public HelloServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Future<HelloResponse> sayHello(HelloRequest request) {
		userRepository.save(new User(request.getName()))
			.onFailure(err -> System.err.println("Failed to save user: " + err.getMessage()));

		return userRepository.findById(1L)
			.compose(user -> {
				HelloResponse response = HelloResponse.newBuilder()
					.setMessage("Hello " + user.getId() + " : " + user.getName())
					.build();

				return Future.succeededFuture(response);
			});
	}
}
