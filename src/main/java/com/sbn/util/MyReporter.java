package com.sbn.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sbn.entity.MyStep;
import com.sbn.entity.MyTest;

public class MyReporter {
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static ExtentTest test;
    private static ExtentTest node;
    public static void start() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("./reports/index.html");
        extent.attachReporter(spark);
    }

    public static void stop() {
        extent.flush();
    }

    public static void startTest(MyTest myTest) {
        test = extent.createTest(myTest.getTestDescription());
    }

    public static void completeTest(boolean result) {
        if(result){
            test.pass("Passed");
        }else {
            test.fail("");
        }
    }

    public static void skipTest() {
        test.skip("");
    }

    public static void startStep(MyStep myStep) {
        node = test.createNode(myStep.getStepName());
        node.info(MarkupHelper.createUnorderedList(myStep.getInputs()));
    }

    public static void completeStep(boolean stepResult) {
        if(stepResult){
            node.pass("");
        }else {
            node.fail("");
        }
    }

    public static void completeStep(Exception e) {
        node.fail(e);
    }

    public static void info(String info) {
        node.info(info);
    }
}
