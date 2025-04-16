package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import io.vertx.core.Future;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
	private final Map<Long, User> store = new HashMap<>();

	@Override
	public Future<Void> save(User foo) {
		store.put(foo.getId(), foo);
		return Future.succeededFuture();
	}

	@Override
	public Future<User> findById(Long id) {
		return Future.succeededFuture(store.get(id));
	}
}
