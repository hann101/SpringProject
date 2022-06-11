package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출(빈 생성전), url = "+url);
        connect();
        call("초기화 연결 메세지 (빈 생성전)");
    }

    public void call(String message) {
        System.out.println("call: "+  url +"\nmessage: "+message);

    }

    private void connect() {
        System.out.println("connect: "+ url);
    }

    //ㅅㅓ비스 종료시 호출
    public void disconnect(){
        System.out.println("close: "+ url);
    }

    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }

}
