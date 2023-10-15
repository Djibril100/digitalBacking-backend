package fr.sid.ebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.sid.ebanking.entities.CompteBancaire;

public interface CompteRepository extends JpaRepository<CompteBancaire, String> {

}
