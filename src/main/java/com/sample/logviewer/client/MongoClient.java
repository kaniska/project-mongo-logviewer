package com.sample.logviewer.client;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoClient {

	public static void main(String[] args) {
		System.out.println("Simple Mongo Client");
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext(
				"META-INF/spring/test-beans-context.xml");

		TestMongoService helloMongo = context.getBean(TestMongoService.class);
		helloMongo.run();

	}

}
