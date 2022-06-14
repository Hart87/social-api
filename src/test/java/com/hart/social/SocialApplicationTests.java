package com.hart.social;

import com.hart.social.controller.v1.PostsController;
import com.hart.social.controller.v1.UsersController;
import com.hart.social.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SocialApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;


//	@Autowired
//	UsersController usersController;
//
//	@Autowired
//	PostsController postsController;
//
//
//	@Test
//	public void contextLoads() throws Exception {
//		assertThat(usersController).isNotNull();
//		assertThat(postsController).isNotNull();
//	}



}
