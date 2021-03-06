package com.example.testing.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testing.entity.Client;

@Repository("clientRepository")
public interface IClientRepository extends JpaRepository<Client, Serializable> {
	public Optional<Client> findByDocument(int focument);
}
