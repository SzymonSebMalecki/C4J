package de.andrena.next.systemtest.config;

import java.util.Collections;
import java.util.Set;

import de.andrena.next.DefaultConfiguration;

public class AssertionErrorOnlyConfiguration extends DefaultConfiguration {
	@Override
	public Set<String> getRootPackages() {
		return Collections.singleton("de.andrena.next.systemtest.config.assertionerroronly");
	}

	@Override
	public Set<ContractViolationAction> getContractViolationActions() {
		return Collections.singleton(ContractViolationAction.ASSERTION_ERROR);
	}
}
