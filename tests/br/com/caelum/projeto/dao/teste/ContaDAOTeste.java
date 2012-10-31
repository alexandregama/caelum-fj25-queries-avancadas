package br.com.caelum.projeto.dao.teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContaDAOTeste {

	private EntityManager entityManager;
	
	private EntityManagerFactory factory;
	
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("controlefinancas");
		entityManager = factory.createEntityManager();
	}
}
