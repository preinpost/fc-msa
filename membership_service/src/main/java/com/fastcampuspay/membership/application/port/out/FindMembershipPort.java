package com.fastcampuspay.membership.application.port.out;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;

public interface FindMembershipPort {
    MembershipJpaEntity findMembership(com.fastcampuspay.membership.domain.Membership.MembershipId membershipId);
}
