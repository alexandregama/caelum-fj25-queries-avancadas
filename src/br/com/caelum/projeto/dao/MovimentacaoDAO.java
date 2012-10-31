package br.com.caelum.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.projeto.modelo.Movimentacao;

public class MovimentacaoDAO {

	private DAO<Movimentacao> dao;
	
	public MovimentacaoDAO(EntityManager entityManager) {
		this.dao = new DAO<Movimentacao>(entityManager, Movimentacao.class);
	}

	public void adiciona(Movimentacao movimentacao) {
		this.dao.adiciona(movimentacao);
	}

	public List<Movimentacao> lista() {
		return this.dao.lista();
	}

	public void remove(Movimentacao movimentacao) {
		this.dao.remove(movimentacao);
	}

}
