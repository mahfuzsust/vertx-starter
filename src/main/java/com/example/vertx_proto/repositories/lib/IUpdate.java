package com.example.vertx_proto.repositories.lib;

import io.vertx.core.Future;

public interface IUpdate<T, ID> {
	Future<T> update(T entity, ID id);
}
