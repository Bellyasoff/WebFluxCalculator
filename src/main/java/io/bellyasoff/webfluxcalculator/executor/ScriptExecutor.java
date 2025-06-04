package io.bellyasoff.webfluxcalculator.executor;

/**
 * @author IlyaB
 */
public interface ScriptExecutor {
    double execute(String script, int input) throws Exception;
}
