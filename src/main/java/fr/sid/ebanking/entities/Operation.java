package fr.sid.ebanking.entities;

import java.util.Date;

import fr.sid.ebanking.enums.TypeOperation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Operation {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateOperation;
	private double montant;
	private String description;
	@Enumerated(EnumType.STRING)
	private TypeOperation typeOperation;
	
	@ManyToOne
	private CompteBancaire compte;
}
