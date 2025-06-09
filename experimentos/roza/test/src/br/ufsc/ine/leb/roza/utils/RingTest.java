package src.br.ufsc.ine.leb.roza.utils;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class RingTest {

	private static Ring<String> ring;

	@BeforeEach
	void setup() {
		this.ring = new Ring<>();
	}

	@Test
	void oneElement() throws Exception {
		this.ring.add("one");
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("one", this.ring.next());
	}

	@Test
	void twoElements() throws Exception {
		this.ring.add("one");
		this.ring.add("two");
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("two", this.ring.next());
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("two", this.ring.next());
	}

	@Test
	void twoElementsSecondAddedAfterFirstIteration() throws Exception {
		this.ring.add("one");
		this.ring.next();
		this.ring.add("two");
		Assertions.assertEquals("two", this.ring.next());
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("two", this.ring.next());
	}

	@Test
	void empty() throws Exception {
		Assertions.assertThrows(NoSuchElementException.class, () -> this.ring.next());
	}

	@Test
	void resetWithoutIterate() throws Exception {
		this.ring.add("one");
		this.ring.add("two");
		this.ring.reset();
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("two", this.ring.next());
	}

	@Test
	void resetAfterIterating() throws Exception {
		this.ring.add("one");
		this.ring.add("two");
		this.ring.next();
		this.ring.reset();
		Assertions.assertEquals("one", this.ring.next());
		Assertions.assertEquals("two", this.ring.next());
	}

}
