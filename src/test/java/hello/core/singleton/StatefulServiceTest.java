package hello.core.singleton;

import hello.core.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);


    @Test
    void statefuServiceTest(){
        StatefulService statefulService1 = ac.getBean( StatefulService.class);
        StatefulService statefulService2 = ac.getBean( StatefulService.class);

        //Thread A사용자
        statefulService1.order("UserA",10000);
        //Thread B 사용자
        statefulService2.order("UserB",20000);

        int num = statefulService1.getPrice();
        System.out.println("num = " + num);

        assertThat(statefulService1.getPrice()).isNotSameAs(statefulService2.getPrice());
    }


     static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }

}