package br.com.caelum.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.projeto.modelo.Conta;

public class ContaDAO {

	private DAO<Conta> dao;
	
	private EntityManager entityManager;
	
	public ContaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.dao = new DAO<Conta>(entityManager, Conta.class);
	}

	public void adiciona(Conta conta) {
		this.dao.adiciona(conta);
	}

	public List<Conta> lista() {
		return this.dao.lista();
	}

	public void remove(Conta conta) {
		this.dao.remove(conta);
	}

	public Conta buscaPorTitular_PositionParameter(String titular) {
		Query query = entityManager.createQuery("select c from Conta c where c.titular = ?1");
		query.setParameter(1, titular);
		
		return (Conta) query.getSingleResult();
	}

	public Conta buscaPorTitular_NamedParameter(String titular) {
		Query query = entityManager.createQuery("select c from Conta c where c.titular = :titular");
		query.setParameter("titular", titular);
		
		return (Conta) query.getSingleResult();
	}
	
	public Conta buscaPorTitular_TypedQuery(String titular) {
		TypedQuery<Conta> query = entityManager.createQuery("select c from Conta c where c.titular = :titular", Conta.class);
		query.setParameter("titular", titular);
		
		return query.getSingleResult();
	}
	
}
