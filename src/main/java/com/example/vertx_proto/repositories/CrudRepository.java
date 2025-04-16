package com.example.vertx_proto.repositories;

import io.vertx.core.Future;

public interface CrudRepository<T, ID> {
	Future<T> save(T entity);
	Future<Void> deleteById(ID id);
	Future<T> findById(ID id);
}
