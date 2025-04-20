package com.example.vertx.repositories.impl;

import com.example.vertx.models.User;
import com.example.vertx.repositories.UserRepository;
import com.example.vertx.repositories.lib.impl.CrudRepositoryImpl;

import java.util.function.Consumer;

public class UserRepositoryImpl extends CrudRepositoryImpl<User, Long> implements UserRepository {
	@Override
	public Consumer<User> getUpdateEntity(User entity, Long id) {
		return e -> {
			e.setId(id);
			e.setName(entity.getName());
		};
	}
}
