package com.bank.service;

import com.bank.entity.DepositType;
import com.bank.repository.DepositTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepositTypeService {

    private final DepositTypeRepository depositTypeRepository;

    public DepositTypeService(DepositTypeRepository depositTypeRepository) {
        this.depositTypeRepository = depositTypeRepository;
    }

    public List<DepositType> getAllDepositTypes() {
        return depositTypeRepository.findAll();
    }

    public Optional<DepositType> getDepositTypeById(Long id) {
        return depositTypeRepository.findById(id);
    }

    public DepositType saveDepositType(DepositType depositType) {
        return depositTypeRepository.save(depositType);
    }

    public void deleteDepositType(Long id) {
        depositTypeRepository.deleteById(id);
    }
}