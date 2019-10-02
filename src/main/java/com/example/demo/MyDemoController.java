package com.example.demo;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
public class MyDemoController {
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/hello/{name}")
	public String getName(@PathVariable String name)
	{
		return "Hello "+name;
	}
	
	@RequestMapping("/hello/{first}/{second}")
	public String add(@PathVariable("first") Integer first, @PathVariable("second") Integer second)
	{
		return "sum is "+(first+second);
	}

	@HystrixCommand(fallbackMethod = "fallback"/*
												 * ,commandProperties=
												 * 
												 * @HystrixProperty(name=
												 * "hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds",
												 * value="1000")
												 */)
	@RequestMapping("/hello")
	public String getCheck()
	{
		String url="http://MYSECONDCLIENT/second/message";
		return restTemplate.getForObject(url, String.class);
	}

	public String fallback()
	{
		return "hello from fallback";
	}
}
