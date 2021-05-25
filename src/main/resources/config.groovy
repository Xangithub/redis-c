package ru.app.bank.config

import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import ru.app.bank.domain.Account
import org.springframework.cache.interceptor.KeyGenerator
import ru.app.bank.repository.AccountRepository

import java.lang.reflect.Method
import java.nio.charset.StandardCharsets

class KeyGeneratorImpl implements KeyGenerator {
    final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    Object generate(Object target, Method method, Object... params) {
        StringBuffer redisKey = new StringBuffer()
        if (params.length > 0) {
            LOG.info(Arrays.deepToString(params));
            redisKey.append(Arrays.deepToString(params))
        }
        return DigestUtils.sha512Hex(redisKey.toString().getBytes(StandardCharsets.UTF_8))
    }
}

class CommandLineRunnerImpl implements CommandLineRunner {
    AccountRepository repository

    CommandLineRunnerImpl(AccountRepository repository) {
        this.repository = repository
    }

    @Override
    void run(String... args) throws Exception {
        repository.save(new Account().setOwner("Atlant").setBalance(1000))
        repository.save(new Account().setOwner("Kolt").setBalance(2000))
    }
}

beans {
    keyGenerator(KeyGeneratorImpl)
    commandLineRunner(CommandLineRunnerImpl)
}
