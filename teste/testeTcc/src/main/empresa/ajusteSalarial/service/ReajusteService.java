package ajusteSalarial.service;

import ajusteSalarial.modelo.Desempenho;
import ajusteSalarial.modelo.Funcionario;
import java.math.BigDecimal;

public class ReajusteService {

	public void concederReajuste(Funcionario funcionario, Desempenho desempenho) {
		BigDecimal reajuste = desempenho.percentualReajuste();
		funcionario.reajustarSalario(reajuste);
	}
}
