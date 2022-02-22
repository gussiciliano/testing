package com.example.testing.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testing.dto.ClientDTO;
import com.example.testing.entity.Client;
import com.example.testing.service.IClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	@Qualifier("clientService")
	private IClientService clientService;
	
	//GET /clients
	@GetMapping("")
	public ResponseEntity<List<Client>> index() {
		List<ClientDTO> clientsDTO = new ArrayList<ClientDTO>(); //TODO
		List<Client> clients = clientService.findAll();
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
	}
	
	//POST /clients
	@PostMapping("")
	public ResponseEntity<Client> create(@RequestBody Client client) {
		clientService.insertOrUpdate(client);
		return new ResponseEntity<Client>(client, HttpStatus.CREATED);
	}
	
	//GET /clients/:id
	@GetMapping("/{id}")
	public ResponseEntity<Client> get(@PathVariable("id") int id) {
		Client client = clientService.findById(id).get();
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}
	
	//PUT /clients/:id
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable("id") int id, @RequestBody Client client) {
		Client clientAux = clientService.findById(id).get();
		clientAux.setName(client.getName());
		clientAux.setDocument(client.getDocument());
		clientService.insertOrUpdate(clientAux);
		return new ResponseEntity<Client>(clientAux, HttpStatus.OK);
	}
	
	//DELETE /clients/:id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		clientService.remove(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
