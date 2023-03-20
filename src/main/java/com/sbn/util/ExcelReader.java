package com.sbn.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Closeable;
import java.io.IOException;

public class ExcelReader implements Closeable {
    private final Workbook workbook;

    public ExcelReader(String path) throws IOException {
        workbook = new XSSFWorkbook(path);
    }

    public int getRowCount(String sheetName){
        return workbook.getSheet(sheetName).getLastRowNum();
    }

    public String getCellStringValue(String sheetName,int rowIndex, int colIndex){
        Cell myCell = workbook.getSheet(sheetName).getRow(rowIndex).getCell(colIndex);
        if(myCell==null)
            return "";
        switch(myCell.getCellType()){
            case NUMERIC -> {
                return "" + myCell.getNumericCellValue();
            }
            case BOOLEAN -> {
                return "" + myCell.getBooleanCellValue();
            }
            default -> {
                return myCell.getStringCellValue();
            }
        }

    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }

    public int getColumnCount(String sheetName, int rowIndex) {
        return workbook.getSheet(sheetName).getRow(rowIndex).getLastCellNum();
    }
}
