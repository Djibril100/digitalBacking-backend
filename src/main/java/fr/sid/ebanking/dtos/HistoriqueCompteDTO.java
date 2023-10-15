package fr.sid.ebanking.dtos;

import java.util.List;

import lombok.Data;

@Data
public class HistoriqueCompteDTO {
	
	private String idCompte;
	private double solde;
	private int pageCourante;
	private int totalPages;
	private int size;
	private List<OperationDTO> operationsDTO;

}
