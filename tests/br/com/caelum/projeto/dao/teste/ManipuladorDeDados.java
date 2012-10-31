package br.com.caelum.projeto.dao.teste;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.projeto.dao.ContaDAO;
import br.com.caelum.projeto.dao.MovimentacaoDAO;
import br.com.caelum.projeto.modelo.Conta;
import br.com.caelum.projeto.modelo.Movimentacao;

public class ManipuladorDeDados {

	private EntityManager entityManager;

	public ManipuladorDeDados(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void removeTodosOsDados() {
		entityManager.getTransaction().begin();
		removeMovimentacoes();
		removeContas();
		entityManager.getTransaction().commit();		
	}
	
	private void removeContas() {
		ContaDAO contaDAO = new ContaDAO(entityManager);
		List<Conta> contas = contaDAO.lista();
		for (Conta conta : contas) {
			contaDAO.remove(conta);
		}
	}

	private void removeMovimentacoes() {
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		List<Movimentacao> lista = movimentacaoDAO.lista();
		for (Movimentacao movimentacao : lista) {
			movimentacaoDAO.remove(movimentacao);
		}
	}
	
}
