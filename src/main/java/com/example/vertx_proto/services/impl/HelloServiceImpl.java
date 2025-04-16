package com.example.vertx_proto.services.impl;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.services.HelloService;
import io.vertx.core.Future;

import java.util.Random;

public class HelloServiceImpl implements HelloService {
	private final UserRepository userRepository;

	public HelloServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Future<HelloResponse> sayHello(HelloRequest request) {
		Future<User> userFuture = userRepository.save(new User(request.getName()))
			.onSuccess(u -> {
				if (u.getId() != 1L) {
					u.setName("Hello " + new Random().nextInt(100));
					userRepository.update(u, u.getId())
						.onFailure(e -> System.err.println("Failed to update: " + e.getMessage()));
					userRepository.deleteById(u.getId())
						.onFailure(e -> System.err.println("Failed to delete: " + e.getMessage()));
				}
			})
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
