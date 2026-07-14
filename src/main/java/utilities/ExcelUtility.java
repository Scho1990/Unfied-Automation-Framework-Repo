package utilities;

import exceptions.ExcelOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility implements AutoCloseable {
    private final Logger logger = LogManager.getLogger(ExcelUtility.class);
    private final String filepath;
    private Workbook workbook;

    //Opens an existing workbook or creates a new one if the file does not exist.
    public ExcelUtility(String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);

        try {
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fileInputStream);
                }
            } else {
                workbook = new XSSFWorkbook();
            }
        } catch (IOException e) {
            throw new ExcelOperationException("Unable to open excel file: '%s' " + filepath, e);
        }
    }

    //Returns an existing sheet or creates it if not present.
    public Sheet getOrCreateSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    //Returns an existing sheet.
    public Sheet getSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new ExcelOperationException("Sheet " + sheetName + " not found");
        }
        return sheet;
    }

    //Saves the workbook.
    public void save() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            throw new ExcelOperationException("Unable to save excel file: " + filepath, e);
        }
    }

    //Closes the workbook.
    @Override
    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    //Returns the total number of rows in a sheet.
    public int getRowCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    //Returns the total number of columns in a sheet.
    public int getColumnCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        Row row = sheet.getRow(0);
        if (row == null) {
            return 0;
        }
        return row.getLastCellNum();
    }

    //Reads cell value as String.
    public String readCell(String sheetName, int rowNumber, int columnNumber) {
        Sheet sheet = getSheet(sheetName);
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(columnNumber, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    //Writes value into a cell.
    public void writeCell(String sheetName, int rowNumber, int columnNumber, String value) {
        Sheet sheet = getOrCreateSheet(sheetName);
        Row row = sheet.getRow(rowNumber);
        if (row == null) {
            row = sheet.createRow(rowNumber);
        }
        Cell cell = row.getCell(columnNumber);
        if (cell == null) {
            cell = row.createCell(columnNumber);
        }
        cell.setCellValue(value);
    }

    //Auto sizes a column.
    public void autoSizeColumn(String sheetName, int columnNumber) {
        Sheet sheet = getSheet(sheetName);
        sheet.autoSizeColumn(columnNumber);
    }

    //Auto sizes all columns in the sheet.
    public void autoSizeAllColumns(String sheetName) {
        int columns = getColumnCount(sheetName);
        for (int columnNumber = 0; columnNumber < columns; columnNumber++) {
            autoSizeColumn(sheetName, columnNumber);
        }
    }

    //Reads all cells from a row.
    public List<String> readRow(String sheetName, int rowNumber) {
        Sheet sheet = getSheet(sheetName);
        Row row = sheet.getRow(rowNumber);
        List<String> rowData = new ArrayList<>();
        if (row == null) {
            return rowData;
        }
        DataFormatter formatter = new DataFormatter();
        for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
            Cell cell = row.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            rowData.add(formatter.formatCellValue(cell));
        }
        return rowData;
    }

    //Reads an entire column.
    public List<String> readColumn(String sheetName, int columnNumber) {
        Sheet sheet = getSheet(sheetName);
        List<String> columnData = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {
            Cell cell = row.getCell(columnNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            columnData.add(formatter.formatCellValue(cell));
        }
        return columnData;
    }

    //Writes a list into a column.
    public void writeColumn(String sheetName, int columnNumber, List<String> values) {
        Sheet sheet = getOrCreateSheet(sheetName);
        for (int rowNumber = 0; rowNumber < values.size(); rowNumber++) {
            Row row = sheet.getRow(rowNumber+1);
            if (row == null) {
                row = sheet.createRow(rowNumber+1);
            }
            Cell cell = row.getCell(columnNumber);
            if (cell == null) {
                cell = row.createCell(columnNumber);
            }
            cell.setCellValue(values.get(rowNumber));
        }
    }

    //Compare two columns.
    public boolean compareColumns(String expectedSheet,int expectedColumn,String actualSheet,int actualColumn) {
        List<String> expected = readColumn(expectedSheet,expectedColumn);
        List<String> actual = readColumn(actualSheet,actualColumn);

        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).trim().equalsIgnoreCase(actual.get(i).trim())) {
                logger.error("Mismatch at row {} Expected={} Actual={}", i + 1, expected.get(i), actual.get(i));
                return false;
            }
        }
        return true;
    }

    /**
     * Clears all rows except header.
     */
    public void clearSheetData(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        int lastRow = sheet.getLastRowNum();
        for (int i = lastRow; i > 0; i--) {
            Row row = sheet.getRow(i);
            if (row != null) {
                sheet.removeRow(row);
            }
        }
    }

    public void writeColumn(String sheetName, int columnNumber, List<String> values, boolean clearExisting){
        if (clearExisting) {
            clearSheetData(sheetName);
        }
        writeColumn(sheetName, columnNumber, values);
    }

    /**
     * Returns the column index for the given header.
     */
    private int getColumnIndex(String sheetName, String columnName) {
        Sheet sheet = getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new RuntimeException("Header row not found in sheet : " + sheetName);
        }
        DataFormatter formatter = new DataFormatter();
        for (Cell cell : headerRow) {
            if (formatter.formatCellValue(cell)
                    .trim()
                    .equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        throw new ExcelOperationException(
                "Column '" + columnName + "' not found in sheet : " + sheetName);
    }

    //Returns data for TestNG DataProvider.
    public Object[][] getDataForDataProvider(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        int lastRow = sheet.getLastRowNum();
        int columns = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[lastRow][columns];
        DataFormatter formatter = new DataFormatter();
        for (int i = 1; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < columns; j++) {
                String value = "";
                if (row != null) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    value = formatter.formatCellValue(cell);
                }
                data[i - 1][j] = value;
            }
        }
        return data;
    }

    /**
     * Returns filtered data for TestNG DataProvider.
     */
    public Object[][] getFilteredData(String sheetName, String columnName, String filterValue) {
        Sheet sheet = getSheet(sheetName);
        int filterColumn = getColumnIndex(sheetName, columnName);
        int columns = sheet.getRow(0).getLastCellNum();

        DataFormatter formatter = new DataFormatter();
        List<Object[]> filteredRows = new ArrayList<>();

        for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
            Row row = sheet.getRow(rowNumber);
            if (row == null) {
                continue;
            }
            String currentValue = formatter.formatCellValue(
                    row.getCell(filterColumn,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
            if (currentValue.toUpperCase().startsWith(filterValue.trim().toUpperCase())) {
                Object[] rowData = new Object[columns];
                for (int column = 0; column < columns; column++) {
                    rowData[column] = formatter.formatCellValue(row.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
                }
                filteredRows.add(rowData);
            }
        }
        return filteredRows.toArray(new Object[0][]);
    }
}

