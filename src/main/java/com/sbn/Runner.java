package com.sbn;

import com.sbn.entity.MyStep;
import com.sbn.entity.MyTest;
import com.sbn.util.ExcelReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    private final static Logger LOGGER = LogManager.getLogger();
    private static WebDriver driver = null;
    public static void main(String[] args) {
        String testSuitePath = "./testSuite.xlsx";
        String suiteName = "Sanity";
        LOGGER.info("Starting test Execution for suite: " + suiteName);
        List<MyTest> testsFromExcel = getTestsFromExcel(testSuitePath, suiteName);
        LOGGER.info("Found total:" + testsFromExcel.size() + " tests from Suite:" + suiteName);
        executeSuite(testsFromExcel);

    }

    private static void executeSuite(List<MyTest> testsFromExcel) {
        boolean result = true;
        for (MyTest myTest : testsFromExcel) {
            if(myTest.isEnabled()){

                result &= executeTest(myTest);

            }else{
                LOGGER.info("Skipping test:[" + myTest.getTestId() + "] as it is marked as disabled");
            }
        }
        LOGGER.info("Final result " + result);
    }

    private static boolean executeTest(MyTest myTest) {
        List<MyStep> mySteps = myTest.getMySteps();
        boolean testResult = true;
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        for (MyStep myStep : mySteps) {
            LOGGER.info("executing Step:" + myStep.getStepName());
            try {
                testResult &= executeStep(myStep);
            }catch (Exception e){
                testResult =false;
                e.printStackTrace();
                break;
            }
        }
        driver.quit();
        return testResult;
    }

    private static boolean executeStep(MyStep myStep) throws Exception {
        LOGGER.info("Executing test:" + myStep.getStepName() + " with input " + Arrays.asList(myStep.getInputs()));
        Class<?> aClass = Class.forName(myStep.getClassName());
        Method method = aClass.getMethod(myStep.getStepName().toLowerCase(),MyStep.class,WebDriver.class);
        return (boolean)method.invoke(null,myStep,driver);

    }

    private static List<MyTest> getTestsFromExcel(String testSuitePath, String suiteName) {
        LOGGER.info("Parsing tests from Excel:"+testSuitePath);
        List<MyTest> myTests = new ArrayList<>();

        try(ExcelReader excelReader = new ExcelReader(testSuitePath)) {
            int numberOfRows = excelReader.getRowCount(suiteName);
            LOGGER.info("Found total " + numberOfRows + " in Excel Sheet:" + suiteName);
            MyTest test = null;
            for(int i=1;i<=numberOfRows;i++){
                if(!excelReader.getCellStringValue(suiteName, i,0).isEmpty() || !excelReader.getCellStringValue(suiteName, i,0).isBlank()) {
                    if(test!=null) {
                        myTests.add(test);
                    }
                    test = createTest(suiteName, excelReader, i);
                }
                MyStep step = createStep(suiteName,excelReader,i);
                if(test!=null) {
                    test.addStep(step);
                }
            }
            myTests.add(test);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myTests;
    }

    private static MyStep createStep(String suiteName, ExcelReader excelReader, int i) {
        String stepName = excelReader.getCellStringValue(suiteName,i,3);
        boolean expected = excelReader.getCellStringValue(suiteName,i,4).equalsIgnoreCase("true");
        int columnCount = excelReader.getColumnCount(suiteName, i);
        LOGGER.info("found total " + columnCount + " at row:" + i);
        String[] params = new String[columnCount-5];
        for(int j= 5;j<columnCount;j++){
            LOGGER.info("Parsing parameter from column:" + j);
            params[j-5] = excelReader.getCellStringValue(suiteName,i,j);
        }
        return new MyStep(stepName,expected,params,getClassName(excelReader,stepName));
    }

    private static MyTest createTest(String suiteName, ExcelReader excelReader, int i) {
        int testId = (int)Float.parseFloat(excelReader.getCellStringValue(suiteName, i,0));
        String description = excelReader.getCellStringValue(suiteName, i,1);
        boolean isEnabled = excelReader.getCellStringValue(suiteName, i,2).equalsIgnoreCase("Y");
        return new MyTest(testId,description,isEnabled);
    }

    private static String getClassName(ExcelReader excelReader, String stepName){
        String sheetName = "keyWords";
        int rows = excelReader.getRowCount(sheetName);
        for(int i =1;i<=rows;i++){
            if(excelReader.getCellStringValue(sheetName,i,0).equalsIgnoreCase(stepName)){
                return excelReader.getCellStringValue(sheetName,i,1);
            }
        }
        return "";
    }

}
