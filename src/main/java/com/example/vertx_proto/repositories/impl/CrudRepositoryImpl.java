package com.example.vertx_proto.repositories.impl;


import com.example.vertx_proto.repositories.CrudRepository;
import io.vertx.core.Future;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CrudRepositoryImpl<T, ID> extends BaseRepository implements CrudRepository<T, ID> {
	private final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public CrudRepositoryImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType)
			getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Future<T> save(T entity) {
		T result = saveEntity(entity);
		if (entity == null) {
			return Future.failedFuture(new Exception("Failed to save"));
		}
		return Future.succeededFuture(result);
	}

	public Future<Void> deleteById(ID id) {
		Future<T> byId = findById(id)
			.onSuccess(this::deleteEntity)
			.onFailure(e -> System.err.println("Failed to delete: " + e.getMessage()));
		return byId.map(v -> null);
	}

	@Override
	public Future<T> findById(ID id) {
		Optional<T> entity = findEntity(this.entityClass, id);
		return entity.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("Entity not found")));
	}

	public Future<T> update(Consumer<T> entity, ID id) {
		try {
			Optional<T> t = updateById(entity, id, entityClass);
			return t.map(Future::succeededFuture).orElseGet(() -> Future.failedFuture(new Exception("Failed to update")));
		} catch (Exception e) {
			return Future.failedFuture(new Exception("Failed to update"));
		}
	}
}
