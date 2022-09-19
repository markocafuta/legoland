package com.cafuta.infrastructure;

import com.cafuta.input.Parser;
import com.cafuta.input.ParserImpl;
import com.cafuta.legoland.AllCombinator;
import com.cafuta.legoland.ConcurrentSawmillOptimizer;
import com.cafuta.legoland.ProfitCalculator;
import com.cafuta.legoland.ProfitConverter;
import com.cafuta.legoland.Sawmill;
import com.cafuta.legoland.SawmillCalculator;
import com.cafuta.legoland.SawmillCalculatorImpl;
import com.cafuta.legoland.SawmillOptimizer;
import com.cafuta.legoland.TreeTrunkCombinator;
import com.cafuta.legoland.TreeTrunkSawmill;
import com.cafuta.legoland.WoodToProfitConverter;
import com.cafuta.legoland.WoodcutterProfitCalculator;
import com.cafuta.output.Printer;
import com.cafuta.output.SystemOutPrinter;
import com.cafuta.testcase.ConcurrentProcessor;
import com.cafuta.testcase.Mapper;
import com.cafuta.testcase.MapperImpl;
import com.cafuta.testcase.Processor;
import com.cafuta.testcase.Validator;
import com.cafuta.testcase.ValidatorImpl;
import com.cafuta.testcase.result.Formatter;
import com.cafuta.testcase.result.FormatterImpl;
import com.cafuta.testcase.result.Writer;
import com.cafuta.testcase.result.WriterImpl;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

	public static BeanFactory instance = new BeanFactory();

	private final Map<Class<?>, Object> beans = new HashMap<>();

	private BeanFactory () {
		beans.put(ThreadPoolExecutor.class, new CachedThreadPoolExecutor());
		beans.put(Sawmill.class, new TreeTrunkSawmill());
		beans.put(ProfitConverter.class, new WoodToProfitConverter());
		beans.put(Printer.class, new SystemOutPrinter());
		beans.put(Parser.class, new ParserImpl());
		beans.put(Validator.class, new ValidatorImpl());
		beans.put(Formatter.class, new FormatterImpl());
		beans.put(TreeTrunkCombinator.class, new AllCombinator());
		beans.put(ProfitCalculator.class, new WoodcutterProfitCalculator(getBean(ProfitConverter.class)));
		beans.put(SawmillCalculator.class, new SawmillCalculatorImpl(getBean(ProfitCalculator.class), getBean(Sawmill.class)));
		beans.put(SawmillOptimizer.class, new ConcurrentSawmillOptimizer(
			getBean(TreeTrunkCombinator.class),
			getBean(SawmillCalculator.class),
			getBean(ThreadPoolExecutor.class).get())
		);
		beans.put(Writer.class, new WriterImpl(getBean(Formatter.class), getBean(Printer.class)));
		beans.put(Processor.class, new ConcurrentProcessor(
			getBean(Validator.class),
			getBean(Writer.class),
			getBean(SawmillOptimizer.class),
			getBean(ThreadPoolExecutor.class).get())
		);
		beans.put(Mapper.class, new MapperImpl(getBean(Processor.class)));
	}

	public <T> T getBean(final Class<T> beanType) {
		return (T) beans.get(beanType);
	}
}
