package com.example.vertx_proto.repositories.impl.lib;

import com.example.vertx_proto.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseRepository {
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

	protected <T> T saveEntity(T entity) {
		return execute(em -> em.merge(entity), true);
	}

	protected <T, ID> Optional<T> findEntity(Class<T> type, ID id) {
		return execute(em -> Optional.ofNullable(em.find(type, id)), false);
	}

	protected <T> boolean deleteEntity(T entity) {
		execute(em -> {
			em.remove(entity);
			return true;
		}, true);
		return false;
	}

	protected <T, ID> Optional<T> updateById(Consumer<T> updater, ID id, Class<T> classType) {
		return execute(em -> {
			T existing = em.find(classType, id);
			if (existing == null) return Optional.empty();
			updater.accept(existing);
			return Optional.of(existing);
		}, true);
	}
}
