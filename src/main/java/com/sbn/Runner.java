package com.sbn;

import com.sbn.util.ExcelReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        String testSuitePath = "./testSuite.xlsx";
        try(ExcelReader er = new ExcelReader(testSuitePath)) {
            String suiteName = "Sanity";
            int rowNumber = er.getRowCount(suiteName);
            for (int i = 1; i <= rowNumber; i++) {
                System.out.println(er.getCellStringValue(suiteName, i, 3));
            }
        }catch (Exception e){

        }

    }

}
