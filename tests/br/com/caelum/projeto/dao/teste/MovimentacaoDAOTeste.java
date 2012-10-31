package br.com.caelum.projeto.dao.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

/* 
 * Este código não foi refatorado justamente para os alunos do FJ-25 entenderem o fluxo 
 */

public class MovimentacaoDAOTeste {

	private EntityManager entityManager;
	
	private EntityManagerFactory factory;
	
	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("controlefinancas");
		entityManager = factory.createEntityManager();
		
		removeDados();
	}

	@Test
	public void deveriaRetornarAsMovimentacoesDeUmaConta() throws Exception {
		Conta conta = new Conta();
		conta.setTitular("Marinaldo");
		conta.setBanco("Santander");
		conta.setNumero("09998");
		conta.setAgencia("4132");
		
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setConta(conta);
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setConta(conta);
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(conta);
		
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		entityManager.getTransaction().commit();
		
		List<Movimentacao> movimentacoes = movimentacaoDAO.buscaPorConta(conta);
		
		assertNotNull(movimentacoes);
		assertEquals(2, movimentacoes.size());
	}

	@Test
	public void deveriaRetornarAsMovimentacoesDeDeterminadoTipo() throws Exception {
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		
		entityManager.getTransaction().begin();
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		movimentacaoDAO.adiciona(movimentacaoC);
		entityManager.getTransaction().commit();
		
		List<Movimentacao> movimentacoes = movimentacaoDAO.buscarPorTipo(TipoMovimentacao.ENTRADA);
		
		assertNotNull(movimentacoes);
		assertEquals(2, movimentacoes.size());
	}
	
	@Test
	public void deveriaRetornarAsMovimentacoesDeDeterminadoTipoEValorAbaixoDe800() throws Exception {
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoA.setValor(new BigDecimal("900"));
		
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacaoB.setValor(new BigDecimal("700"));
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacaoC.setValor(new BigDecimal("800"));
		
		entityManager.getTransaction().begin();
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		movimentacaoDAO.adiciona(movimentacaoC);
		entityManager.getTransaction().commit();
		
		List<Movimentacao> movimentacoes = movimentacaoDAO.buscarPorTipoEValorMenor(TipoMovimentacao.ENTRADA, new BigDecimal("800"));
		
		assertNotNull(movimentacoes);
		assertEquals(1, movimentacoes.size());
	}
	
	@Test
	public void deveriaRetornarOValorTotalDasMovimentacoesDeDeterminadaContaMasSomenteSaida() throws Exception {
		Conta conta = new Conta();
		conta.setTitular("Astolfaldo");
		
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoA.setValor(new BigDecimal("900.00"));
		movimentacaoA.setConta(conta);
		
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoB.setValor(new BigDecimal("700.00"));
		movimentacaoB.setConta(conta);
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacaoC.setValor(new BigDecimal("800.00"));
		movimentacaoC.setConta(conta);
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(conta);
		
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		movimentacaoDAO.adiciona(movimentacaoC);
		entityManager.getTransaction().commit();
		
		BigDecimal valorTotal = movimentacaoDAO.valorTotalPorContaETipo(conta, TipoMovimentacao.SAIDA);
		
		assertEquals(new BigDecimal("1600.00"), valorTotal);
	}
	
	@Test
	public void deveriaRetornarOValorTotalDeMovimentacoesPorMesEPorTipoDeMovimentacao() throws Exception {
		Calendar dataDeOutubro = Calendar.getInstance();
		dataDeOutubro.set(2012, 9, 20);
		Movimentacao movimentacaoA = new Movimentacao();
		movimentacaoA.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoA.setValor(new BigDecimal("900.00"));
		movimentacaoA.setData(dataDeOutubro);
		
		Calendar dataDeNovembro = Calendar.getInstance();
		dataDeNovembro.set(2012, 10, 01);
		Movimentacao movimentacaoB = new Movimentacao();
		movimentacaoB.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoB.setValor(new BigDecimal("800.00"));
		movimentacaoB.setData(dataDeNovembro);
		
		Movimentacao movimentacaoC = new Movimentacao();
		movimentacaoC.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacaoC.setValor(new BigDecimal("200.00"));
		movimentacaoC.setData(dataDeNovembro);
		
		entityManager.getTransaction().begin();
		MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(entityManager);
		movimentacaoDAO.adiciona(movimentacaoA);
		movimentacaoDAO.adiciona(movimentacaoB);
		movimentacaoDAO.adiciona(movimentacaoC);
		entityManager.getTransaction().commit();
		
		List<Object[]> valoresPorMes = movimentacaoDAO.buscaValorTotalPorMesEPorTipo(TipoMovimentacao.SAIDA);
		
		for (Object[] obj : valoresPorMes) {
			System.out.println("Mês: " + obj[0] + " Ano:" + obj[1] + " Total: " + obj[2]);
		}
		
		assertEquals(valoresPorMes.get(0)[0], 10);
		assertEquals(valoresPorMes.get(0)[1], 2012);
		assertEquals(valoresPorMes.get(0)[2], new BigDecimal("900.00"));
		
		assertEquals(valoresPorMes.get(1)[0], 11);
		assertEquals(valoresPorMes.get(1)[1], 2012);
		assertEquals(valoresPorMes.get(1)[2], new BigDecimal("1000.00"));
	}
	
	private void removeDados() {
		ManipuladorDeDados manipuladorDeDados = new ManipuladorDeDados(entityManager);
		manipuladorDeDados.removeTodosOsDados();
	}
}


