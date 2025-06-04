package io.bellyasoff.webfluxcalculator.executor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author IlyaB
 */

@Component
@RequiredArgsConstructor
public class ScriptEngineFactory {

   private final JavaScriptExecutor jsExecutor;
   private final PythonExecutor pyExecutor;

   public ScriptExecutor getExecutor(String script) {
      if (script.trim().startsWith("function")) {
         return jsExecutor;
      }
      return pyExecutor;
   }
}
