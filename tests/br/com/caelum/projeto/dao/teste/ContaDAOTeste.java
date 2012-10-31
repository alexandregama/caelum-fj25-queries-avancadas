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
		
		removeDados();
	}
	
	@Test
	public void deveriaRetornarTodasAsContasCadastradas() throws Exception {
		Conta contaA = new Conta();
		Conta contaB = new Conta();
		Conta contaC = new Conta();
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(contaA);
		contaDAO.adiciona(contaB);
		contaDAO.adiciona(contaC);
		entityManager.getTransaction().commit();
		
		List<Conta> contas = contaDAO.lista();
		
		assertNotNull(contas);
		assertEquals(3, contas.size());
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoPositionParameter() throws Exception {
		Conta novaConta = new Conta();
		novaConta.setTitular("Tiririca");
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(novaConta);
		entityManager.getTransaction().commit();
		
		Conta conta = contaDAO.buscaPorTitular_PositionParameter("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoNamedParameter() throws Exception {
		Conta novaConta = new Conta();
		novaConta.setTitular("Tiririca");
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(novaConta);
		entityManager.getTransaction().commit();
		
		Conta conta = contaDAO.buscaPorTitular_NamedParameter("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}
	
	@Test
	public void deveriaRetornarAContaDeDeterminadoTitular_UsandoTypedQuery() throws Exception {
		Conta novaConta = new Conta();
		novaConta.setTitular("Tiririca");
		
		entityManager.getTransaction().begin();
		ContaDAO contaDAO = new ContaDAO(entityManager);
		contaDAO.adiciona(novaConta);
		entityManager.getTransaction().commit();
		
		Conta conta = contaDAO.buscaPorTitular_TypedQuery("Tiririca");
		
		assertEquals(conta.getTitular(), "Tiririca");
	}

	private void removeDados() {
		ManipuladorDeDados manipuladorDeDados = new ManipuladorDeDados(entityManager);
		manipuladorDeDados.removeTodosOsDados();
	}
}
