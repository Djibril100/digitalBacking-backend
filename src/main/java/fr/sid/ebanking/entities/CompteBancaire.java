package fr.sid.ebanking.entities;

import java.util.Date;
import java.util.List;

import fr.sid.ebanking.enums.StatusDuCompte;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 30, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor @AllArgsConstructor
public class CompteBancaire {
	
	@Id
	private String id;
	private Date dateCreation;
	private double solde;
	@Enumerated(EnumType.STRING)
	private StatusDuCompte status;
	
	@ManyToOne
	private Client client;
	
	@OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
	private List<Operation> operations;
	
	
	

}
