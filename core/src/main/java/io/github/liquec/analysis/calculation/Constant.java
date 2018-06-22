/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Constant {
   NO_CHECK_DEPTH("No check depth", 20.0, 0.0),;

   private String description;
   private Double eurocode;
   private Double ncse02;

   Constant(final String description, final Double eurocode, final Double ncse02) {
        this.description = description;
        this.eurocode = eurocode;
        this.ncse02 = ncse02;
   }

   public String getDescription() {
       return description;
   }

   public Double getValue(final Mode mode) {
       if (Mode.EUROCODE == mode) {
           return eurocode;
       }
       return ncse02;
   }

}
