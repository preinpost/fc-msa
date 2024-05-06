package com.fastcampuspay.membership.application.port.out;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(com.fastcampuspay.membership.domain.Membership.MembershipName membershipName, com.fastcampuspay.membership.domain.Membership.MembershipEmail membershipEmail, com.fastcampuspay.membership.domain.Membership.MembershipAddress membershipAddress, com.fastcampuspay.membership.domain.Membership.MembershipIsValid membershipIsValid, com.fastcampuspay.membership.domain.Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getMembershipName(),
                        membershipEmail.getMembershipEmail(),
                        membershipAddress.getMembershipAddress(),
                        membershipIsValid.isMembershipIsValid(),
                        membershipIsCorp.isMembershipIsCorp()
                )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(com.fastcampuspay.membership.domain.Membership.MembershipId membershipId) {
        return membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
    }
}
