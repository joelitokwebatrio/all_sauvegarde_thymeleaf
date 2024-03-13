package org.up.excel_landry.service;

import org.springframework.web.multipart.MultipartFile;
import org.up.excel_landry.domain.Customer;

import java.util.List;


public interface CustomerService {
    void saveCustomersToDatabase(MultipartFile file);
    List<Customer> getCustomers();



}
