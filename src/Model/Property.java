package Model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Property {
    String getProperty(String fileName, String property) throws IOException;
}
