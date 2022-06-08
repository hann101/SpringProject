package hello.core.beanfind;


import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextBasicFindTest {
 AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext(AppConfig.class);

 @Test
 @DisplayName("Bean 이름으로 확인")
 void findName (){
     MemberService memberService = ac.getBean("memberService", MemberService.class);
     assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
 }

    @Test
    @DisplayName("Bean 타입으로 확인")
    void findType1 (){
        MemberService memberService = ac.getBean( MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("Bean 구체 타입으로 확인")
    void findTypeImpl (){
        MemberService memberService = ac.getBean( "memberService",MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("Bean Fail 테스트 확인")
    void findTypeFail (){
//        MemberService xxxx = ac.getBean("xxxx", MemberService.class);
        //NoSuchBeanDefinitionException이 터지면 테스트가 성공한것이고,
        //ac.getBean을 실행했을때 발생해야한다.
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxx", MemberService.class));
    }
}
