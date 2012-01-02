package de.andrena.next.systemtest.dbi;

import static de.andrena.next.Condition.old;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.andrena.next.Condition.PostCondition;
import de.andrena.next.Contract;
import de.andrena.next.systemtest.TransformerAwareRule;

public class OldSystemTest {
	@Rule
	public TransformerAwareRule transformerAware = new TransformerAwareRule();

	private DummyClass dummy;

	@Before
	public void before() {
		dummy = new DummyClass();
	}

	@Test
	public void testOldWithField() {
		dummy.setValue(5);
		dummy.incrementValueCheckField();
	}

	@Test
	public void testOldWithMethod() {
		dummy.setValue(5);
		dummy.incrementValueCheckMethod();
	}

	@Contract(DummyContract.class)
	public static class DummyClass {
		protected int value;
		protected OtherClass otherValue;

		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void incrementValueCheckField() {
			value++;
		}

		public void incrementValueCheckMethod() {
			value++;
		}
	}

	public static class OtherClass {
		public InputStream stream;

		public int otherMethod() {
			return 0;
		}
	}

	public static class DummyContract extends DummyClass {
		@Override
		public void incrementValueCheckField() {
			new PostCondition() {
				{
					assert value == old(value) + 1;
				}
			};
		}

		@Override
		public void incrementValueCheckMethod() {
			new PostCondition() {
				{
					assert getValue() == old(getValue()) + 1;
				}
			};
		}
	}
}
