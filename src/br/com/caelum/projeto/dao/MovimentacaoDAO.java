package br.com.caelum.projeto.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.projeto.modelo.Conta;
import br.com.caelum.projeto.modelo.Movimentacao;
import br.com.caelum.projeto.modelo.TipoMovimentacao;
import br.com.caelum.projeto.modelo.ValoresPorMesETipo;

public class MovimentacaoDAO {

	private EntityManager entityManager;
	
	private DAO<Movimentacao> dao;
	
	public MovimentacaoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
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

	public List<Movimentacao> buscaPorConta(Conta conta) {
		TypedQuery<Movimentacao> query = entityManager.createQuery("select m from Movimentacao m where m.conta = :conta", Movimentacao.class);
		query.setParameter("conta", conta);
		List<Movimentacao> movimentacoes = query.getResultList();
		
		return movimentacoes;
	}

	public List<Movimentacao> buscarPorTipo(TipoMovimentacao tipo) {
		TypedQuery<Movimentacao> query = entityManager.createQuery("select m from Movimentacao m where m.tipoMovimentacao = :tipo", Movimentacao.class);
		query.setParameter("tipo", tipo);
		List<Movimentacao> movimentacoes = query.getResultList();
		
		return movimentacoes;
	}

	public List<Movimentacao> buscarPorTipoEValorMenor(TipoMovimentacao tipo, BigDecimal valor) {
		TypedQuery<Movimentacao> query = entityManager.createQuery("select m from Movimentacao m where m.tipoMovimentacao = :tipo and m.valor < :valor", Movimentacao.class);
		query.setParameter("tipo", tipo);
		query.setParameter("valor", valor);
		List<Movimentacao> movimentacoes = query.getResultList();
		
		return movimentacoes;
	}

	public BigDecimal valorTotalPorContaETipo(Conta conta, TipoMovimentacao tipo) {
		TypedQuery<BigDecimal> query = entityManager.createQuery("select sum(m.valor) from Movimentacao m where m.tipoMovimentacao = :tipo and m.conta = :conta", BigDecimal.class);
		query.setParameter("tipo", tipo);
		query.setParameter("conta", conta);
		BigDecimal valorTotal = query.getSingleResult();
		
		return valorTotal;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> buscaValorTotalPorMesEPorTipo(TipoMovimentacao tipo) {
		Query query = entityManager.createQuery(
				"select " +
				"	month(m.data)," +
				"	year(m.data)," +
				"	sum(m.valor) " +
				"from " +
				"	Movimentacao m " +
				"where " +
				"	m.tipoMovimentacao = :tipo " +
				"group by " +
				"	month(m.data), " +
				"	year(m.data)");
		query.setParameter("tipo", tipo);
		
		return (List<Object[]>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ValoresPorMesETipo> buscaValorTotalPorMesEPorTipo_EncapsulandoEmClasseAuxiliar(TipoMovimentacao tipo) {
		Query query = entityManager.createQuery(
				"select " +
				"	month(m.data)," +
				"	year(m.data)," +
				"	sum(m.valor) " +
				"from " +
				"	Movimentacao m " +
				"where " +
				"	m.tipoMovimentacao = :tipo " +
				"group by " +
				"	month(m.data), " +
				"	year(m.data)");
		query.setParameter("tipo", tipo);
		List<Object[]> resultados = query.getResultList();
		
		List<ValoresPorMesETipo> valores = new ArrayList<ValoresPorMesETipo>();
		for (Object[] obj : resultados) {
			ValoresPorMesETipo valorPorMesETipo = new ValoresPorMesETipo();
			valorPorMesETipo.setMes((Integer) obj[0]);
			valorPorMesETipo.setAno((Integer) obj[1]);
			valorPorMesETipo.setTotal((BigDecimal) obj[2]);
			
			valores.add(valorPorMesETipo);
		}
		
		return valores;
	}
	
	@SuppressWarnings("unchecked")
	public List<ValoresPorMesETipo> buscaValorTotalPorMesEPorTipo_RetornandoListaSemParse(TipoMovimentacao tipo) {
		Query query = entityManager.createQuery(
				"select new br.com.caelum.projeto.modelo.ValoresPorMesETipo(" +
				"	month(m.data)," +
				"	year(m.data)," +
				"	sum(m.valor)) " +
				"from " +
				"	Movimentacao m " +
				"where " +
				"	m.tipoMovimentacao = :tipo " +
				"group by " +
				"	month(m.data), " +
				"	year(m.data)");
		query.setParameter("tipo", tipo);
		List<ValoresPorMesETipo> valores = query.getResultList();
		
		return valores;
	}

}
