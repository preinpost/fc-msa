package com.fastcampuspay.money.application.service;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.application.port.out.GetMembershipPort;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.fastcampuspay.common.UseCase;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@UseCase
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final CountDownLatchManager countDownLatchManager;

    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    private final GetMembershipPort membershipPort;

    private final IncreaseMoneyPort increaseMoneyPort;

    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 충전. 증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 있는지, 고객과 연동괸 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 (뱅킹)

        // 4. 증액을 위한 "기록", 요청 상태로 MoneyChangingRequest를 생성한다. (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)


        // 6-1. 결과가 정상적이라면. 성공적으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if (memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(
                    increaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(0),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID())
                    )
            );
        } else {
            return null;
        }

        // 성공 시에 멤버의 MemberMoney 값 증액이 필요


        // 6-2 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴

    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // Subtask
        // 정의: 각 서비스에 특정 membershipId 로 validation 을 하기 위한 Task.


        // 1. SubTask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName(String.join("", "validMemberTask : ", "멤버십 유효성 검사"))
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking Sub task
        // Banking Account Validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName(String.join("", "validBankingAccountTask : ", "뱅킹 계좌 유효성 검사"))
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();


        // Amount Money Firmbanking -> (생략) 무조건 ok 받았다고 가정.


        List<SubTask> subTaskList = List.of(validMemberTask, validBankingAccountTask);


        RechargingMoneyTask task = RechargingMoneyTask.builder()

                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("fastcampus")
                .build();


        // 2. Kafka Cluster Produce
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // 3. Wait
        try {
            // consume 할 때 까지 기다리는 용도
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        // 받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 받아야 한다.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if ("success".equals(result)) {
            // 4-1. Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    command.getAmount()
            );

            if (memberMoneyJpaEntity != null) {
                return mapper.mapToDomainEntity(
                        increaseMoneyPort.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                                new MoneyChangingRequest.MoneyChangingType(0),
                                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                                new MoneyChangingRequest.MoneyChangingStatus(1),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID())
                        )
                );
            }


        } else {
            // 4-2 Consume fail, Logic
            return null;
        }

        // 5. Consume ok, Logic



        return null;
    }
}
