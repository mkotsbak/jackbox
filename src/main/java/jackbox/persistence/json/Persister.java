package jackbox.persistence.json;

import java.io.IOException;
import java.io.Reader;

import jackbox.MethodRecording;


public interface Persister {
    void persistToWriter(MethodRecording recording, Appendable output) throws IOException;

    MethodRecording readFromReader(Reader input);
}
