package Logic.Tester;

import org.python.util.PythonInterpreter;

public class pytest {
    public static void main(String[] args) {
        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            pyInterp.execfile("src/main/python/test.py");
        }
    }
}