package experiments.tests.approach;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.tuples.generated.Tuple2;

import experiments.contracts.SimpleAgenda;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

/**
    https://github.com/guisantos/Agenda-in-Solidity
    
 * @author hallan
 *
 */

@ExtendWith(SolUnitRunner.class)
public class TestSimpleAgendaApproach {

	@Contract
	SimpleAgenda agenda;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.agenda.newContact("Teste", stringToByteArray("1234") , "teste@gmail.com").send();
		this.agenda.newContact("Teste 2", stringToByteArray("5678") , "teste@gmail.com").send();
	}
	
	@Test
	public void possible_to_add_a_new_contact() throws Exception{
		this.agenda.newContact("Teste add", stringToByteArray("23") , "teste@gmail.com").send();
	}
	
	@Test
	public void possible_to_remove_a_contact() throws Exception{
		this.agenda.deleteContact( stringToByteArray("1234") ).send();
	}
	
	@Test
	public void possible_to_count_the_contacts() throws Exception{
		BigInteger total = this.agenda.countContacts().send();
		Assertions.assertEquals( 2, total.intValue() );
	}
	
	@Test
	public void possible_to_get_a_number_by_his_index() throws Exception{
		byte[] result = this.agenda.getNumberAtIndex( new BigInteger("1") ).send();
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void possible_to_get_a_contact_by_his_number() throws Exception{
		Tuple2<String, String> result = this.agenda.getContact( stringToByteArray("1234") ).send();
		Assertions.assertNotNull(result);
		Assertions.assertEquals("Teste", result.getValue1());
	}
	
	
	private byte []  stringToByteArray( String number ) throws UnsupportedEncodingException {
		byte[] dst = new byte[32];
		byte[] src = number.getBytes("utf-8");
		System.arraycopy(src, 0, dst, 1, src.length);
		return dst;
	}
}
