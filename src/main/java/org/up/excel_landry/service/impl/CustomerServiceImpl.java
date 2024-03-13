package org.up.excel_landry.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.up.excel_landry.domain.Customer;
import org.up.excel_landry.repository.CustomerRepository;
import org.up.excel_landry.service.CustomerService;
import org.up.excel_landry.service.ExcelUploadService;

import java.io.IOException;
import java.util.List;

import static org.up.excel_landry.utils.Utils.THE_FILE_IS_NOT_A_VALID_EXCEL_FILE;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * sauvegarde des informations du fichier dans la base de donnees
     *
     * @param file
     */
    public void saveCustomersToDatabase(MultipartFile file) {
        if (ExcelUploadService.isExcelFile(file)) {
            try {
                List<Customer> customers = ExcelUploadService.getCustomersDataFromExcel(file.getInputStream());
                this.customerRepository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException(THE_FILE_IS_NOT_A_VALID_EXCEL_FILE);
            }
        }
    }

    /**
     * récupérations de la liste des utilisateurs sauvegarder dans la base de donnees
     *
     * @return
     */
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
