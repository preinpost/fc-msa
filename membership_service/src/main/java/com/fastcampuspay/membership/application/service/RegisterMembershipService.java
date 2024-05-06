package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public com.fastcampuspay.membership.domain.Membership registerMembership(RegisterMembershipCommand command) {

        // bis logic -> DB
        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new com.fastcampuspay.membership.domain.Membership.MembershipName(command.getName()),
                new com.fastcampuspay.membership.domain.Membership.MembershipEmail(command.getEmail()),
                new com.fastcampuspay.membership.domain.Membership.MembershipAddress(command.getAddress()),
                new com.fastcampuspay.membership.domain.Membership.MembershipIsValid(true),
                new com.fastcampuspay.membership.domain.Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
