package org.up.excel_landry.service;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.up.excel_landry.domain.Customer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@UtilityClass
public class ExcelUploadService {

    /**
     * cette methode me permet de tester  si un fichier est un bel et bien
     * un fihcier excel avant d'effectuer des operations sur ce derniere
     *
     * @param file
     * @return
     */
    public static boolean isExcelFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return (fileName != null && (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")));
    }

    /**
     * cette methode permet de ouvrir un fichier excel et appelle  la met getCustomer pour lire les informations
     * d'un Customer.
     *
     * @param inputStream
     * @return
     */
    public static  List<Customer> getCustomersDataFromExcel(InputStream inputStream) {
        List<Customer> customers = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("customers");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Customer customer = getCustomer(row);
                customers.add(customer);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return customers;
    }

    /**
     * lire les lignes d'un fchier excel qui font reference aux information d'une personne
     * En effet le fichier contient plusieurs lignes en chaque contient les informations d'une personne.
     * cette methode permet de lire les informations d'une personne.
     *
     * @param row
     * @return
     */
    private static Customer getCustomer(Row row) {
        /**
         * commencer avec une iteration sur les cellules
         */
        Iterator<Cell> cellIterator = row.iterator();
        int cellIndex = 0;
        Customer customer = new Customer();
        while (cellIterator.hasNext()) {
            /**
             * parcourir les cellules et les mappers les lignes
             */
            Cell cell = cellIterator.next();
            switch (cellIndex) {
                case 0 -> customer.setCustomerId((int) cell.getNumericCellValue());
                case 1 -> customer.setFirstName(cell.getStringCellValue());
                case 2 -> customer.setLastName(cell.getStringCellValue());
                case 3 -> customer.setCountry(cell.getStringCellValue());
                case 4 -> customer.setTelephone((int) cell.getNumericCellValue());
                default -> {
                }
            }
            cellIndex++;
        }
        return customer;
    }

}
