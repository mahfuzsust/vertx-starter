package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.models.User;
import com.example.vertx_proto.repositories.UserRepository;
import com.example.vertx_proto.repositories.impl.lib.CrudRepositoryImpl;

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
