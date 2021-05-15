package ru.app.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.app.bank.domain.Account;

import javax.persistence.LockModeType;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE) // делаем SELECT BEFORE UPDATE
    Account findAccountById(Long id);
}
