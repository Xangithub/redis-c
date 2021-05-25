package ru.app.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.app.bank.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
