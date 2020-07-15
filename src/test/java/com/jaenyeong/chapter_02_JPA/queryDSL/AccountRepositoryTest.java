package com.jaenyeong.chapter_02_JPA.queryDSL;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void queryDsl() {
		QAccount account = QAccount.account;

		Predicate predicate = account.firstName.containsIgnoreCase("jaenyeong")
				.and(account.lastName.startsWith("kim"));

		Optional<Account> one = accountRepository.findOne(predicate);
		assertThat(one).isEmpty();
	}
}
