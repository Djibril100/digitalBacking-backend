package fr.sid.ebanking.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.sid.ebanking.dtos.ClientDTO;
import fr.sid.ebanking.dtos.CompteCourantDTO;
import fr.sid.ebanking.dtos.CompteEpargneDTO;
import fr.sid.ebanking.dtos.OperationDTO;
import fr.sid.ebanking.entities.Client;
import fr.sid.ebanking.entities.CompteCourant;
import fr.sid.ebanking.entities.CompteEpargne;
import fr.sid.ebanking.entities.Operation;

@Service
public class DigitalBankingMapperService {

	public ClientDTO fromClient(Client client) {

		ClientDTO clientDTO = new ClientDTO();
		BeanUtils.copyProperties(client, clientDTO);
		// clientDTO.setId(client.getId());
		// clientDTO.setNom(client.getNom());
		// clientDTO.setPrenom(client.getPrenom());
		// clientDTO.setEmail(client.getEmail());

		return clientDTO;
	}

	public Client fromClientDTO(ClientDTO clientDTO) {

		Client client = new Client();
		BeanUtils.copyProperties(clientDTO, client);
		return client;
	}

	public CompteCourantDTO fromCompteCourant(CompteCourant compteCourant) {

		CompteCourantDTO compteCourantDTO = new CompteCourantDTO();
		BeanUtils.copyProperties(compteCourant, compteCourantDTO);
		compteCourantDTO.setClientDTO(fromClient(compteCourant.getClient()));
		compteCourantDTO.setType(compteCourant.getClass().getSimpleName());
		
		return compteCourantDTO;
	}

	public CompteCourant fromCompteCourantDTO(CompteCourantDTO compteCourantDTO) {

		CompteCourant compteCourant = new CompteCourant();
		BeanUtils.copyProperties(compteCourantDTO, compteCourant);
		compteCourant.setClient(fromClientDTO(compteCourantDTO.getClientDTO()));
		
		return compteCourant;
	}

	public CompteEpargneDTO fromCompteEpargne(CompteEpargne compteEpargne) {

		CompteEpargneDTO compteEpargneDTO = new CompteEpargneDTO();
		BeanUtils.copyProperties(compteEpargne, compteEpargneDTO);
		compteEpargneDTO.setClientDTO(fromClient(compteEpargne.getClient()));
		compteEpargneDTO.setType(compteEpargne.getClass().getSimpleName());
		
		return compteEpargneDTO;
		
	}

	public CompteEpargne fromCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO) {
		CompteEpargne compteEpargne = new CompteEpargne();
		BeanUtils.copyProperties(compteEpargneDTO, compteEpargne);
		compteEpargne.setClient(fromClientDTO(compteEpargneDTO.getClientDTO()));
		
		return compteEpargne;
	}
	
	public OperationDTO fromOperationCompte(Operation operation) {
		
		OperationDTO operationDTO = new OperationDTO();
		BeanUtils.copyProperties(operation, operationDTO);
		return operationDTO;
	}
	
	
}
