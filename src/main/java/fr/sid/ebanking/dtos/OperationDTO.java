package fr.sid.ebanking.dtos;

import java.util.Date;

import fr.sid.ebanking.enums.TypeOperation;
import lombok.Data;

@Data
public class OperationDTO {

	private Long id;
	private Date dateOperation;
	private double montant;
	private TypeOperation typeOperation;
	private String description;
}
