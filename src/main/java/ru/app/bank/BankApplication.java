package ru.app.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import ru.app.bank.domain.Account;
import ru.app.bank.repository.AccountRepository;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@SpringBootApplication(exclude = { RedisRepositoriesAutoConfiguration.class })
@EnableCaching
public class BankApplication {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuffer redisKey = new StringBuffer();

			if (params.length > 0) {
//				redisKey.append("-").append(Arrays.deepToString(params));
				LOG.info(Arrays.deepToString(params));
				redisKey.append(Arrays.deepToString(params));
			}
			return  org.apache.commons.codec.digest.DigestUtils.sha512Hex(redisKey.toString().getBytes(StandardCharsets.UTF_8));
		};
	}
	/*@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.create(connectionFactory);
	}*/

/*	@Bean
	JedisConnectionFactory jedisConnectionFactory(){
		return  new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<Map, Map> redisTemplate() {

		RedisTemplate<Map,Map> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}*/

	/**
	 * Наполнение для БД
	 */
	@Bean
	public CommandLineRunner demo(AccountRepository repository) {
		return (args) -> {
			repository.save(new Account().setOwner("Atlant").setBalance(1000));
			repository.save(new Account().setOwner("Kolt").setBalance(2000));
		};
	}

}
