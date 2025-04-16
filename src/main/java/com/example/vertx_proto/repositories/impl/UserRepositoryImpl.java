package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import io.vertx.core.Future;

import java.util.Optional;

public class UserRepositoryImpl extends BaseRepository implements UserRepository {
	@Override
	public Future<Void> save(User user) {
		User entity = saveEntity(user);
		if (entity == null) {
			return Future.failedFuture(new Exception("Failed to save user"));
		}
		return Future.succeededFuture();
	}

	@Override
	public Future<User> getById(Long id) {
		Optional<User> user = findById(User.class, id);
		return user.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("User not found")));
	}
}
