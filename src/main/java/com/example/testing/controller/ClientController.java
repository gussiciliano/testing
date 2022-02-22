package com.example.testing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	
	private ModelMapper modelMapper = new ModelMapper();
	
	//GET /clients
	@GetMapping("")
	public ResponseEntity<List<ClientDTO>> index() {
		List<ClientDTO> clientsDTO = new ArrayList<ClientDTO>();
		clientsDTO = clientService.findAll()
						.stream()
						.map(client -> modelMapper.map(client, ClientDTO.class))
						.collect(Collectors.toList());
		return new ResponseEntity<List<ClientDTO>>(clientsDTO, HttpStatus.OK);
	}
	
	//POST /clients
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("")
	public ResponseEntity create(@RequestBody Client client) {
		if(client == null) return new ResponseEntity<String>("client does not exists", HttpStatus.BAD_REQUEST);
		Optional<Client> cli = clientService.findByDocument(client.getDocument());
		if(cli.isPresent()) return new ResponseEntity<String>("duplicated document", HttpStatus.BAD_REQUEST);
		try {
			clientService.insertOrUpdate(client);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(modelMapper.map(client, ClientDTO.class), HttpStatus.CREATED);
	}
	
	//GET /clients/:id
	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable("id") int id) {
		if (id < 1) return new ResponseEntity<String>("id could not be less than 1", HttpStatus.BAD_REQUEST);
		Optional<Client> client = clientService.findById(id);
		return !client.isPresent()
				? new ResponseEntity<String>("", HttpStatus.OK)
				: new ResponseEntity<ClientDTO>(modelMapper.map(client.get(), ClientDTO.class), HttpStatus.OK);
	}
	
	//PUT /clients/:id
	@SuppressWarnings("rawtypes")
	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable("id") int id, @RequestBody Client client) {
		if (id < 1) return new ResponseEntity<String>("id could not be less than 1", HttpStatus.BAD_REQUEST);
		Optional<Client> newClient = clientService.findById(id);
		if (!newClient.isPresent()) return new ResponseEntity<String>("this client does not exists", HttpStatus.BAD_REQUEST);
		
		//possible changes (it will be better do it this into an internal Client Method
		newClient.get().setName(client.getName());
		
		try {
			clientService.insertOrUpdate(newClient.get());
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ClientDTO>(modelMapper.map(newClient.get(), ClientDTO.class), HttpStatus.OK);
	}
	
	//DELETE /clients/:id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		if (id < 1) return new ResponseEntity<String>("id could not be less than 1", HttpStatus.BAD_REQUEST);
		
		try {
			clientService.remove(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
