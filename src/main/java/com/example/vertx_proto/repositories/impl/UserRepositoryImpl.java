package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import io.vertx.core.Future;

import java.util.Optional;

public class UserRepositoryImpl extends CrudRepositoryImpl<User, Long> implements UserRepository {
	@Override
	public Future<User> update(User entity, Long id) {
		try {
			Optional<User> t = updateById(e -> {
				e.setId(id);
				e.setName(entity.getName());
			}, id, User.class);
			return t.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("Failed to update")));
		} catch (Exception e) {
			return Future.failedFuture(new Exception("Failed to update"));
		}
	}
}
