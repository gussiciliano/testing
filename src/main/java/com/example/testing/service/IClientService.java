package com.example.testing.service;
import java.util.List;
import java.util.Optional;

import com.example.testing.entity.Client;


public interface IClientService {

	public List<Client> findAll();
	
	public Client insertOrUpdate(Client client);
	
	public Optional<Client> findById(int id);
	
	public Optional<Client> findByDocument(int document);
	
	public void remove(int id);
}

