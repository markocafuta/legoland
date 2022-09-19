package com.cafuta.testcase;

import com.cafuta.testcase.datastructure.TestCase;
import com.cafuta.testcase.datastructure.TestCaseResult;

public interface Processor {

	TestCaseResult process(TestCase testCase);
}
