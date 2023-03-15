package com.sbn.entity;

public class MyStep {
    private final String stepName;
    private final boolean expected;
    private final String[] inputs;

    public MyStep(String stepName, boolean expected, String[] inputs) {
        this.stepName = stepName;
        this.expected = expected;
        this.inputs = inputs;
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
}
