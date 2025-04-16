package com.example.vertx_proto.repositories;

import com.example.vertx_proto.models.User;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;

@ProxyGen
@VertxGen
public interface UserRepository {
	Future<Void> save(User foo);
	Future<User> findById(Long id);

	static String ADDRESS = "repository.user";
}
