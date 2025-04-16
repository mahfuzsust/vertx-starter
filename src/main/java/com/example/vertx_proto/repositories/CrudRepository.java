package com.example.vertx_proto.repositories;

import io.vertx.core.Future;

public interface CrudRepository<T, ID> {
	String ADDRESS = "";
	Future<T> save(T entity);
	Future<Void> deleteById(ID id);
	Future<T> findById(ID id);
	Future<T> update(T entity, ID id);
}
