package com.sbn.entity;

public class MyStep {
    private final String stepName;
    private final boolean expected;
    private final String[] inputs;

    private final String className;

    public MyStep(String stepName, boolean expected, String[] inputs,String className) {
        this.stepName = stepName;
        this.expected = expected;
        this.inputs = inputs;
        this.className = className;
    }

    public String getStepName() {
        return stepName;
    }

    public boolean isExpected() {
        return expected;
    }

    public String[] getInputs() {
        return inputs;
    }

    public String getClassName() {
        return className;
    }
}
