package com.example.vertx.repositories.lib;

import io.vertx.core.Future;

public interface IFindById<T, ID> {
	Future<T> findById(ID id);
}
