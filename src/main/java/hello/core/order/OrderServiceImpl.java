package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; //고정 폴리시

    @Autowired
    private DiscountPolicy rateDiscountPolicy;


    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = rateDiscountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //회원정보 조회
        Member member = memberRepository.findById(memberId);

        //할인정책
        int discount = discountPolicy.discount(member, itemPrice);

        return new Order(memberId,itemName,itemPrice,discount);
    }
}
