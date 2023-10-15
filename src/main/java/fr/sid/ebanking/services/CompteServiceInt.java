package fr.sid.ebanking.services;

import java.util.List;

import fr.sid.ebanking.dtos.ClientDTO;
import fr.sid.ebanking.dtos.CompteBancaireDTO;
import fr.sid.ebanking.dtos.CompteCourantDTO;
import fr.sid.ebanking.dtos.CompteEpargneDTO;
import fr.sid.ebanking.dtos.HistoriqueCompteDTO;
import fr.sid.ebanking.dtos.OperationDTO;
import fr.sid.ebanking.entities.Client;
import fr.sid.ebanking.entities.CompteBancaire;
import fr.sid.ebanking.exceptions.ClientNotFoundException;
import fr.sid.ebanking.exceptions.CompteNotFoundException;
import fr.sid.ebanking.exceptions.SoldeInsuffisantException;

public interface CompteServiceInt {
	
	ClientDTO saveClient(ClientDTO clientDTO);
	ClientDTO updateClient(ClientDTO clientDTO);
	void deleteClient(Long idClient);
	CompteCourantDTO saveCompteCourant(double soldeInitial, double decouvert, Long idClient) throws ClientNotFoundException;
	CompteEpargneDTO saveCompteEpargne(double soldeInitial, double taux, Long idClient) throws ClientNotFoundException;
	List<ClientDTO> listClients();
	ClientDTO getClient(Long idClient) throws ClientNotFoundException;
	List<CompteBancaireDTO> listComptes();
	List<OperationDTO> historiqueCompte(String compteId);
	CompteBancaireDTO getCompteBancaireDTO(String idCompte) throws CompteNotFoundException;
	void crediter(String idCompte, double montant, String description) throws CompteNotFoundException;
	void debiter(String idCompte, double montant, String description) throws CompteNotFoundException, SoldeInsuffisantException;
	void virement(String idCompteSource, String idCompteDestination, double montant) throws CompteNotFoundException, SoldeInsuffisantException;
	
	HistoriqueCompteDTO getHistoriqueCompte(String idCompte, int page, int size) throws CompteNotFoundException;
	
	List<ClientDTO> rechercheClientByNomOrPrenom(String motCle);

	

}
