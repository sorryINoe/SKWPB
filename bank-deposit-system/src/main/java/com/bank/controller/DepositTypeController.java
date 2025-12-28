package com.bank.controller;

import com.bank.entity.DepositType;
import com.bank.service.DepositTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deposit-types")
public class DepositTypeController {

    private final DepositTypeService depositTypeService;

    public DepositTypeController(DepositTypeService depositTypeService) {
        this.depositTypeService = depositTypeService;
    }

    // Список всех типов вкладов
    @GetMapping
    public String listDepositTypes(Model model) {
        List<DepositType> depositTypes = depositTypeService.getAllDepositTypes();
        model.addAttribute("depositTypes", depositTypes);
        return "deposit-types/list";  // Убедись, что есть этот шаблон
    }

    // Форма добавления нового типа вклада
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("depositType", new DepositType());
        return "deposit-types/form";
    }

    // Сохранение типа вклада
    @PostMapping("/save")
    public String saveDepositType(@ModelAttribute DepositType depositType) {
        depositTypeService.saveDepositType(depositType);
        return "redirect:/deposit-types";
    }

    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DepositType depositType = depositTypeService.getDepositTypeById(id)
                .orElseThrow(() -> new RuntimeException("Тип вклада не найден"));
        model.addAttribute("depositType", depositType);
        return "deposit-types/form";
    }

    // Удаление типа вклада
    @GetMapping("/delete/{id}")
    public String deleteDepositType(@PathVariable Long id) {
        depositTypeService.deleteDepositType(id);
        return "redirect:/deposit-types";
    }
}