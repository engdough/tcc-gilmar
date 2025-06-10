package empresa.ajusteSalarial.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDate;

import ajusteSalarial.service.BonusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import ajusteSalarial.modelo.Funcionario;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class BonusServiceTest {

	private static BonusService service;
	private static Funcionario funcionarioT;
	private static LocalDate hoje;

	@BeforeEach
	public void setUp() throws InterruptedException {
		//Thread.sleep(1000);
		this.hoje = LocalDate.now();
		this.service = new BonusService();
		this.funcionarioT = criarFuncionario(new BigDecimal(10000));
	}

	@Test
	public void deveRetornarNomeCorretoDoFuncionario() {
		Assertions.assertEquals(this.funcionarioT.getNome(), "Rodrigo");
	}

	@Test
	public void deveRetornarSalarioCorretoDoFuncionario() {
		Assertions.assertEquals(this.funcionarioT.getSalario(), new BigDecimal(10000));
	}

	@Test
	public void deveRetornarDataAdmissaoCorretoDoFuncionario() {
		Assertions.assertEquals(this.funcionarioT.getDataAdmissao(), this.hoje);
	}

	@Test
	public void deveAlterarNomeCorretamenteDoFuncionario() {
		String novoNome = "Rodrigo T";
		this.funcionarioT.setNome(novoNome);

		Assertions.assertEquals(this.funcionarioT.getNome(), novoNome);
	}

	@Test
	public void deveAlterarSalarioCorretamenteDoFuncionario() {
		BigDecimal salarioNovo = new BigDecimal(5000);
		this.funcionarioT.setSalario(salarioNovo);

		Assertions.assertEquals(this.funcionarioT.getSalario(), salarioNovo);
	}

	@Test
	public void deveAlterarDataAdmissaoCorretamenteDoFuncionario() {
		LocalDate dataAdmissaoNova = LocalDate.now().minusDays(10);
		this.funcionarioT.setDataAdmissao(dataAdmissaoNova);

		Assertions.assertEquals(this.funcionarioT.getDataAdmissao(), dataAdmissaoNova);
	}

	@Test
	public void bonusDeveriaSerZeroParaFuncionarioComSalarioMuitoAlto() {
		try {
			this.funcionarioT = criarFuncionario(new BigDecimal("25000"));
			service.calcularBonus(this.funcionarioT);
			fail("nao deu exception");
		} catch (IllegalArgumentException e) {
			Assertions.assertEquals("Funcionario com salario maior do que R$1000 nao pode receber bonus!", e.getMessage());
		}
	}

	@Test
	public void bonusDeveriaSer10PorCentoDoSalario() {
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assertions.assertEquals(new BigDecimal("1000.00"), bonus);
	}

	@Test
	public void bonusDeveriaSerDezPorCentoParaSalarioDeExatamente10000() {
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assertions.assertEquals(new BigDecimal("1000.00"), bonus);
	}

	@Test
	public void bonusDeveriaSerZeroPorCentoParaSalarioDeExatamente0() {
		this.funcionarioT = criarFuncionario(new BigDecimal(0));
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assertions.assertEquals(new BigDecimal("0.00"), bonus);
	}

	@Test
	public void bonusDeveriaSer50ParaSalarioDeExatamente0() {
		this.funcionarioT = criarFuncionario(new BigDecimal(500));
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assertions.assertEquals(new BigDecimal("50.00"), bonus);
	}

	private Funcionario criarFuncionario(BigDecimal salario) {
		return new Funcionario("Rodrigo", this.hoje, salario);
	}
}
