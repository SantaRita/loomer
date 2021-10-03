package org.srcom.loomerdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;

@SpringBootApplication
@EnableCaching
public class LoomerDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoomerDbApplication.class, args);
	}

}
