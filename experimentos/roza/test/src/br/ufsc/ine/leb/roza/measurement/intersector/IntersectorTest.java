package src.br.ufsc.ine.leb.roza.measurement.intersector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class IntersectorTest {

	private static Interval oneToTen;
	private static Interval oneToFive;
	private static Interval tenToTen;
	private static Interval oneToThree;
	private static Interval eightToTen;
	private static Interval fourToSeven;
	private static Interval threeToSeven;
	private static Intersector intersector;

	@BeforeEach
	void setup() {
		this.oneToTen = new Interval(1, 10);
		this.oneToFive = new Interval(1, 5);
		this.tenToTen = new Interval(10, 10);
		this.oneToThree = new Interval(1, 3);
		this.eightToTen = new Interval(8, 10);
		this.threeToSeven = new Interval(3, 7);
		this.fourToSeven = new Interval(4, 7);
		this.intersector = new Intersector(10);
	}

	@Test
	void withoutIntersection() throws Exception {
		assertEquals(BigDecimal.ZERO, this.intersector.evaluate());
		assertEquals(0, this.intersector.getIntervals().size());
	}

	@Test
	void oneFullSegment() throws Exception {
		this.intersector.addSegment(1, 10);
		assertEquals(BigDecimal.ONE, this.intersector.evaluate());
		assertEquals(1, this.intersector.getIntervals().size());
		assertEquals(this.oneToTen, this.intersector.getIntervals().get(0));
	}

	@Test
	void oneSegmentInTheBegin() throws Exception {
		this.intersector.addSegment(1, 5);
		assertEquals(new BigDecimal("0.5"), this.intersector.evaluate());
		assertEquals(1, this.intersector.getIntervals().size());
		assertEquals(this.oneToFive, this.intersector.getIntervals().get(0));
	}

	@Test
	void oneSegmentInTheEnd() throws Exception {
		this.intersector.addSegment(10, 10);
		assertEquals(new BigDecimal("0.1"), this.intersector.evaluate());
		assertEquals(1, this.intersector.getIntervals().size());
		assertEquals(this.tenToTen, this.intersector.getIntervals().get(0));
	}

	@Test
	void twoSegments() throws Exception {
		this.intersector.addSegment(1, 3);
		this.intersector.addSegment(8, 10);
		assertEquals(new BigDecimal("0.6"), this.intersector.evaluate());
		assertEquals(2, this.intersector.getIntervals().size());
		assertEquals(this.oneToThree, this.intersector.getIntervals().get(0));
		assertEquals(this.eightToTen, this.intersector.getIntervals().get(1));
	}

	@Test
	void twoSegmentsWithOverlap() throws Exception {
		this.intersector.addSegment(4, 6);
		this.intersector.addSegment(5, 7);
		assertEquals(new BigDecimal("0.4"), this.intersector.evaluate());
		assertEquals(1, this.intersector.getIntervals().size());
		assertEquals(this.fourToSeven, this.intersector.getIntervals().get(0));
	}

	@Test
	void threeSegmentsWithOverlap() throws Exception {
		this.intersector.addSegment(3, 4);
		this.intersector.addSegment(6, 7);
		this.intersector.addSegment(4, 6);
		assertEquals(new BigDecimal("0.5"), this.intersector.evaluate());
		assertEquals(1, this.intersector.getIntervals().size());
		assertEquals(this.threeToSeven, this.intersector.getIntervals().get(0));
	}

}
