package fr.sid.ebanking.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.sid.ebanking.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long> {
	
	List<Operation> findByCompteId(String compteId);
	Page<Operation> findByCompteId(String compteId, Pageable page);

}
