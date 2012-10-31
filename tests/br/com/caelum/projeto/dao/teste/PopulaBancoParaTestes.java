package br.com.caelum.projeto.dao.teste;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.projeto.dao.ContaDAO;
import br.com.caelum.projeto.dao.MovimentacaoDAO;
import br.com.caelum.projeto.modelo.Conta;
import br.com.caelum.projeto.modelo.Movimentacao;
import br.com.caelum.projeto.modelo.TipoMovimentacao;

public class PopulaBancoParaTestes {

	private EntityManager entityManager;
	
	private EntityManagerFactory factory;

	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("controlefinancas");
		entityManager = factory.createEntityManager();
	}
	
	@Test
	public void deveriaInserirUmaListaDeContasEMovimentacoesSomenteParaTestes() throws Exception {
		limpaBaseDeDados();
		
		Conta contaA = new Conta();
		contaA.setTitular("Tiririca");
		contaA.setBanco("Itau");
		contaA.setNumero("12345");
		contaA.setAgencia("1234");
		
		Conta contaB = new Conta();
		contaB.setTitular("Michel Teló");
		contaB.setBanco("Bradesco");
		contaB.setNumero("43222");
		contaB.setAgencia("4321");
		
		Conta contaC = new Conta();
		contaC.setTitular("Luan Santana");
		contaC.setBanco("Santander");
		contaC.setNumero("09998");
		contaC.setAgencia("4132");
		
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setDescricao("Terno");
		movimentacaoA.setData(Calendar.getInstance());
		movimentacaoA.setValor(new BigDecimal("1000"));
		movimentacaoA.setConta(contaA);
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setDescricao("Gravata");
		movimentacaoB.setData(Calendar.getInstance());
		movimentacaoB.setValor(new BigDecimal("800"));
		movimentacaoB.setConta(contaA);
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setDescricao("Sapato");
		movimentacaoC.setData(Calendar.getInstance());
		movimentacaoC.setValor(new BigDecimal("1100"));
		movimentacaoC.setConta(contaA);
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacaoD = new Movimentacao();
		movimentacaoD.setDescricao("Carro");
		movimentacaoD.setData(Calendar.getInstance());
		movimentacaoD.setValor(new BigDecimal("15100"));
		movimentacaoD.setConta(contaB);
		movimentacaoD.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacaoE = new Movimentacao();
		movimentacaoE.setDescricao("Bicicleta");
		movimentacaoE.setData(Calendar.getInstance());
		movimentacaoE.setValor(new BigDecimal("2300"));
		movimentacaoE.setConta(contaB);
		movimentacaoE.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoF = new Movimentacao();
		movimentacaoF.setDescricao("Playstation");
		movimentacaoF.setData(Calendar.getInstance());
		movimentacaoF.setValor(new BigDecimal("1200"));
		movimentacaoF.setConta(contaB);
		movimentacaoF.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacaoG = new Movimentacao();
		movimentacaoG.setDescricao("Sinuca");
		movimentacaoG.setData(Calendar.getInstance());
		movimentacaoG.setValor(new BigDecimal("8200"));
		movimentacaoG.setConta(contaB);
		movimentacaoG.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoH = new Movimentacao();
		movimentacaoH.setDescricao("Roupas");
		movimentacaoH.setData(Calendar.getInstance());
		movimentacaoH.setValor(new BigDecimal("3200"));
		movimentacaoH.setConta(contaC);
		movimentacaoH.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(contaA);
		contaDAO.adiciona(contaB);
		contaDAO.adiciona(contaC);
		
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		movimentacaoDAO.adiciona(movimentacaoC);
		movimentacaoDAO.adiciona(movimentacaoD);
		movimentacaoDAO.adiciona(movimentacaoE);
		movimentacaoDAO.adiciona(movimentacaoF);
		movimentacaoDAO.adiciona(movimentacaoG);
		movimentacaoDAO.adiciona(movimentacaoH);
		entityManager.getTransaction().commit();
		
		List<Conta> contas = contaDAO.lista();
		List<Movimentacao> movimentacoes = movimentacaoDAO.lista();
		
		assertEquals(3, contas.size());
		assertEquals(8, movimentacoes.size());
	}
	
	private void limpaBaseDeDados() {
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
