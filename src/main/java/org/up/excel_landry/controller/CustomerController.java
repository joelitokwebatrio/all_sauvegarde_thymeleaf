package org.up.excel_landry.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.up.excel_landry.domain.Customer;
import org.up.excel_landry.service.CustomerService;

import java.util.List;

import static org.up.excel_landry.utils.Utils.MSG;
import static org.up.excel_landry.utils.Utils.UPLOADED_THE_FILE_SUCCESSFULLY;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    /**
     * rediger l utilisateur vers la page permettant d'uploader un fichier excel
     *
     * @param model
     * @return
     */
    @GetMapping("/files/new")
    public String newFile(Model model) {
        return "upload_form";
    }

    /**
     * uploader le fichier excel
     *
     * @param model
     * @param file
     * @return
     */
    @PostMapping("/upload-customers-data")
    public String uploadCustomersData(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        this.customerService.saveCustomersToDatabase(file);
        message = UPLOADED_THE_FILE_SUCCESSFULLY + file.getOriginalFilename();
        model.addAttribute(MSG, message);
        return "upload_form";


    }

    /**
     * recuperation de la liste des utilisation inserer dans la base de donnes
     *
     * @return
     */
    @GetMapping("/lists")
    public String getCustomers(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("listCustomers", customers);
        return "files";
    }
}
