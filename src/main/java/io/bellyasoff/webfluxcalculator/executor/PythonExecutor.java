package io.bellyasoff.webfluxcalculator.executor;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Component;

/**
 * @author IlyaB
 */
@Component
public class PythonExecutor implements ScriptExecutor {
    @Override
    public double execute(String script, int input) throws Exception {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec("def apply(x):\n" + indent(script));
            PyObject applyFunc = interpreter.get("apply");
            PyObject result = applyFunc.__call__(new org.python.core.PyInteger(input));
            return result.asDouble();
        }
    }

    private String indent(String script) {
        return "    " + script.replace("\n", "\n    ");
    }
}
