package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 빈으로 등록해야 함 (빈네임 accountAuditAware)
@Service
public class AccountAuditAware implements AuditorAware<AccountAudit> {

	@Override
	public Optional<AccountAudit> getCurrentAuditor() {
		System.out.println("Looking for current User");
		return Optional.empty();
	}
}
