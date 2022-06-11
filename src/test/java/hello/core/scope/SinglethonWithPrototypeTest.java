package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.ref.Cleaner;

public class SinglethonWithPrototypeTest {


    @Test
    public void singlethonClientUseTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
        //Bean 조회
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);

        int count1 = clientBean1.logic();
        int count2 = clientBean2.logic();

        Assertions.assertThat(count1).isEqualTo(1);
        Assertions.assertThat(count2).isEqualTo(2);
        //ProtoType Bean은 Bean호출시 새로운 컨테이너를 만들기 때문에
        //결과는 0 -> 1이 나와야한다. 하지만 2가 나온다? 새로운 컨테이너를 만들지 않았다는 뜻.
        //주체가 싱글톤이고 싱글톤의 생명주기는 스프링이 종료됨과 동시에 끝난다.
        //근데 싱글톤이 생성되면서 proto를 호출하게 되는데 .. 클라이언트가 호출해서 프로토의 컨테이너가 유지되는 이유는
        // 싱글톤을 통해서 호출 되기 때문이다. 싱글톤은 하나이다.
        //그렇ㄱ다면 어떻게?
        //1) 프로토타입을 호출할때 마다 스프링 컨테이너를 지속적으로 호출.



    }

    static class ClientBean {
//        private final PrototypeBean prototypeBean;
//
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

//        1)
//        @Autowired
//        private AnnotationConfigApplicationContext ac;

//        2)
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeans;

//        3)
//        implementation 'javax.inject:javax.inject:1' gradle 추가 필수 @Autowired
//        private Provider<PrototypeBean> provider;

        public int logic() {
            //1) clientBean 호출 할때 마다 Bean을 호출하여 새로운 컨테이너를 만들어 전달.
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();

            //2) 두번째 방법.
            PrototypeBean prototypeBean = prototypeBeans.getObject();


//          3) 세번째 방법.
//            PrototypeBean prototypeBean = provider.get();

            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() {
            count++; }
        public int getCount() {
            return count;
        }
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
