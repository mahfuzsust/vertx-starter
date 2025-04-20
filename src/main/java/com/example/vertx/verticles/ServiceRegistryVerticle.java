package com.example.vertx.verticles;

import com.example.vertx.repositories.UserRepository;
import com.example.vertx.repositories.impl.UserRepositoryImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class ServiceRegistryVerticle extends AbstractVerticle {
	@Override
	public void start() {
		UserRepository service = new UserRepositoryImpl();
		new ServiceBinder(vertx)
			.setAddress(UserRepository.ADDRESS)
			.register(UserRepository.class, service);

	}
}
