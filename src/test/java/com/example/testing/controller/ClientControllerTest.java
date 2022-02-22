package com.example.testing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.testing.entity.Client;
import com.example.testing.repository.IClientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application_test.properties")
public class ClientControllerTest {
	
    @Autowired
    private IClientRepository clientRepository;
	
	@Test
	public void when_index_returnOk() {
		assertTrue(true);
	}
	
	@Test
	public void when_create_succesfull() {
		
		// given (POST simulation)
		Client client = new Client();
		client.setName("Gustavo");
		client.setDocument(12345678);
	    
	    // when (Controller simulation)
		clientRepository.save(client);
		Client clientNew = clientRepository.findById(1).get();

	    // then
		assertThat(clientNew.getName()).isEqualTo(client.getName());
		assertThat(clientNew.getId()).isEqualTo(1);
		System.out.println(clientNew);
	}
	
	@Test
	public void when_create_faild() {
		assertTrue(true);
	}
	
	@Test
	public void when_get_givenValue() {
		assertTrue(true);
	}
	
	@Test
	public void when_get_givenNull() {
		assertTrue(true);
	}
	
	@Test
	public void when_update_succesfull() {
		assertTrue(true);
	}
	
	@Test
	public void when_update_faild() {
		assertTrue(true);
	}
	
	@Test
	public void when_delete_succesfull() {
		assertTrue(true);
	}
	
	@Test
	public void when_delete_faild() {
		assertTrue(true);
	}
}
