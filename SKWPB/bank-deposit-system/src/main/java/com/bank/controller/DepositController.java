package com.bank.controller;

import com.bank.entity.Deposit;
import com.bank.entity.Client;
import com.bank.entity.DepositType;
import com.bank.service.DepositService;
import com.bank.service.ClientService;
import com.bank.service.DepositTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deposits")
public class DepositController {

    private final DepositService depositService;
    private final ClientService clientService;
    private final DepositTypeService depositTypeService;

    public DepositController(DepositService depositService,
                             ClientService clientService,
                             DepositTypeService depositTypeService) {
        this.depositService = depositService;
        this.clientService = clientService;
        this.depositTypeService = depositTypeService;
    }

    // Список всех вкладов
    @GetMapping
    public String listDeposits(Model model) {
        List<Deposit> deposits = depositService.getAllDeposits();
        model.addAttribute("deposits", deposits);
        return "deposits/list";
    }

    // Форма открытия вклада
    @GetMapping("/open")
    public String openDepositForm(Model model) {
        List<Client> clients = clientService.getAllClients();
        List<DepositType> depositTypes = depositTypeService.getAllDepositTypes();

        model.addAttribute("deposit", new Deposit());
        model.addAttribute("clients", clients);
        model.addAttribute("depositTypes", depositTypes);
        return "deposits/open-form";
    }

    // Открытие вклада
    @PostMapping("/open")
    public String openDeposit(@ModelAttribute Deposit deposit,
                              @RequestParam Long clientId,
                              @RequestParam Long depositTypeId) {
        depositService.openDeposit(clientId, depositTypeId, deposit.getAmount());
        return "redirect:/deposits";
    }

    // Расчет процентов
    @GetMapping("/{id}/interest")
    public String calculateInterest(@PathVariable Long id) {
        depositService.calculateInterest(id);
        return "redirect:/deposits";
    }

    // Закрытие вклада
    @GetMapping("/{id}/close")
    public String closeDeposit(@PathVariable Long id) {
        depositService.closeDeposit(id);
        return "redirect:/deposits";
    }
}