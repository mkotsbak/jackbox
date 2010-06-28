package jackbox.persistance.testgeneration;

import jackbox.MethodRecording;
import jackbox.persistence.json.Persister;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.lang.NotImplementedException;

public class PowerMockTestGenerator implements Persister {

    public void persistToWriter(MethodRecording recording, Appendable output) throws IOException {
        output.append("package generatedtest;\n\n");
        appendImports(output, recording);
        output.append("public class AGeneratedTest {\n");
        appendTest(output, recording);
    }

    private void appendTest(Appendable output, MethodRecording recording) throws IOException {
        String klassname = recording.getTargetClass().getSimpleName();
        String methodname = recording.getMethod().getName();
        output.append("@Test\npublic void test" + methodname + "() {\n");
        output.append(klassname + " a" + klassname + " = new " + klassname + "();\n");
        output.append("a" + klassname + "." + methodname + "();\n");
        output.append("}");
    }

    private void appendImports(Appendable output, MethodRecording recording) throws IOException {
        output.append( "import " +  recording.getTargetClass().getCanonicalName() + ";\n\n");
    }

    public MethodRecording readFromReader(Reader input) {
        throw new NotImplementedException("Parsing of generated test code is not supported.");
    }

}
