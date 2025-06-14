package src.br.ufsc.ine.leb.roza.retrieval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.exceptions.NegativeRelevantSetSizeException;
import src.br.ufsc.ine.leb.roza.exceptions.RecallLevelOutOfBoundsException;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class RecallLevelTest {

	@Test
	void tenPercentLevel() throws Exception {
		RecallLevel tenPercentLevel = new RecallLevel(1);
		assertEquals(0, tenPercentLevel.getAmountOfElementsInRelevantSet(0));
		assertEquals(0, tenPercentLevel.getAmountOfElementsInRelevantSet(9));
		assertEquals(1, tenPercentLevel.getAmountOfElementsInRelevantSet(10));
		assertEquals(1, tenPercentLevel.getAmountOfElementsInRelevantSet(11));
		assertEquals(1, tenPercentLevel.getAmountOfElementsInRelevantSet(19));
		assertEquals(2, tenPercentLevel.getAmountOfElementsInRelevantSet(20));
		assertEquals(2, tenPercentLevel.getAmountOfElementsInRelevantSet(21));
	}

	@Test
	void fiftyPercentLevel() throws Exception {
		RecallLevel fiftyPercentLevel = new RecallLevel(5);
		assertEquals(0, fiftyPercentLevel.getAmountOfElementsInRelevantSet(0));
		assertEquals(4, fiftyPercentLevel.getAmountOfElementsInRelevantSet(9));
		assertEquals(5, fiftyPercentLevel.getAmountOfElementsInRelevantSet(10));
		assertEquals(5, fiftyPercentLevel.getAmountOfElementsInRelevantSet(11));
		assertEquals(9, fiftyPercentLevel.getAmountOfElementsInRelevantSet(19));
		assertEquals(10, fiftyPercentLevel.getAmountOfElementsInRelevantSet(20));
		assertEquals(10, fiftyPercentLevel.getAmountOfElementsInRelevantSet(21));
	}

	@Test
	void oneeHundredPercentLevel() throws Exception {
		RecallLevel oneHundredPercentPercentLevel = new RecallLevel(10);
		assertEquals(0, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(0));
		assertEquals(9, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(9));
		assertEquals(10, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(10));
		assertEquals(11, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(11));
		assertEquals(19, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(19));
		assertEquals(20, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(20));
		assertEquals(21, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(21));
	}

	@Test
	void zeroPercentLevel() throws Exception {
		RecallLevel oneHundredPercentPercentLevel = new RecallLevel(0);
		assertEquals(0, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(0));
		assertEquals(0, oneHundredPercentPercentLevel.getAmountOfElementsInRelevantSet(1));
	}

	@Test
	void outOfBounds() throws Exception {
		assertThrows(RecallLevelOutOfBoundsException.class, () -> {
			new RecallLevel(-1);
		});
		assertThrows(RecallLevelOutOfBoundsException.class, () -> {
			new RecallLevel(11);
		});
	}

	@Test
	void negativeRelevantSetSize() throws Exception {
		assertThrows(NegativeRelevantSetSizeException.class, () -> {
			new RecallLevel(1).getAmountOfElementsInRelevantSet(-1);
		});
	}

	@Test
	void equals() throws Exception {
		assertEquals(new RecallLevel(1), new RecallLevel(1));
	}

	@Test
	void notEquals() throws Exception {
		assertNotEquals(new RecallLevel(1), new RecallLevel(2));
	}

}
