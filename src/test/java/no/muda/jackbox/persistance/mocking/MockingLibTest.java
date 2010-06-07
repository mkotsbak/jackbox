package no.muda.jackbox.persistance.mocking;

import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import no.muda.jackbox.example.ExampleDependency;
import no.muda.jackbox.example.ExampleRecordedObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
//@PrepareForTest( {ExampleRecordedObject.class, MockingLibTest.class})
@PrepareForTest( {ExampleRecordedObject.class} )
public class MockingLibTest {
    public static final String testArgument1 = "testargument1";
    public static final String testMockedReturnValue_ExampleDependency_invokedMethodOnDependency = "mockedreturn1";
    public static final int testMockedReturnValue_ExampleRecordedObject_exampleMethod = 435432;
    private static final int testAdd1 = 2;
    private static final int testAdd2 = 5;

    @Test
    public void testPowerMockDirectCall() throws Exception {
        ExampleRecordedObject eroMock = createMock(ExampleRecordedObject.class);
        expectNew(ExampleRecordedObject.class).andReturn(eroMock);
//          expectNew(ExampleRecordedObject.class).andReturn(new ExampleRecordedObject());
        expect(eroMock.exampleMethod(testAdd1, testAdd2)).andReturn(testMockedReturnValue_ExampleRecordedObject_exampleMethod);

        //replay(eroMock, ExampleRecordedObject.class);
        PowerMock.replayAll();

        testExampleMethod();

        PowerMock.verifyAll();
    }

    @Test
    public void testPowerMockIndirectCall() throws Exception {
        ExampleDependency edMock = createMock(ExampleDependency.class);

        expectNew(ExampleDependency.class).andReturn(edMock);
        expect(edMock.invokedMethodOnDependency(testArgument1)).andReturn(testMockedReturnValue_ExampleDependency_invokedMethodOnDependency);

        //replay(eroMock, ExampleRecordedObject.class);
        PowerMock.replayAll();

        testExampleMethodThatDelegatesToDependency();

        PowerMock.verifyAll();
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
        assertThat(result).isEqualTo(testMockedReturnValue_ExampleRecordedObject_exampleMethod);
    }
}
