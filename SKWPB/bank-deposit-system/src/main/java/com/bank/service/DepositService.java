package com.bank.service;

import com.bank.entity.Deposit;
import com.bank.entity.Client;
import com.bank.entity.DepositType;
import com.bank.repository.DepositRepository;
import com.bank.repository.ClientRepository;
import com.bank.repository.DepositTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final ClientRepository clientRepository;
    private final DepositTypeRepository depositTypeRepository;

    public DepositService(DepositRepository depositRepository,
                          ClientRepository clientRepository,
                          DepositTypeRepository depositTypeRepository) {
        this.depositRepository = depositRepository;
        this.clientRepository = clientRepository;
        this.depositTypeRepository = depositTypeRepository;
    }

    // Получить все вклады
    public List<Deposit> getAllDeposits() {
        return depositRepository.findAll();
    }

    // Найти вклад по ID
    public Optional<Deposit> getDepositById(Long id) {
        return depositRepository.findById(id);
    }

    // Сохранить вклад
    @Transactional
    public Deposit saveDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    // Удалить вклад
    @Transactional
    public void deleteDeposit(Long id) {
        depositRepository.deleteById(id);
    }

    // Открыть новый вклад
    @Transactional
    public Deposit openDeposit(Long clientId, Long depositTypeId, BigDecimal amount) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        DepositType depositType = depositTypeRepository.findById(depositTypeId)
                .orElseThrow(() -> new RuntimeException("Тип вклада не найден"));

        Deposit deposit = new Deposit();
        deposit.setClient(client);
        deposit.setDepositType(depositType);
        deposit.setAmount(amount);
        deposit.setCurrentBalance(amount); // начальный баланс равен сумме вклада
        deposit.setOpenDate(LocalDate.now());
        deposit.setStatus("АКТИВЕН");

        // Генерация номера счета (пример: DEP-2025-XXXX)
        String accountNumber = "DEP-" + LocalDate.now().getYear() + "-" +
                String.format("%04d", depositRepository.count() + 1);
        deposit.setAccountNumber(accountNumber);

        return depositRepository.save(deposit);
    }

    // Закрыть вклад
    @Transactional
    public Deposit closeDeposit(Long depositId) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Вклад не найден"));

        deposit.setStatus("ЗАКРЫТ");
        return depositRepository.save(deposit);
    }

    // Начислить проценты
    @Transactional
    public Deposit calculateInterest(Long depositId) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Вклад не найден"));

        if (!"АКТИВЕН".equals(deposit.getStatus())) {
            throw new RuntimeException("Вклад не активен");
        }

        DepositType depositType = deposit.getDepositType();
        BigDecimal interestRate = depositType.getInterestRate();

        // Простой расчет: проценты на остаток
        // Формула: currentBalance * (interestRate / 100) / 12
        BigDecimal interest = deposit.getCurrentBalance()
                .multiply(interestRate) // умножаем на процентную ставку (уже в процентах, например 5.5)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP) // делим на 100, чтобы получить долю
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP); // делим на 12 месяцев

        deposit.setCurrentBalance(deposit.getCurrentBalance().add(interest));

        return depositRepository.save(deposit);
    }

    // Получить вклады по клиенту
    public List<Deposit> getDepositsByClientId(Long clientId) {
        return depositRepository.findByClientId(clientId);
    }

    // Получить активные вклады
    public List<Deposit> getActiveDeposits() {
        return depositRepository.findByStatus("АКТИВЕН");
    }

    // Получить общую сумму всех вкладов
    public BigDecimal getTotalDepositsAmount() {
        BigDecimal total = depositRepository.getTotalAmount();
        return total != null ? total : BigDecimal.ZERO;
    }
}