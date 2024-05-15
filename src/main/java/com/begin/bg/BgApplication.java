package com.begin.bg;

import com.begin.bg.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BgApplication {
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BgApplication.class, args);
	}


}
