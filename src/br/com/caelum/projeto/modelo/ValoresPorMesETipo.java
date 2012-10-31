package br.com.caelum.projeto.modelo;

import java.math.BigDecimal;

public class ValoresPorMesETipo {

	private int mes;
	
	private int ano;
	
	private BigDecimal total;
	
	public ValoresPorMesETipo() {
	}
	
	public ValoresPorMesETipo(int mes, int ano, BigDecimal total) {
		super();
		this.mes = mes;
		this.ano = ano;
		this.total = total;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getMes() {
		return mes;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getAno() {
		return ano;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
