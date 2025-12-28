package com.bank.controller;

import com.bank.repository.ClientRepository;
import com.bank.repository.DepositRepository;
import com.bank.repository.DepositTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private DepositTypeRepository depositTypeRepository;

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("clientCount", clientRepository.count());
        model.addAttribute("depositCount", depositRepository.count());
        model.addAttribute("depositTypes", depositTypeRepository.findAll());
        return "reports";
    }
}