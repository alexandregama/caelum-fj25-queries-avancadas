package br.com.caelum.projeto.dao.teste;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.projeto.dao.ContaDAO;
import br.com.caelum.projeto.modelo.Conta;

public class ContaDAOTeste {

	private EntityManager entityManager;
	
	private EntityManagerFactory factory;
	
	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("controlefinancas");
		entityManager = factory.createEntityManager();
	}
	
	@Test
	public void deveriaRetornarTodasAsContasCadastradas() throws Exception {
		ContaDAO contaDAO = new ContaDAO(entityManager);
		List<Conta> contas = contaDAO.lista();
		
		assertNotNull(contas);
		assertEquals(3, contas.size());
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoPositionParameter() throws Exception {
		ContaDAO contaDAO = new ContaDAO(entityManager);
		Conta conta = contaDAO.buscaPorTitular_PositionParameter("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoNamedParameter() throws Exception {
		ContaDAO contaDAO = new ContaDAO(entityManager);
		Conta conta = contaDAO.buscaPorTitular_NamedParameter("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoTypedQuery() throws Exception {
		ContaDAO contaDAO = new ContaDAO(entityManager);
		Conta conta = contaDAO.buscaPorTitular_TypedQuery("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}
	
}
