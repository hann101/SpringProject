package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("할인 10프로")
    void vip_o(){
        //given
        Member member = new Member(1L,"memberVip", Grade.VIP);
        //when
        int discount = rateDiscountPolicy.discount(member, 10000);
        //result
        System.out.println("discount = " + discount);
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("할인 10프로 실패")
    void basic_o(){
        //given
        Member member = new Member(2L,"memberVip", Grade.BASIC);
        //when
        int discount = rateDiscountPolicy.discount(member, 10000);
        //result
        System.out.println("discount = " + discount);
        assertThat(discount).isEqualTo(0);
    }

}