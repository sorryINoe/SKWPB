package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    @GetMapping
    public String calculatorForm() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam BigDecimal amount,
                            @RequestParam BigDecimal rate,
                            @RequestParam Integer months,
                            Model model) {
        // Простая формула: сумма * ставка * месяцев / 1200
        BigDecimal interest = amount.multiply(rate)
                .multiply(BigDecimal.valueOf(months))
                .divide(BigDecimal.valueOf(1200), 2, RoundingMode.HALF_UP);

        model.addAttribute("initial", amount);
        model.addAttribute("rate", rate);
        model.addAttribute("months", months);
        model.addAttribute("interest", interest);
        model.addAttribute("total", amount.add(interest));

        return "calculator-result";
    }
}