package ajusteSalarial.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ajusteSalarial.modelo.Desempenho;
import ajusteSalarial.modelo.Funcionario;
import solunit.runner.SolUnitRunner;

@RunWith(SolUnitRunner.class)
public class ReajusteServiceTest {

	private static ReajusteService service;

	private static Funcionario funcionario;

	@Before
	public void setUp(){
		this.service = new ReajusteService();
		this.funcionario = new Funcionario("Fulano", LocalDate.now(), new BigDecimal("2000.00"));
	}

	@Test
	public void reajusteDeveriaSerDeTresPorcentoQuandoDesempenhoForADesejar() {
		service.concederReajuste(this.funcionario, Desempenho.A_DESEJAR);

		Assert.assertEquals(new BigDecimal("2060.00"), funcionario.getSalario());
	}

	@Test
	public void reajusteDeveriaSerDeQuinzePorcentoQuandoDesempenhoForBom() {
		service.concederReajuste(this.funcionario, Desempenho.BOM);

		Assert.assertEquals(new BigDecimal("2300.00"), this.funcionario.getSalario());
	}

	@Test
	public void reajusteDeveriaSerDeVintePorcentoQuandoDesempenhoForOtimo() {
		service.concederReajuste(funcionario, Desempenho.OTIMO);

		Assert.assertEquals(new BigDecimal("2400.00"), funcionario.getSalario());
	}
}
