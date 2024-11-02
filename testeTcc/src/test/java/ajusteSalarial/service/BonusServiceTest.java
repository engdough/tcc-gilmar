package ajusteSalarial.service;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ajusteSalarial.modelo.Funcionario;
import solunit.runner.SolUnitRunner;

@RunWith(SolUnitRunner.class)
public class BonusServiceTest {

	private static BonusService service;

	private static Funcionario funcionarioT;

	private static LocalDate hoje;

	@Before
	public void setUp() throws InterruptedException {
		Thread.sleep(1000);
		this.hoje = LocalDate.now();
		this.service = new BonusService();
		this.funcionarioT = criarFuncionario(new BigDecimal(10000));
	}

	@Test
	public void deveRetornarNomeCorretoDoFuncionario() {
		Assert.assertEquals(this.funcionarioT.getNome(), "Rodrigo");
	}

	@Test
	public void deveRetornarSalarioCorretoDoFuncionario() {
		Assert.assertEquals(this.funcionarioT.getSalario(), new BigDecimal(10000));
	}

	@Test
	public void deveRetornarDataAdmissaoCorretoDoFuncionario() {
		Assert.assertEquals(this.funcionarioT.getDataAdmissao(), this.hoje);
	}

	@Test
	public void deveAlterarNomeCorretamenteDoFuncionario() {
		String novoNome = "Rodrigo T";

		this.funcionarioT.setNome(novoNome);

		Assert.assertEquals(this.funcionarioT.getNome(), novoNome);
	}

	@Test
	public void deveAlterarSalarioCorretamenteDoFuncionario() {

		BigDecimal salarioNovo = new BigDecimal(5000);

		this.funcionarioT.setSalario(salarioNovo);

		Assert.assertEquals(this.funcionarioT.getSalario(), salarioNovo);
	}

	@Test
	public void deveAlterarDataAdmissaoCorretamenteDoFuncionario() {
		LocalDate dataAdmissaoNova = LocalDate.now().minusDays(10);

		this.funcionarioT.setDataAdmissao(dataAdmissaoNova);

		Assert.assertEquals(this.funcionarioT.getDataAdmissao(), dataAdmissaoNova);
	}

	@Test
	public void bonusDeveriaSerZeroParaFuncionarioComSalarioMuitoAlto() {
		try {
			this.funcionarioT = criarFuncionario(new BigDecimal("25000"));
			service.calcularBonus(this.funcionarioT);
			fail("nao deu exception");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Funcionario com salario maior do que R$1000 nao pode receber bonus!", e.getMessage());
		}
	}

	@Test
	public void bonusDeveriaSer10PorCentoDoSalario() {
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assert.assertEquals(new BigDecimal("1000.00"), bonus);
	}

	@Test
	public void bonusDeveriaSerDezPorCentoParaSalarioDeExatamente10000() {
		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assert.assertEquals(new BigDecimal("1000.00"), bonus);
	}

	@Test
	public void bonusDeveriaSerZeroPorCentoParaSalarioDeExatamente0() {
		this.funcionarioT = criarFuncionario(new BigDecimal(0));

		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assert.assertEquals(new BigDecimal("0.00"), bonus);
	}

	@Test
	public void bonusDeveriaSer50ParaSalarioDeExatamente0() {
		this.funcionarioT = criarFuncionario(new BigDecimal(500));

		BigDecimal bonus = service.calcularBonus(this.funcionarioT);

		Assert.assertEquals(new BigDecimal("50.00"), bonus);
	}

	private Funcionario criarFuncionario(BigDecimal salario) {
		return new Funcionario("Rodrigo", this.hoje, salario);
	}
}
