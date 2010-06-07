package no.muda.jackbox.persistance.mocking;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import no.muda.jackbox.example.ExampleDependency;
import no.muda.jackbox.example.ExampleRecordedObject;

import org.junit.Test;

public class MockingLibTest {
    public static final String testArgument1 = "testargument1";
    public static final String testMockedReturnValue_ExampleDependency_invokedMethodOnDependency = "mockedreturn1";
    public static final int testMockedReturnValue_ExampleDependency_exampleMethod = 435432;
    private static final int testAdd1 = 2;
    private static final int testAdd2 = 5;

    @Test
    public void testPowerMock() {
        
    }

    @Test
    public void testNoMockIndirectCall() {
        try {
            testExampleMethodThatDelegatesToDependency();
            fail("Should not work");
        }
        catch (Throwable expected) {}
    }

    @Test
    public void testNoMockDirectCall() {
        try {
            testExampleMethod();
            fail("Should not work");
        }
        catch (Throwable expected) {}
    }

    /**
     * Before calling this, ensure that ExampleDependency.invokedMethodOnDependency returns
     * MockingLibTest.testMockedReturnValue_ExampleDependency_invokedMethodOnDependency instead of doing normal work
     */
    private void testExampleMethodThatDelegatesToDependency() {
        ExampleRecordedObject obj = new ExampleRecordedObject();
        obj.setDependency(new ExampleDependency());

        String returnValue = obj.exampleMethodThatDelegatesToDependency(testArgument1);
        assertThat(returnValue).isNotEqualTo(testArgument1.toUpperCase());
        assertThat(returnValue).isEqualTo(testMockedReturnValue_ExampleDependency_invokedMethodOnDependency);
    }

    /**
     * Before calling this, ensure that ExampleDependency.exampleMethod return
     * MockingLibTest.testMockedReturnValue_ExampleDependency_exampleMethod
     */
    private void testExampleMethod() {
        ExampleRecordedObject obj = new ExampleRecordedObject();
        int result = obj.exampleMethod(testAdd1, testAdd2);
        assertThat(result).isNotEqualTo(testAdd1 + testAdd2);
        assertThat(result).isEqualTo(testMockedReturnValue_ExampleDependency_exampleMethod);
    }
}
