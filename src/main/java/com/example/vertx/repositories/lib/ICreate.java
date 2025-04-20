package com.example.vertx.repositories.lib;

import io.vertx.core.Future;

public interface ICreate<T> {
	Future<T> save(T entity);
}
