package com.example.vertx.repositories.lib.impl;

import com.example.vertx.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseRepository<T, ID> {
	protected final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	protected BaseRepository() {
		this.entityClass = (Class<T>) ((ParameterizedType)
			getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected <R> R execute(Function<EntityManager, R> action, boolean inTransaction) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;
		try (session) {
			if (inTransaction) {
				transaction = session.getTransaction();
				transaction.begin();
			}
			R result = action.apply(session);

			if (inTransaction) {
				transaction.commit();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if (inTransaction && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(e);
		}
	}

	public T create(T entity) {
		return execute(em -> {
			em.persist(entity);
			return entity;
		}, true);
	}

	public T update(T entity) {
		return execute(em -> em.merge(entity), true);
	}

	protected Optional<T> updateById(Consumer<T> updater, ID id, Class<T> classType) {
		return execute(em -> {
			T existing = em.find(classType, id);
			if (existing == null) return Optional.empty();
			updater.accept(existing);
			return Optional.of(existing);
		}, true);
	}

	public Optional<T> getOne(ID id) {
		return execute(em -> Optional.ofNullable(em.find(entityClass, id)), false);
	}

	public List<T> getAll() {
		return execute(em -> em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList(), false);
	}

	public boolean exists(ID id) {
		return execute(em -> em.find(entityClass, id) != null, false);
	}

	public boolean delete(ID id) {
		return execute(em -> {
			T found = em.find(entityClass, id);
			if (found != null) {
				em.remove(found);
				return true;
			}
			return false;
		}, true);
	}

	public <R> List<R> execQuery(String jpql, Class<R> resultClass) {
		return execute(em -> em.createQuery(jpql, resultClass).getResultList(), false);
	}

	public <R> R execQuerySingle(String jpql, Class<R> resultClass) {
		return execute(em -> em.createQuery(jpql, resultClass).getSingleResult(), false);
	}
}
