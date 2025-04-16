package com.example.vertx_proto.repositories.impl;

import com.example.vertx_proto.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.function.Function;

public abstract class BaseRepository {
	protected <R> R executeInTransaction(Function<EntityManager, R> action) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try (session) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			R result = action.apply(session);
			transaction.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(e);
		}
	}

	protected <R> R execute(Function<EntityManager, R> action) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try (session) {
			return action.apply(session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected <T> T saveEntity(T entity) {
		return executeInTransaction(em -> em.merge(entity));
	}

	protected <T, ID> Optional<T> findEntity(Class<T> type, ID id) {
		return execute(em -> Optional.ofNullable(em.find(type, id)));
	}

	protected <T> boolean deleteEntity(T entity) {
		executeInTransaction(em -> {
			em.remove(entity);
			return true;
		});
		return false;
	}

	protected <T, ID> T updateEntity(T item, ID id) {
		executeInTransaction(em -> {
			Optional<T> entity = (Optional<T>) findEntity(item.getClass(), id);
			if (entity.isPresent()) {
				return em.merge(item);
			} else {
				throw new RuntimeException("Entity not found");
			}
		});
		return null;
	}
}
