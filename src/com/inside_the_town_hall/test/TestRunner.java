package com.inside_the_town_hall.test;

import com.inside_the_town_hall.test.gui.JUnitGUISuite;
import com.inside_the_town_hall.test.logic.JUnitLogicSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runs different JUnit test suites and outputs the results
 *
 * @author NekroQuest
 */
public class TestRunner {

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        runner.run();
    }

    /**
     * runs the test suites
     */
    public void run() {
        Result result = JUnitCore.runClasses(JUnitGUISuite.class, JUnitLogicSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}