package com.parking.app;

import java.io.File;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        File script = new File("./chatbot/server.py");
//        String[] params = new String[2];
//        params[0] = "python";
//        params[1] = script.getAbsolutePath();
//
//        Process exec = Runtime.getRuntime().exec(params);
//        exec.waitFor();
        
    }

}
