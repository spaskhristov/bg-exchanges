package com.gmail.spaskhristov.exchange.until;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gmail.spaskhristov.exchange.model.ExchangeInstitution;
import com.gmail.spaskhristov.exchange.model.ExchangeInstitutionType;

public class ExcelRead {

  private static List<ExchangeInstitution> institutions = new ArrayList<ExchangeInstitution>();
  private static List<ExchangeInstitution> institutionsOther = new ArrayList<ExchangeInstitution>();
  final static Logger logger = Logger.getLogger(ExcelRead.class);

  private ExcelRead() {}

  public static List<ExchangeInstitution> getInstitutions() {
    checkIsEmpty();
    return institutions;
  }

  public static List<ExchangeInstitution> getInstitutionsOther() {
    checkIsEmpty();
    return institutionsOther;
  }

  private static void checkIsEmpty() {
    if (institutions.isEmpty()) {
      try {
        readInstitutionsFromExcel();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private static void readInstitutionsFromExcel() throws IOException {
    StringBuilder path = new StringBuilder();
    path.append(File.separator);
    path.append("Banks.xlsx");
    InputStream file =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(path.toString());
    XSSFWorkbook workbook = new XSSFWorkbook(file);
    XSSFSheet worksheet = workbook.getSheetAt(0);
    for (int rowCount = 1; rowCount < Constants.EXCEL_ROWS; rowCount++) {
      XSSFRow row = worksheet.getRow(rowCount);
      String id = row.getCell(0).getStringCellValue().trim();
      String name = row.getCell(1).getStringCellValue().trim();
      String nameEn = row.getCell(2).getStringCellValue().trim();
      String url = row.getCell(3).getStringCellValue().trim();
      String type = row.getCell(4).getStringCellValue().trim();

      if (type.equals(ExchangeInstitutionType.bank.name())) {
        institutions.add(new ExchangeInstitution(Integer.parseInt(id), name, nameEn, url));
        if (id.equals("1") || id.equals("2") || id.equals("3") || id.equals("22")) {
          institutionsOther.add(new ExchangeInstitution(Integer.parseInt(id), name, nameEn, url));
        }
      } else {
        institutions.add(new ExchangeInstitution(Integer.parseInt(id), name, nameEn, url,
            ExchangeInstitutionType.change));
        if (id.equals("10") || id.equals("11") || id.equals("12") || id.equals("13")
            || id.equals("15") || id.equals("19")) {
          institutionsOther.add(new ExchangeInstitution(Integer.parseInt(id), name, nameEn, url,
              ExchangeInstitutionType.change));
        }
      }
    }
    workbook.close();
    file.close();
  }

}
