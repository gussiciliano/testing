package com.example.testing.service.implementation;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.testing.entity.Client;
import com.example.testing.repository.IClientRepository;
import com.example.testing.service.IClientService;

@Service("clientService")
public class ClientService implements IClientService {

	@Autowired
	@Qualifier("clientRepository")
	private IClientRepository clientRepository;

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public Client insertOrUpdate(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Optional<Client> findById(int id) {
		return clientRepository.findById(id);
	}
	
	@Override
	public boolean remove(int id) {
		try {
			clientRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}
