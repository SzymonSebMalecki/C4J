package de.andrena.next.systemtest.performance;

import static de.andrena.next.Condition.post;
import static de.andrena.next.Condition.pre;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import de.andrena.next.Contract;
import de.andrena.next.Target;

public class PerformanceSystemTest {
	private static TargetClass target;

	private static final int NUM_CALLS = 100;

	@BeforeClass
	public static void before() {
		target = new TargetClass();
		target.method(10);
	}

	@Test
	public void testPerformance() {
		long start = System.currentTimeMillis();
		long[] durations = new long[NUM_CALLS];
		for (int i = 0; i < NUM_CALLS; i++) {
			long thisStart = System.nanoTime();
			target.method(10);
			durations[i] = System.nanoTime() - thisStart;
		}
		long duration = System.currentTimeMillis() - start;
		System.out.println("PerformanceSystemTest.testPerformance() took " + duration + " ms.");
		//		for (int i = 0; i < NUM_CALLS; i++) {
		//			System.out.println(durations[i] + " ns");
		//		}
		assertTrue(duration < 300);
	}

	@Contract(ContractClass.class)
	public static class TargetClass {
		protected int field;

		public void method(int arg) {
			field = arg;
		}
	}

	public static class ContractClass extends TargetClass {
		@Target
		private TargetClass target;

		@Override
		public void method(int arg) {
			if (pre()) {
				assert arg >= 0;
			}
			if (post()) {
				assert target.field == arg;
			}
		}
	}
}
