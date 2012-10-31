package br.com.caelum.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;

public class DAO<T> {

	private EntityManager entityManager;
	
	private Class<T> classe;

	public DAO(EntityManager entityManager, Class<T> classe) {
		this.entityManager = entityManager;
		this.classe = classe;
	}

	public void adiciona(T t) {
		this.entityManager.persist(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> lista() {
		return entityManager.createQuery("select t from " + classe.getName() + " t").getResultList();
	}

	public void remove(T t) {
		this.entityManager.remove(t);
	}
	
}
