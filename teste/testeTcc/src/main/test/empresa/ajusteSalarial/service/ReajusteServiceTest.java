package empresa.ajusteSalarial.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import ajusteSalarial.service.ReajusteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import ajusteSalarial.modelo.Desempenho;
import ajusteSalarial.modelo.Funcionario;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class ReajusteServiceTest {

	private static ReajusteService service;

	private static Funcionario funcionario;

	@BeforeEach
	public void setUp() throws InterruptedException{
		//Thread.sleep(1000);
		this.service = new ReajusteService();
		this.funcionario = new Funcionario("Fulano", LocalDate.now(), new BigDecimal("2000.00"));
	}

	@Test
	public void reajusteDeveriaSerDeTresPorcentoQuandoDesempenhoForADesejar() {
		service.concederReajuste(this.funcionario, Desempenho.A_DESEJAR);

		Assertions.assertEquals(new BigDecimal("2060.00"), funcionario.getSalario());
	}

	@Test
	public void reajusteDeveriaSerDeQuinzePorcentoQuandoDesempenhoForBom() {
		service.concederReajuste(this.funcionario, Desempenho.BOM);

		Assertions.assertEquals(new BigDecimal("2300.00"), this.funcionario.getSalario());
	}

	@Test
	public void reajusteDeveriaSerDeVintePorcentoQuandoDesempenhoForOtimo() {
		service.concederReajuste(funcionario, Desempenho.OTIMO);

		Assertions.assertEquals(new BigDecimal("2400.00"), funcionario.getSalario());
	}
}
