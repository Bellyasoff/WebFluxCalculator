package io.bellyasoff.webfluxcalculator.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author IlyaB
 */

@Getter
@Setter
public class Result {
   public int iteration;
   public int functionNumber;
   public double value;
   public long duration;
   public boolean error;
   public String errorMessage;

   public Result(int iteration, int functionNumber, double value, long duration, boolean error, String errorMessage) {
      this.iteration = iteration;
      this.functionNumber = functionNumber;
      this.value = value;
      this.duration = duration;
      this.error = error;
      this.errorMessage = errorMessage;
   }

   public static Result success(int iteration, int functionNumber, double value, long duration) {
      return new Result(iteration, functionNumber, value, duration, false, null);
   }

   public static Result error(int iteration, int functionNumber, String errorMessage) {
      return new Result(iteration, functionNumber, Double.NaN, 0, true, errorMessage);
   }
}
