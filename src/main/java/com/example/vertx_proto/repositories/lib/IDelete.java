package com.example.vertx_proto.repositories.lib;

import io.vertx.core.Future;

public interface IDelete<ID> {
	Future<Void> deleteById(ID id);
}
