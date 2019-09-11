/*
Business Entity Library (BEL) - A foundational library for JSF web applications 
Copyright (C) 2017  D P Bennett & Associates Limited

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

Email: info@dpbennett.com.jm
 */
package jm.com.dpbennett.wal.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import jm.com.dpbennett.business.entity.DatePeriod;
import jm.com.dpbennett.business.entity.utils.SearchParameters;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dbennett
 */
public class ReportUtils {
    
     public static String getSearchResultsTableHeader(SearchParameters searchParameters, List searchResultsList) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        if (searchParameters != null) {
            if (searchParameters.getDatePeriod().getStartDate() != null
                    && searchParameters.getDatePeriod().getEndDate() != null) {
                return "Period: " + formatter.format(searchParameters.getDatePeriod().getStartDate()) + " to "
                        + formatter.format(searchParameters.getDatePeriod().getEndDate()) + " (found: "
                        + searchResultsList.size() + ")";
            } else {
                return "Search Results";
            }
        } else {
            return "Search Results";
        }
    }

    public static String getSearchResultsTableHeader(DatePeriod datePeriod, List searchResultsList) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

        if (datePeriod.getStartDate() != null
                && datePeriod.getEndDate() != null) {
            return "Period: " + formatter.format(datePeriod.getStartDate()) + " to "
                    + formatter.format(datePeriod.getEndDate()) + " (found: "
                    + searchResultsList.size() + ")";
        } else {
            return "Search Results";
        }

    }

    public static String getSearchResultsTableHeader(List searchResultsList) {
        return "Search Results (found: " + searchResultsList.size() + ")";
    }
    
    public static List getCategories() {
        ArrayList categories = new ArrayList();

        categories.add(new SelectItem("", ""));
        categories.add(new SelectItem("Client", "Client"));
        categories.add(new SelectItem("Job", "Job"));
        categories.add(new SelectItem("Legal", "Legal"));

        return categories;
    } 
    
    public static List getMimeTypes() {
        ArrayList types = new ArrayList();

        types.add(new SelectItem("--", "--"));
        types.add(new SelectItem("application/jasper", "application/jasper"));
        types.add(new SelectItem("application/jrxml", "application/jrxml"));
        types.add(new SelectItem("application/xls", "application/xls"));
        types.add(new SelectItem("application/xlsx", "application/xlsx"));
        types.add(new SelectItem("application/pdf", "application/pdf"));

        return types;
    }

    public static String ALPHABET[] = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static int getLetterIndex(String letter) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i].equals(letter)) {
                return i;
            }
        }

        return -1;
    }


    public static void insertColumnHeaders(HSSFWorkbook wb,
            HSSFSheet sheet,
            short rowIndex,
            String[] headers,
            short fontSize) {
        // Setup column headers
        // Create column header font
        HSSFFont columnHeaderFont = wb.createFont();
        columnHeaderFont.setFontHeightInPoints(fontSize);
        columnHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFRow columnHeaderRow = sheet.createRow(fontSize);
        // Setup header style
        HSSFCellStyle headerColumnCellStyle = wb.createCellStyle();
        headerColumnCellStyle.setFont(columnHeaderFont);
        headerColumnCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        for (int i = 0; i < headers.length; i++) {
            columnHeaderRow.createCell(i).setCellValue(new HSSFRichTextString(headers[i]));
            columnHeaderRow.getCell(i).setCellStyle(headerColumnCellStyle);
        }
    }

    public static void insertExcelHeader(HSSFWorkbook wb,
            HSSFSheet sheet,
            String header,
            short rowIndex,
            short columnIndex,
            int widthInColumns,
            short fontSize) {
        // Create header font
        HSSFFont headerFont = wb.createFont();
        headerFont.setFontHeightInPoints(fontSize);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFRow headerRow = sheet.createRow(rowIndex);
        // Setup header style
        HSSFCellStyle headerCellStyle = wb.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        for (int i = 0; i < widthInColumns; i++) {
            headerRow.createCell(i);
            // Set cell style
            headerRow.getCell(i).setCellStyle(headerCellStyle);
            // Set as first sheet
        }
        // merge header cells
        sheet.addMergedRegion(new CellRangeAddress(
                rowIndex, //first row
                rowIndex, //last row
                0, //first column
                widthInColumns - 1 //last column
        ));
        // Set header title
        headerRow.getCell(0).setCellValue(new HSSFRichTextString(header));
    }

    public static HSSFCellStyle getExcelCellStyle(HSSFWorkbook wb, String cellType) {
        HSSFCellStyle style = null;

        switch (cellType) {
            case "java.lang.Long":
                style = wb.createCellStyle();
                style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Integer":
                style = wb.createCellStyle();
                style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Double":
                style = wb.createCellStyle();
                style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            case "java.lang.Boolean":
                style = wb.createCellStyle();
                break;
            case "java.lang.String":
                style = wb.createCellStyle();
                break;
            case "java.util.Date":
                style = wb.createCellStyle();
                style.setDataFormat(wb.createDataFormat().getFormat("MMM dd, yyyy"));
                break;
            default:
                break;
        }

        return style;
    }

    public static HSSFCellStyle setExcelCellDataFormat(HSSFWorkbook wb, String cellType, HSSFCellStyle style) {

        switch (cellType) {
            case "java.lang.Long":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Integer":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Double":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            case "Currency":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("J$#,##0.00"));
                break;
        //style = wb.createCellStyle();
            case "java.lang.Boolean":
                break;
        //style = wb.createCellStyle();
            case "java.lang.String":
                break;
            case "java.util.Date":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("MMM dd, yyyy"));
                break;
            default:
                break;
        }

        return style;
    }

    public static XSSFCellStyle setExcelCellDataFormat(XSSFWorkbook wb, String cellType, XSSFCellStyle style) {

        switch (cellType) {
            case "java.lang.Long":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Integer":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("###"));
                break;
            case "java.lang.Double":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            case "Currency":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("J$#,##0.00"));
                break;
        //style = wb.createCellStyle();
            case "java.lang.Boolean":
                break;
        //style = wb.createCellStyle();
            case "java.lang.String":
                break;
            case "java.util.Date":
                if (style == null) {
                    style = wb.createCellStyle();
                }   style.setDataFormat(wb.createDataFormat().getFormat("MMM dd, yyyy"));
                break;
            default:
                break;
        }

        return style;
    }

    public static HSSFFont createBoldFont(HSSFWorkbook wb, short size, short color) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(color);

        return font;
    }

    public static XSSFFont createBoldFont(XSSFWorkbook wb, short size, short color) {
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(color);

        return font;
    }

    public static Boolean isBasicDataType(String type) {

        switch (type) {
            case "java.lang.Long":
                return Boolean.TRUE;
            case "java.lang.Integer":
                return Boolean.TRUE;
            case "java.lang.Double":
                return Boolean.TRUE;
            case "java.lang.Boolean":
                return Boolean.TRUE;
            case "java.lang.String":
                return Boolean.TRUE;
            case "java.util.Date":
                return Boolean.TRUE;
            default:
                break;
        }

        return Boolean.FALSE;
    }

    public static void setExcelCellValue(HSSFWorkbook wb, HSSFCell cell, Object entity, String methodPath) {

        String dataType;
        HSSFCellStyle style;
        Object value = getBusinessEntityValue(entity, methodPath);

        if (value != null) {
            dataType = value.getClass().getCanonicalName();
            style = getExcelCellStyle(wb, dataType);

            switch (dataType) {
                case "java.lang.Long":
                    cell.setCellValue((Long) value);
                    break;
                case "java.lang.Integer":
                    cell.setCellValue((Integer) value);
                    break;
                case "java.lang.Double":
                    cell.setCellValue((Double) value);
                    break;
                case "java.lang.Boolean":
                    cell.setCellValue((Boolean) value);
                    break;
                case "java.lang.String":
                    cell.setCellValue(new HSSFRichTextString(value.toString()));
                    break;
                case "java.util.Date":
                    cell.setCellValue((Date) value);
                    break;
                default:
                    break;
            }

            // set the style if it is valid
            if (style != null) {
                cell.setCellStyle(style);
            }
        }
    }

    public static void setExcelCellValue(HSSFWorkbook wb, HSSFCell cell, Object value, String dataType, HSSFCellStyle style) {

        setExcelCellDataFormat(wb, dataType, style);

        try {
            if (value != null) {
                switch (dataType) {
                    case "java.lang.Long":
                        cell.setCellValue((Long) value);
                        break;
                    case "java.lang.Integer":
                        cell.setCellValue((Integer) value);
                        break;
                    case "java.lang.Double":
                        cell.setCellValue((Double) value);
                        break;
                    case "java.lang.Boolean":
                        cell.setCellValue((Boolean) value);
                        break;
                    case "java.lang.String":
                        cell.setCellValue(new HSSFRichTextString(value.toString()));
                        break;
                    case "java.util.Date":
                        cell.setCellValue((Date) value);
                        break;
                    default:
                        break;
                }

                if (style != null) {
                    cell.setCellStyle(style);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setExcelCellValue(HSSFWorkbook wb, HSSFSheet sheet, int rowIndex, int cellIndex,
            Object value, String dataType, HSSFCellStyle style) {

        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        HSSFCell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        HSSFCellStyle newStyle = setExcelCellDataFormat(wb, dataType, style);

        try {
            if (value != null) {
                switch (dataType) {
                    case "java.lang.Long":
                        cell.setCellValue((Long) value);
                        break;
                    case "java.lang.Integer":
                        cell.setCellValue((Integer) value);
                        break;
                    case "java.lang.Double":
                        cell.setCellValue((Double) value);
                        break;
                    case "Currency":
                        cell.setCellValue((Double) value);
                        break;
                    case "java.lang.Boolean":
                        cell.setCellValue((Boolean) value);
                        break;
                    case "java.lang.String":
                        cell.setCellValue(new HSSFRichTextString(value.toString()));
                        break;
                    case "java.util.Date":
                        cell.setCellValue((Date) value);
                        break;
                    default:
                        break;
                }

                cell.setCellStyle(newStyle);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setExcelCellValue(XSSFWorkbook wb, XSSFSheet sheet, int rowIndex, int cellIndex,
            Object value, String dataType, XSSFCellStyle style) {

        XSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        XSSFCell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }

        XSSFCellStyle newStyle;
        if (style != null) {
            newStyle = style; //setExcelCellDataFormat(wb, dataType, style);
        } else {
            newStyle = cell.getCellStyle();
        }

        try {
            if (value != null) {
                if (dataType.equals("java.lang.Long")) {
                    cell.setCellValue((Long) value);
                } else if (dataType.equals("java.lang.Integer")) {
                    cell.setCellValue((Integer) value);
                } else if (dataType.equals("java.lang.Double")) {
                    cell.setCellValue((Double) value);
                } else if (dataType.equals("Currency")) {
                    cell.setCellValue((Double) value);
                } else if (dataType.equals("java.lang.Boolean")) {
                    cell.setCellValue((Boolean) value);
                } else if (dataType.equals("java.lang.String")) {
                    cell.setCellValue(new XSSFRichTextString(value.toString()));
                } else if (dataType.equals("java.util.Date")) {
                    cell.setCellValue((Date) value);
                }

                cell.setCellStyle(newStyle);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Object getBusinessEntityValue(Object entity, String methodPath) {

        Object value = null;
        Class c;
        Method m;

        // split the path into class and methods string
        String[] path = methodPath.split("/");
        // get array of method names
        String[] methodNames = path[1].split("\\.");

        int i = -1;
        try {
            do {
                ++i;
                c = entity.getClass();
                m = c.getMethod(methodNames[i], (Class[]) null);
                entity = m.invoke(c.cast(entity), (Object[]) null);
                value = entity;
            } while (!isBasicDataType(m.getReturnType().getName()));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            System.out.println(ex);
        }

        return value;
    }

    /**
     * Determines the corresponding index for a maximum sequence of characters.
     * The sequence of characters are those that are obtained from a spreadsheet
     * column heading (eg AR).Currently it works for alphabets up to "ZZ" but
     * falls apart beyond that. Need to fix this!
     *
     * @param alphabet
     * @param len
     * @param num
     * @return
     */
    public static int convertAlphabetToNumber(String alphabet, int len, int num) {
        int letterIndex, previousLetterIndex;

        char chars[] = alphabet.toCharArray();
        letterIndex = getLetterIndex("" + chars[len - 1]);
        if ((len - 1) > 0) {
            previousLetterIndex = getLetterIndex("" + chars[len - 2]);
        } else {
            previousLetterIndex = -1;
        }
        num = num + letterIndex;
        if (previousLetterIndex != -1) {
            num = num + (previousLetterIndex + len - 1) * 26;
        }

        if ((len - 2) > 0) {
            String newAlphabet = alphabet.substring(0, len - 1);
            return convertAlphabetToNumber(newAlphabet, newAlphabet.length(), num);
        } else {
            return num;
        }
    }

    public static String getColumnPartOfCellReference(String cellReference) {
        String cellCol = "";

        try {
            char chars[] = cellReference.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isLetter(c)) {
                    cellCol = cellCol + c;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return cellCol;
        }

    }

    public static String getRowPartOfCellReference(String cellReference) {
        String cellRow = "";

        try {
            char chars[] = cellReference.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isDigit(c)) {
                    cellRow = cellRow + c;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return cellRow;
        }

    }

    public static void setExcelCellValue(HSSFWorkbook wb, HSSFSheet sheet, String cellReference,
            Object value, String dataType, HSSFCellStyle style) {

        int rowIndex = Integer.parseInt(getRowPartOfCellReference(cellReference)) - 1;
        int colIndex = convertAlphabetToNumber(
                getColumnPartOfCellReference(cellReference),
                getColumnPartOfCellReference(cellReference).length(),
                0);

        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        HSSFCell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        HSSFCellStyle newStyle = setExcelCellDataFormat(wb, dataType, style);

        try {
            if (value != null) {
                switch (dataType) {
                    case "java.lang.Long":
                        cell.setCellValue((Long) value);
                        break;
                    case "java.lang.Integer":
                        cell.setCellValue((Integer) value);
                        break;
                    case "java.lang.Double":
                        cell.setCellValue((Double) value);
                        break;
                    case "Currency":
                        cell.setCellValue((Double) value);
                        break;
                    case "java.lang.Boolean":
                        cell.setCellValue((Boolean) value);
                        break;
                    case "java.lang.String":
                        cell.setCellValue(new HSSFRichTextString(value.toString()));
                        break;
                    case "java.util.Date":
                        cell.setCellValue((Date) value);
                        break;
                    default:
                        break;
                }

                cell.setCellStyle(newStyle);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
