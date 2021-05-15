package ru.app.bank.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import ru.app.bank.domain.Account;
import ru.app.bank.dto.RequestDto;
import ru.app.bank.service.AccountServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final AccountServiceImpl accountService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
//    @CachePut(value = "account-single", key = "#account.id")
//    @Caching(cacheable = @Cacheable("account-single"), put = @CachePut("account-single"))
    @Cacheable(value = "account-single", keyGenerator = "keyGenerator")
    @PostMapping("/account")
    public Account create(@RequestBody RequestDto account) {
        LOG.info("работает метод " + Thread.currentThread().getStackTrace()[1].getMethodName());
        Account acc = accountService.createAccount(account);
        return acc;
    }

    @Cacheable(value = "account-single")
    @GetMapping("/account/{id}")
    public Account read(@PathVariable Long id)  {
        LOG.info("работает метод " + Thread.currentThread().getStackTrace()[1].getMethodName());
        return accountService.getAccount(id);
    }
    @Cacheable(value = "post-top")
    @GetMapping("/account")
    public List<Account> readAll() {
        LOG.info("работает метод " + Thread.currentThread().getStackTrace()[1].getMethodName());
        List<Account> accountList = accountService.readAllAccount();
        return accountList;
    }
    @CacheEvict(value = {"post-top","account-single"}, allEntries=true)
    @GetMapping("/account/clean")
    public String cleanCache() {
        LOG.info("работает метод " + Thread.currentThread().getStackTrace()[1].getMethodName());
        return "RedisCache clean";
    }
 /* @Cacheable(value = "account-single", key = "#id")
    @CachePut(value = "account-single", key = "#account.id")
    @PutMapping("/account")
    public ResponseEntity<Account> update(@RequestBody RequestDto account) {
        Long number = account.getNumber();
        if (number == null) {
            return ResponseEntity.badRequest().build();
        }
        Integer op = account.getOperation();
        Account updatedAcc = accountService.updateAccount(number, op);
        return new ResponseEntity(updatedAcc,HttpStatus.OK);
    }

    @CacheEvict(value = "post-single", key = "#id")
    @DeleteMapping("/account/{id}")
    public void deleteAccountByID(@PathVariable Long id) {
    }*/
}
