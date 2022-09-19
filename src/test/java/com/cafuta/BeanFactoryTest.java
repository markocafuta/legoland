package com.cafuta;

import com.cafuta.infrastructure.BeanFactory;
import com.cafuta.input.Parser;
import com.cafuta.input.Reader;
import com.cafuta.testcase.Mapper;
import com.cafuta.testcase.Processor;
import com.cafuta.testcase.Validator;
import com.cafuta.testcase.result.Formatter;
import com.cafuta.testcase.result.Writer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BeanFactoryTest {


	@Test
	void returnNull_ifBeanDoesNotExist() {
		Reader reader = BeanFactory.instance.getBean(Reader.class);
		assertNull(reader);
	}

	@Test
	void getBean () {
		Mapper mapper = BeanFactory.instance.getBean(Mapper.class);
		assertNotNull(mapper);

		Parser parser = BeanFactory.instance.getBean(Parser.class);
		assertNotNull(parser);

		Processor processor = BeanFactory.instance.getBean(Processor.class);
		assertNotNull(processor);

		Validator validator = BeanFactory.instance.getBean(Validator.class);
		assertNotNull(validator);

		Formatter formatter = BeanFactory.instance.getBean(Formatter.class);
		assertNotNull(formatter);

		Writer writer = BeanFactory.instance.getBean(Writer.class);
		assertNotNull(writer);
	}
}