package com.sbn;

import com.sbn.entity.MyStep;
import com.sbn.entity.MyTest;
import com.sbn.util.ExcelReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        String testSuitePath = "./testSuite.xlsx";
        String suiteName = "Sanity";
        List<MyTest> testsFromExcel = getTestsFromExcel(testSuitePath, suiteName);
        executeSuite(testsFromExcel);

    }

    private static void executeSuite(List<MyTest> testsFromExcel) {

    }

    private static List<MyTest> getTestsFromExcel(String testSuitePath, String suiteName) {
        List<MyTest> myTests = new ArrayList<>();
        try(ExcelReader excelReader = new ExcelReader(testSuitePath)) {
            int numberOfRows = excelReader.getRowCount(suiteName);
            for(int i=1;i<=numberOfRows;i++){
                MyTest test = createTest(suiteName, excelReader, i);
                MyStep step = createStep(suiteName,excelReader,i);
                test.addStep(step);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myTests;
    }

    private static MyStep createStep(String suiteName, ExcelReader excelReader, int i) {
        String stepName = excelReader.getCellStringValue(suiteName,i,3);
        boolean expected = excelReader.getCellStringValue(suiteName,i,4).equalsIgnoreCase("true");
        int columnCount = excelReader.getColumnCount(suiteName, i);
        String[] params = new String[columnCount-5];
        for(int j= 5;j<=columnCount;j++){
            params[j-5] = excelReader.getCellStringValue(suiteName,i,j);
        }
        return new MyStep(stepName,expected,params);
    }

    private static MyTest createTest(String suiteName, ExcelReader excelReader, int i) {
        int testId = Integer.parseInt(excelReader.getCellStringValue(suiteName, i,0));
        String description = excelReader.getCellStringValue(suiteName, i,1);
        boolean isEnabled = excelReader.getCellStringValue(suiteName, i,2).equalsIgnoreCase("Y");
        return new MyTest(testId,description,isEnabled);
    }

}
