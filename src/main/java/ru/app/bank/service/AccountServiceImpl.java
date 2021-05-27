package ru.app.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.app.bank.domain.Account;
import ru.app.bank.dto.RequestDto;
import ru.app.bank.repository.AccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl  {
    private final AccountRepository accountRepository;

    @Transactional
    public Account updateAccount(Long numberOfAccount, Integer operation) {
        Account account = accountRepository.findById(numberOfAccount).orElseThrow();
        account.setBalance(account.getBalance() + operation);
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    public List<Account> readAllAccount() {
       return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
       return accountRepository.findById(id).orElse(null);
    }

    public Account createAccount(RequestDto requestDto) {
        String name = requestDto.getName();
        String data = requestDto.getData();
        Integer operation = requestDto.getOperation();
        Account account = new Account().setBalance(operation).setOwner(name).setData(data);
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }
}
