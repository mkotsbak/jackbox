package jackbox.persistance.testgeneration;

import static org.fest.assertions.Assertions.assertThat;
import jackbox.MethodRecording;
import jackbox.example.ExampleRecordedObject;
import jackbox.persistence.json.Persister;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.BeforeClass;
import org.junit.Test;

public class PowermockTestGenerationTest {
    public static MethodRecording testRecording;

    @BeforeClass
    public static void setupConstants() throws SecurityException, NoSuchMethodException {
        testRecording = new MethodRecording(
                ExampleRecordedObject.class,
                ExampleRecordedObject.class.getMethod("exampleMethod", Integer.TYPE, Integer.TYPE),
                new Object[] { 5, 6 });
        testRecording.setReturnValue(11);
    }

    @Test
    public void generatedCodeShoudContainEssentialParts() throws IOException {
        String generatedCode = generateTestCode(testRecording);

        assertThat(generatedCode).contains("public class").contains("package")
        .contains("@Test").contains("import " + ExampleRecordedObject.class.getCanonicalName());
    }

    @Test
    public void shouldCallRecordedMethod() throws IOException {
        String generatedCode = generateTestCode(testRecording);
        assertThat(generatedCode).contains("." + testRecording.getMethod().getName() + "(")
        .contains("new " + testRecording.getTargetClass().getSimpleName());
    }

    /**
     * 
     * @param recording
     * @return Generated test
     * @throws IOException
     */
    private String generateTestCode(MethodRecording recording) throws IOException {
        Persister persister = new PowerMockTestGenerator();
        StringWriter output = new StringWriter();
        persister.persistToWriter(recording, output);
        return output.toString();
    }
}
