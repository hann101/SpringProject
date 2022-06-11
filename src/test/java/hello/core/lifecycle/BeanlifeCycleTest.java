package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanlifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{
//     2)   @Bean(initMethod = "init", destroyMethod = "close")
//      3)
        @Bean
        public NetworkClient networkClient(){
            //객체 생성
            NetworkClient networkClient = new NetworkClient();
           //Spring Bean 등록 (필드 주입시 / 생성자 주입은 제외)

            //@Bean(initMethod 등의 어노테이션을 사용하면 초기화와 )
            networkClient.setUrl("http://spring.net");
            return networkClient;
        }
    }
}
