package com.musicbook.test.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.musicbook.entity.Artist;
import com.musicbook.service.EmailService;
import com.musicbook.service.ImageService;

@Configuration
@PropertySource("classpath:persistence-mysql-test.properties")
public class TestConfig {

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Environment env;
	
	@Bean
	public EmailService emailService() {
		
		return new MockEmailService();
	}
	
	@Bean
	public ImageService imageService() {
		
		return new MockImageService();
	}
	
	@Bean
	public DataSource securityDataSource() {
		
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	
	@Bean
	public MockMvc mockMvc() {
		
		return MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	private int getIntProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	private class MockEmailService implements EmailService {
		
		@Override
		public void sendVerificationEmail(Artist artist) {

		}
	}
	
	private class MockImageService implements ImageService {
		
		@Override
		public boolean processAndSaveImage(MultipartFile file, String dirPropertyName, String name) {
			
			return true;
		}
		
		@Override
		public void deleteImage(String dirPropertyName, String name) {
			
		}
	}
}
