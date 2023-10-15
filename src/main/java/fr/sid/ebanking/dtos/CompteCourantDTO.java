package fr.sid.ebanking.dtos;

import java.util.Date;

import fr.sid.ebanking.enums.StatusDuCompte;
import lombok.Data;

@Data
public class CompteCourantDTO extends CompteBancaireDTO {

	private String id;
	private Date dateCreation;
	private double solde;
	private StatusDuCompte status;
	private double decouvert;
	private ClientDTO clientDTO;
}
