package io.bellyasoff.webfluxcalculator.executor;

import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author IlyaB
 */

@Component
public class JavaScriptExecutor implements ScriptExecutor {

    private final ScriptEngine engine;

    public JavaScriptExecutor() {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public double execute(String script, int input) throws Exception {
        String fullScript = "var apply = " + script + "; apply(" + input + ");";
        Object result = engine.eval(fullScript);
        return Double.parseDouble(result.toString());
    }
}
