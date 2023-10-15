package fr.sid.ebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.sid.ebanking.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("select c from Client c where c.nom like :mc or c.prenom like:mc")
	List<Client> rechercheClientByNomOrPrenom(@Param("mc") String motCle);
	
	
}
