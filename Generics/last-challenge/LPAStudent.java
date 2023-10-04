package model;

import model.Student;

public class LPAStudent extends Student {
    private double percentComplete;
    public LPAStudent(){
        percentComplete = Student.random.nextDouble(0, 100.001);
       }
    public String toString(){
        return "%s %8.1f%%".formatted(super.toString(), percentComplete);
        // %% This is how you print out a percent sign in the output, so it's a specifier for a percent sign.
    }
    public double getPercentComplete() {
        return percentComplete;
    }

    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        if(fieldName.equalsIgnoreCase("percentComplete")) {
            return percentComplete <= Integer.parseInt(value);
        }
        return super.matchFieldValue(fieldName, value);
    }
}
