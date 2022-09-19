package com.cafuta.testcase;

import com.cafuta.infrastructure.CachedThreadPoolExecutor;
import com.cafuta.legoland.AllCombinator;
import com.cafuta.legoland.ConcurrentSawmillOptimizer;
import com.cafuta.legoland.ProfitCalculator;
import com.cafuta.legoland.Sawmill;
import com.cafuta.legoland.SawmillCalculator;
import com.cafuta.legoland.SawmillCalculatorImpl;
import com.cafuta.legoland.TreeTrunkCombinator;
import com.cafuta.legoland.TreeTrunkSawmill;
import com.cafuta.legoland.WoodToProfitConverter;
import com.cafuta.legoland.WoodcutterProfitCalculator;
import com.cafuta.testcase.datastructure.TestCase;
import com.cafuta.testcase.datastructure.TestCaseResult;
import com.cafuta.testcase.result.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProcessorImplTest {

	@Mock private Writer writer;
	private final Validator validator = new ValidatorImpl();

	private final TreeTrunkCombinator combinator = new AllCombinator();
	private final ProfitCalculator calculator = new WoodcutterProfitCalculator(new WoodToProfitConverter());
	private final Sawmill sawmill = new TreeTrunkSawmill();
	private final SawmillCalculator sawmillCalculator = new SawmillCalculatorImpl(calculator, sawmill);
	private final ExecutorService executor = new CachedThreadPoolExecutor().get();
	private final ConcurrentSawmillOptimizer optimizer = new ConcurrentSawmillOptimizer(combinator, sawmillCalculator, executor);
	private Processor processor;

	@BeforeEach
	void init() {
		processor = new ConcurrentProcessor(validator, writer, optimizer, executor);
	}

	@Test
	void process () {
		TestCase testCase = new TestCase(2, 3);
		testCase.addTreeTrunksPerSawmill(new int[] {1,2,1});
		testCase.addTreeTrunksPerSawmill(new int[] {1,2});
		testCase.addTreeTrunksPerSawmill(new int[] {1,4});

		TestCaseResult result = processor.process(testCase);
		assertEquals(2, result.getOrdinal());
		assertEquals(8, result.getMaxProfit());

		Map<Integer, Collection<String>> order = result.getOrder();
		assertEquals(3, order.size());

		assertEquals(2, order.get(0).size());
		assertTrue(order.get(0).contains("[1 2 1]"));
		assertTrue(order.get(0).contains("[2 1 1]"));

		assertEquals(2, order.get(1).size());
		assertTrue(order.get(1).contains("[1 2]"));
		assertTrue(order.get(1).contains("[2 1]"));

		assertEquals(1, order.get(2).size());
		assertTrue(order.get(2).contains("[1 4]"));
	}
}