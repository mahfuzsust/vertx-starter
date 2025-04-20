package com.example.vertx.repositories.lib;

import io.vertx.core.Future;

public interface IDelete<ID> {
	Future<Void> deleteById(ID id);
}
