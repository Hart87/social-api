package com.hart.social;

import com.hart.social.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CLRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(CLRunner.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("CL RUNNER");


    }
}