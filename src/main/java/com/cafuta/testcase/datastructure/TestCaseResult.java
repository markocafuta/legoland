package com.cafuta.testcase.datastructure;

import com.cafuta.legoland.SawmillOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class TestCaseResult {
	private final int ordinal;
	private int maxProfit;
	private final Map<Integer, Collection<String>> order = new HashMap<>();

	public void addSowmillOutput(final SawmillOutput output) {
		maxProfit += output.getProfit();
		order.put(output.getSawmillIndex(), output.getOrder());
	}
}
