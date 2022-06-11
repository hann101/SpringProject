package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    public void singleton(){
        //Bean 등록
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SinglethonBean.class);
        //Bean 호출
        SinglethonBean bean1 = ac.getBean(SinglethonBean.class);
        SinglethonBean bean2 = ac.getBean(SinglethonBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        Assertions.assertThat(bean1).isSameAs(bean2);
    }


    @Scope("singleton")
    static class SinglethonBean{

        @PostConstruct
        public void init(){
            System.out.println("SinglethonBean.init 빈 생성시점에서 메서드 실행");
        }

        @PreDestroy
        public void destory(){
            System.out.println("SinglethonBean.destory");
        }
    }
}
