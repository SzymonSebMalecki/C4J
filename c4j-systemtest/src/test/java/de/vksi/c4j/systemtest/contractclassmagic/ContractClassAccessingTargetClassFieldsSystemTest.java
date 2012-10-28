package de.vksi.c4j.systemtest.contractclassmagic;

import static de.vksi.c4j.Condition.postCondition;
import static de.vksi.c4j.Condition.preCondition;

import org.junit.Rule;
import org.junit.Test;

import de.vksi.c4j.ContractReference;
import de.vksi.c4j.Target;
import de.vksi.c4j.systemtest.TransformerAwareRule;

public class ContractClassAccessingTargetClassFieldsSystemTest {
	@Rule
	public TransformerAwareRule transformerAwareRule = new TransformerAwareRule();

	@Test
	public void testContractClassAccessingTargetClassField() {
		new TargetClass().method(3, 3);
	}

	@ContractReference(ContractClass.class)
	public static class TargetClass {
		protected int value;

		public void method(int value, int param) {
			this.value = value;
		}
	}

	public static class ContractClass extends TargetClass {
		@Target
		private TargetClass target;

		@Override
		public void method(int value, int param) {
			if (postCondition()) {
				assert target.value >= param;
			}
		}
	}

	@Test
	public void testContractClassAccessingOverriddenTargetClassField() {
		new TargetClassOverridden().method();
	}

	@ContractReference(ContractClassOverridden.class)
	public static class TargetClassOverridden {
		protected Boolean value = Boolean.TRUE;

		public void method() {
		}
	}

	public static class ContractClassOverridden extends TargetClassOverridden {
		private Boolean value = Boolean.FALSE;

		@Override
		public void method() {
			if (preCondition()) {
				assert !value.booleanValue();
			}
		}
	}
}
