package com.fastcampuspay.membership.application.port.out;


import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {
}
