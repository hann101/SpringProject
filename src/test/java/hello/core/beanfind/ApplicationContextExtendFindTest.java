package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextExtendFindTest {
    AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 클래스 조회시, 자식클래스가 2개이상인 애들은 중복에러를 밠생시키낟.")
    void findByParentTypeDuplicated(){
//        DiscountPolicy discountPolicy = ac.getBean(DiscountPolicy.class);
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class)
        );
    }

    @Test
    @DisplayName("부모 클래스 조회시, 자식클래스가 2개이상이면 새로운 이름을 지정해준다. .")
    void findByParentTypeBeanName(){
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(DiscountPolicy.class);
    }
    @Test
    @DisplayName("특정 하위 타입으로 지정.")
    void findBySubType(){
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
   @Test
    @DisplayName("부모 타입으로 조회하기 ")
    void findBeanByParentType(){
       Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
       assertThat(beansOfType.size()).isEqualTo(2);
       for (String key : beansOfType.keySet()) {
           System.out.println("key = " + key + "\nvalues = "+ beansOfType.get(key ));

       }
   }   @Test
    @DisplayName("부모 타입으로 조회하기 ")
    void findBeanByObject(){
       Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
       for (String key : beansOfType.keySet()) {
           System.out.println("key = " + key + "\nvalues = "+ beansOfType.get(key ));

       }
   }


    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
