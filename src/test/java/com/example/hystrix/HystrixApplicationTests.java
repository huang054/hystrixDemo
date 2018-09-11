package com.example.hystrix;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.hystrix.order.CommandOrder;
import com.example.hystrix.user.CommandUser;


public class HystrixApplicationTests {

	 private final static Logger LOGGER = LoggerFactory.getLogger(HystrixApplicationTests.class);
	public static void main(String[] args) throws Exception {
	    CommandOrder commandPhone = new CommandOrder("手机");
	    CommandOrder command = new CommandOrder("电视");
	    //阻塞方式执行
	    String execute = commandPhone.execute();
	    LOGGER.info("execute=[{}]", execute);
	    //异步非阻塞方式
	 
		    Future<String> queue = command.queue();
		    String value = queue.get(200, TimeUnit.MILLISECONDS);
		    LOGGER.info("value=[{}]", value);
	    

	    CommandUser commandUser = new CommandUser("张三");
	    String name = commandUser.execute();
	    LOGGER.info("name=[{}]", name);
	}

}
