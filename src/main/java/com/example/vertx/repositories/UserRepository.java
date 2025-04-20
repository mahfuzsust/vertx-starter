package com.example.vertx.repositories;

import com.example.vertx.models.User;
import com.example.vertx.repositories.lib.CrudRepository;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;

@ProxyGen
@VertxGen
public interface UserRepository extends CrudRepository<User, Long> {
	String ADDRESS = "repository.user";
	Future<User> save(User entity);
	Future<Void> deleteById(Long id);
	Future<User> findById(Long id);
	Future<User> update(User entity, Long id);
}
