package com.xu;


import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@MapperScan("com.xu.mapper")
@EnableEurekaClient
@SpringBootApplication
public class CertificateApplication {

    public static int testValue;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(CertificateApplication.class,args);
        Map<String,String> map = new ConcurrentHashMap<>();
        String oldValue = map.put("1","2");
        System.out.println(oldValue);
        ThreadPoolExecutor s = new ThreadPoolExecutor(4,10,2, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        FutureTask<List<Integer>> listFuture = new FutureTask<>(new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                List<Integer> list =new ArrayList<>();
                TimeUnit.SECONDS.sleep(1);
                list.add(20);
                return list;
            }
        });

        s.execute(listFuture);
        s.execute(listFuture);
        s.execute(listFuture);
        s.execute(listFuture);
        List<Integer> result = listFuture.get();
        TimeUnit.SECONDS.sleep(5);
        s.execute(listFuture);

    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule(){
        return new BestAvailableRule();
    }

}
class Base{
    public Base(String s){
        System.out.print("B");
    }
}