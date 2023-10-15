package fr.sid.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Compe Courant")
@Data @NoArgsConstructor @AllArgsConstructor
public class CompteCourant extends CompteBancaire {
	
	private double decouvert;

}
