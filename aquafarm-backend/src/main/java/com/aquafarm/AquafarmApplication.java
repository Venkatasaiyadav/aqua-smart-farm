// src/main/java/com/aquafarm/AquafarmApplication.java
package com.aquafarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AquafarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquafarmApplication.class, args);
        System.out.println("""
            
            ╔═══════════════════════════════════════╗
            ║   🦐 AquaFarm Pro is RUNNING!         ║
            ║   http://localhost:8080               ║
            ║   Health: /api/auth/health            ║
            ╚═══════════════════════════════════════╝
            
        """);
    }
}