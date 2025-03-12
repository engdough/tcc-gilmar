package ajusteSalarial.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Funcionario {

	private String nome;

	private LocalDate dataAdmissao;

	private BigDecimal salario;

	public Funcionario (String nome, LocalDate dataAdmissao, BigDecimal salario) {
		this.nome = nome;
		this.dataAdmissao = dataAdmissao;
		this.salario = salario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataAdmissao() {
		return this.dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public BigDecimal getSalario() {
		return this.salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public void reajustarSalario(BigDecimal percentual) {
		BigDecimal aumento = calcularValorAumento(percentual);
		this.salario = this.salario.add(aumento).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal calcularValorAumento(BigDecimal percentual) {
		return salario.multiply(percentual);
	}
}
