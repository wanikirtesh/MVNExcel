package com.sbn.entity;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    private final int testId;
    private final String testDescription;
    private final boolean isEnabled;
    private final List<MyStep> mySteps;

    public MyTest(int testId, String testDescription, boolean isEnabled) {
        this.testId = testId;
        this.testDescription = testDescription;
        this.isEnabled = isEnabled;
        this.mySteps = new ArrayList<>();
    }

    public void addStep(MyStep myStep){
        mySteps.add(myStep);
    }

    public int getTestId() {
        return testId;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public List<MyStep> getMySteps() {
        return mySteps;
    }
}
