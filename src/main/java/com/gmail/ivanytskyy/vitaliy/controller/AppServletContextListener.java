package com.gmail.ivanytskyy.vitaliy.controller;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class AppServletContextListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {		
		ServletContext sc = sce.getServletContext();
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");		
		sc.setAttribute("appContext", appContext);	
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub		
	}
}