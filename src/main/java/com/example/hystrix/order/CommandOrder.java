package com.example.hystrix.order;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * Function:订单服务
 *
 * 
 *         
 * @since JDK 1.8
 */
public class CommandOrder extends HystrixCommand<String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandOrder.class);
    private String orderName;
    public CommandOrder(String orderName) {
        super(Setter.withGroupKey(
                //服务分组
                HystrixCommandGroupKey.Factory.asKey("OrderGroup"))
                //线程分组
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("OrderPool"))
                //线程池配置
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withKeepAliveTimeMinutes(5)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(10000))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
        )
        ;
        this.orderName = orderName;
    }
    @Override
    public String run() throws Exception {
        LOGGER.info("orderName=[{}]", orderName);
        TimeUnit.MILLISECONDS.sleep(100);
        return "OrderName=" + orderName;
    }
}