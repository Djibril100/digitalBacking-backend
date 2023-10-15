package fr.sid.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Compte Epargne")
@Data @NoArgsConstructor @AllArgsConstructor
public class CompteEpargne extends CompteBancaire {
	
	private double tauxInteret;

}
