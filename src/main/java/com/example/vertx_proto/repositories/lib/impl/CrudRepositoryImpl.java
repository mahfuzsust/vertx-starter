package com.example.vertx_proto.repositories.lib.impl;


import com.example.vertx_proto.repositories.lib.CrudRepository;
import io.vertx.core.Future;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class CrudRepositoryImpl<T, ID> extends BaseRepository<T, ID> implements CrudRepository<T, ID> {
	public Future<T> save(T entity) {
		T result = create(entity);
		if (entity == null) {
			return Future.failedFuture(new Exception("Failed to save"));
		}
		return Future.succeededFuture(result);
	}

	public Future<Void> deleteById(ID id) {
		return delete(id) ? Future.succeededFuture() : Future.failedFuture(new Error("Delete failed"));
	}

	@Override
	public Future<T> findById(ID id) {
		Optional<T> entity = getOne(id);
		return entity.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("Entity not found")));
	}

	public abstract Consumer<T> getUpdateEntity(T entity, ID id);

	@Override
	public Future<T> update(T entity, ID id) {
		try {
			Optional<T> t = updateById(getUpdateEntity(entity, id), id, entityClass);
			return t.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("Failed to update")));
		} catch (Exception e) {
			return Future.failedFuture(new Exception("Failed to update"));
		}
	}
}
