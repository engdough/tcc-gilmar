package src.br.ufsc.ine.leb.roza.retrieval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class PrecisionRecallTest {

	private static BigDecimal oneOfTwo;
	private static BigDecimal twoOfThree;
	private static BigDecimal threeOfFour;
	private static BigDecimal fourOfFive;
	private static BigDecimal fiveOfSix;
	private static BigDecimal sixOfSeven;
	private static BigDecimal sevenOfEigth;
	private static BigDecimal eigthOfNine;
	private static BigDecimal nineOfTen;
	private static BigDecimal threeOfFive;
	private static BigDecimal fourOfSeven;
	private static BigDecimal fiveOfNine;
	private static BigDecimal oneOfSix;
	private static BigDecimal twoOfSeven;
	private static BigDecimal threeOfEigth;
	private static BigDecimal fourOfNine;

	private static RecallLevel zeroPercent;
	private static RecallLevel tenPercent;
	private static RecallLevel twentyPercent;
	private static RecallLevel thrirtyPercent;
	private static RecallLevel fortyPercent;
	private static RecallLevel fiftyPercent;
	private static RecallLevel sixtyPercent;
	private static RecallLevel seventyPercent;
	private static RecallLevel eightyPercent;
	private static RecallLevel ninetyPercent;
	private static RecallLevel oneHundredPercent;

	@BeforeEach
	public void bigDecimals() {
		this.oneOfTwo = new BigDecimal(1).divide(new BigDecimal(2), MathContext.DECIMAL32);
		this.twoOfThree = new BigDecimal(2).divide(new BigDecimal(3), MathContext.DECIMAL32);
		this.threeOfFour = new BigDecimal(3).divide(new BigDecimal(4), MathContext.DECIMAL32);
		this.fourOfFive = new BigDecimal(4).divide(new BigDecimal(5), MathContext.DECIMAL32);
		this.fiveOfSix = new BigDecimal(5).divide(new BigDecimal(6), MathContext.DECIMAL32);
		this.sixOfSeven = new BigDecimal(6).divide(new BigDecimal(7), MathContext.DECIMAL32);
		this.sevenOfEigth = new BigDecimal(7).divide(new BigDecimal(8), MathContext.DECIMAL32);
		this.eigthOfNine = new BigDecimal(8).divide(new BigDecimal(9), MathContext.DECIMAL32);
		this.nineOfTen = new BigDecimal(9).divide(new BigDecimal(10), MathContext.DECIMAL32);
		this.threeOfFive = new BigDecimal(3).divide(new BigDecimal(5), MathContext.DECIMAL32);
		this.fourOfSeven = new BigDecimal(4).divide(new BigDecimal(7), MathContext.DECIMAL32);
		this.fiveOfNine = new BigDecimal(5).divide(new BigDecimal(9), MathContext.DECIMAL32);
		this.oneOfSix = new BigDecimal(1).divide(new BigDecimal(6), MathContext.DECIMAL32);
		this.twoOfSeven = new BigDecimal(2).divide(new BigDecimal(7), MathContext.DECIMAL32);
		this.threeOfEigth = new BigDecimal(3).divide(new BigDecimal(8), MathContext.DECIMAL32);
		this.fourOfNine = new BigDecimal(4).divide(new BigDecimal(9), MathContext.DECIMAL32);

		this.zeroPercent = new RecallLevel(0);
		this.tenPercent = new RecallLevel(1);
		this.twentyPercent = new RecallLevel(2);
		this.thrirtyPercent = new RecallLevel(3);
		this.fortyPercent = new RecallLevel(4);
		this.fiftyPercent = new RecallLevel(5);
		this.sixtyPercent = new RecallLevel(6);
		this.seventyPercent = new RecallLevel(7);
		this.eightyPercent = new RecallLevel(8);
		this.ninetyPercent = new RecallLevel(9);
		this.oneHundredPercent = new RecallLevel(10);
	}

//	@BeforeEach
//	public void recallLevels() {
//		this.zeroPercent = new RecallLevel(0);
//		this.tenPercent = new RecallLevel(1);
//		this.twentyPercent = new RecallLevel(2);
//		this.thrirtyPercent = new RecallLevel(3);
//		this.fortyPercent = new RecallLevel(4);
//		this.fiftyPercent = new RecallLevel(5);
//		this.sixtyPercent = new RecallLevel(6);
//		this.seventyPercent = new RecallLevel(7);
//		this.eightyPercent = new RecallLevel(8);
//		this.ninetyPercent = new RecallLevel(9);
//		this.oneHundredPercent = new RecallLevel(10);
//	}

	@Test
	void rankingEqualsRelevantSet() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void missingFirstElement() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(this.twoOfThree, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(this.threeOfFour, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(this.fourOfFive, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(this.fiveOfSix, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(this.sixOfSeven, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(this.sevenOfEigth, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(this.eigthOfNine, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(this.nineOfTen, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void missingLastElement() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'x');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void oneHitOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'x', 'c', 'x', 'e', 'x', 'g', 'x', 'i', 'x');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(this.twoOfThree, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(this.threeOfFive, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(this.fourOfSeven, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(this.fiveOfNine, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void oneMissOneHit() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'b', 'x', 'd', 'x', 'f', 'x', 'h', 'x', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void rankingHalfOfRelevantSetWithOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'x', 'd', 'e');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(this.threeOfFour, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(this.fourOfFive, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void rankingTwiceOfRelevantSetWithOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'x', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(this.threeOfFour, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(this.threeOfFour, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(this.fourOfFive, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(this.fourOfFive, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

	@Test
	void firstHalfOfRankinIncorret() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'x', 'x', 'x', 'x', 'a', 'b', 'c', 'd', 'e');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(this.tenPercent));
		assertEquals(this.oneOfSix, precisionRecall.precisionAtRecallLevel(this.twentyPercent));
		assertEquals(this.oneOfSix, precisionRecall.precisionAtRecallLevel(this.thrirtyPercent));
		assertEquals(this.twoOfSeven, precisionRecall.precisionAtRecallLevel(this.fortyPercent));
		assertEquals(this.twoOfSeven, precisionRecall.precisionAtRecallLevel(this.fiftyPercent));
		assertEquals(this.threeOfEigth, precisionRecall.precisionAtRecallLevel(this.sixtyPercent));
		assertEquals(this.threeOfEigth, precisionRecall.precisionAtRecallLevel(this.seventyPercent));
		assertEquals(this.fourOfNine, precisionRecall.precisionAtRecallLevel(this.eightyPercent));
		assertEquals(this.fourOfNine, precisionRecall.precisionAtRecallLevel(this.ninetyPercent));
		assertEquals(this.oneOfTwo, precisionRecall.precisionAtRecallLevel(this.oneHundredPercent));
	}

}
