package br.com.caelum.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.projeto.modelo.Conta;

public class ContaDAO {

	private DAO<Conta> dao;
	
	public ContaDAO(EntityManager entityManager) {
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

}
