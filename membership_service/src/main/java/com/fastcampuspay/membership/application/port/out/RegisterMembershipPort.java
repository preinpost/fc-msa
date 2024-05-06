package com.fastcampuspay.membership.application.port.out;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;

public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(
            com.fastcampuspay.membership.domain.Membership.MembershipName membershipName,
            com.fastcampuspay.membership.domain.Membership.MembershipEmail membershipEmail,
            com.fastcampuspay.membership.domain.Membership.MembershipAddress membershipAddress,
            com.fastcampuspay.membership.domain.Membership.MembershipIsValid membershipIsValid,
            com.fastcampuspay.membership.domain.Membership.MembershipIsCorp membershipIsCorp
    );
}
