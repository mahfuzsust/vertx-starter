package com.example.vertx_proto.repositories.lib;

public interface CrudRepository<T, ID> extends ICreate<T>, IDelete<ID>, IFindById<T, ID>, IUpdate<T, ID> {
}
