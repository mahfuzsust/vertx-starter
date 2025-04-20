package com.example.vertx_proto.repositories.lib;

import io.vertx.core.Future;

public interface ICreate<T> {
	Future<T> save(T entity);
}
