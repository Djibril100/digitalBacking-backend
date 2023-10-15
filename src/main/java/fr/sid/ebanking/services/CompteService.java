package fr.sid.ebanking.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.sid.ebanking.dtos.ClientDTO;
import fr.sid.ebanking.dtos.CompteBancaireDTO;
import fr.sid.ebanking.dtos.CompteCourantDTO;
import fr.sid.ebanking.dtos.CompteEpargneDTO;
import fr.sid.ebanking.dtos.HistoriqueCompteDTO;
import fr.sid.ebanking.dtos.OperationDTO;
import fr.sid.ebanking.entities.Client;
import fr.sid.ebanking.entities.CompteBancaire;
import fr.sid.ebanking.entities.CompteCourant;
import fr.sid.ebanking.entities.CompteEpargne;
import fr.sid.ebanking.entities.Operation;
import fr.sid.ebanking.enums.StatusDuCompte;
import fr.sid.ebanking.enums.TypeOperation;
import fr.sid.ebanking.exceptions.ClientNotFoundException;
import fr.sid.ebanking.exceptions.CompteNotFoundException;
import fr.sid.ebanking.exceptions.SoldeInsuffisantException;
import fr.sid.ebanking.mappers.DigitalBankingMapperService;
import fr.sid.ebanking.repositories.ClientRepository;
import fr.sid.ebanking.repositories.CompteRepository;
import fr.sid.ebanking.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CompteService implements CompteServiceInt {
	
	private CompteRepository compteRep;
	private ClientRepository clientRep;
	private OperationRepository operationRep;
	private DigitalBankingMapperService mapper;

	
	public void consulterCompte() {
		CompteBancaire c = compteRep.findById("612f9b47-b605-44b1-ac18-378eb8079a36").orElse(null);
		if(c!=null) {
			System.out.println("==========================================================");
			System.out.println(c.getId());
			System.out.println(c.getDateCreation());
			System.out.println(c.getSolde());
			System.out.println(c.getStatus());
			System.out.println(c.getClient().getPrenom()+" "+c.getClient().getNom());
			System.out.println(c.getClass().getSimpleName());
			if(c instanceof CompteCourant) {
				System.out.println(((CompteCourant) c).getDecouvert());
			}else if(c instanceof CompteEpargne) {
				System.out.println(((CompteEpargne) c).getTauxInteret());
			}
			
			c.getOperations().forEach(op->{
				System.out.println(op.getDateOperation()+"\t"+op.getMontant()+"\t"+op.getTypeOperation());
			});
			
		}
	}

	

	@Override
	public ClientDTO saveClient(ClientDTO clientDTO) {
		// TODO Auto-generated method stub
		log.info("Enregistrement d'un nouveau Client");
		Client client = mapper.fromClientDTO(clientDTO);
		Client savedClient = clientRep.save(client);
		
		return  mapper.fromClient(savedClient);
	}
	
	@Override
	public ClientDTO updateClient(ClientDTO clientDTO) {
		// TODO Auto-generated method stub
		log.info("Mise a jour d'un Client");
		Client client = mapper.fromClientDTO(clientDTO);
		Client updatedClient = clientRep.save(client);
		
		return  mapper.fromClient(updatedClient);
	}
	
	@Override
	public void deleteClient(Long idClient) {
		this.clientRep.deleteById(idClient);
	}

	

	@Override
	public List<ClientDTO> listClients() {
		// TODO Auto-generated method stub
		List<Client> clients = this.clientRep.findAll();
		
		List<ClientDTO> clientsDTOs = clients.stream().map(client -> mapper
				.fromClient(client)).collect(Collectors.toList());
		/*List<ClientDTO> clientsDTOs = new ArrayList<>();
		
		for(Client client: clients) {
			ClientDTO clientDTO = mapper.fromClient(client);
			clientsDTOs.add(clientDTO);
		}*/
		return clientsDTOs;
	}
	
	@Override
	public ClientDTO getClient(Long idClient) throws ClientNotFoundException {
		Client client = clientRep.findById(idClient)
				.orElseThrow(() -> new ClientNotFoundException("Client not found !"));
		return mapper.fromClient(client);
	}

	@Override
	public List<CompteBancaireDTO> listComptes() {
		// TODO Auto-generated method stub
		List<CompteBancaire> comptes =  this.compteRep.findAll();
		
		List<CompteBancaireDTO> comptesBancaireDTO = comptes.stream().map(compte->{
			if(compte instanceof CompteCourant) {
				CompteCourant courant = (CompteCourant) compte;
				return this.mapper.fromCompteCourant(courant);
			}else {
				CompteEpargne epargne = (CompteEpargne) compte;
				return this.mapper.fromCompteEpargne(epargne);
			}
		}).collect(Collectors.toList());
		return comptesBancaireDTO;
	}

	@Override
	public CompteBancaireDTO getCompteBancaireDTO(String idCompte) throws CompteNotFoundException {
		// TODO Auto-generated method stub
		CompteBancaire compte = compteRep.findById(idCompte).orElseThrow(()->
							new CompteNotFoundException("Compte bancaire not found !"));
		if(compte instanceof CompteCourant) {
			
			CompteCourant courant = (CompteCourant) compte;
			return this.mapper.fromCompteCourant(courant);
			
		}else{
			
			CompteEpargne epargne = (CompteEpargne) compte;
			return this.mapper.fromCompteEpargne(epargne);
		}
	}

	@Override
	public void crediter(String idCompte, double montant, String description) throws CompteNotFoundException {
		// TODO Auto-generated method stub
		CompteBancaire compte = compteRep.findById(idCompte).orElseThrow(()->
		new CompteNotFoundException("Compte bancaire not found !"));
		
		Operation operation = new Operation();
		operation.setDateOperation(new Date());
		operation.setMontant(montant);
		operation.setTypeOperation(TypeOperation.CREDIT);
		operation.setDescription(description);
		operation.setCompte(compte);
		operationRep.save(operation);
		compte.setSolde(compte.getSolde() + montant);
		compteRep.save(compte);
		
		
	}

	@Override
	public void debiter(String idCompte, double montant, String description) throws CompteNotFoundException, SoldeInsuffisantException {
		// TODO Auto-generated method stub
		CompteBancaire compte = compteRep.findById(idCompte).orElseThrow(()->
		new CompteNotFoundException("Compte bancaire not found !"));
		
		if(compte.getSolde()<montant)
			throw new SoldeInsuffisantException("Solde insuffisant !");
		Operation operation = new Operation();
		operation.setDateOperation(new Date());
		operation.setTypeOperation(TypeOperation.DEBIT);
		operation.setDescription(description);
		operation.setMontant(montant);
		operation.setCompte(compte);
		operationRep.save(operation);
		compte.setSolde(compte.getSolde() - montant);
		compteRep.save(compte);
		
	}

	@Override
	public void virement(String idCompteSource, String idCompteDestination, double montant) throws CompteNotFoundException, SoldeInsuffisantException {
		// TODO Auto-generated method stub
		this.debiter(idCompteSource, montant, "Transfert de "+montant+" a "+idCompteDestination);
		this.crediter(idCompteDestination, montant, montant+" transfere depuis "+idCompteSource);
		
	}



	@Override
	public CompteCourantDTO saveCompteCourant(double soldeInitial, double decouvert, Long idClient)
			throws ClientNotFoundException {
		// TODO Auto-generated method stub
		Client client = clientRep.findById(idClient).orElse(null);
		if(client == null) 
			throw new ClientNotFoundException("Client not found!");
		CompteCourant compte = new CompteCourant();
		compte.setId(UUID.randomUUID().toString());
		compte.setSolde(soldeInitial);
		compte.setDateCreation(new Date());
		compte.setDecouvert(decouvert);
		compte.setStatus(StatusDuCompte.CREATED);
		compte.setClient(client);
		CompteCourant savedCompteCourant = compteRep.save(compte);
		return this.mapper.fromCompteCourant(savedCompteCourant);
		
	}



	@Override
	public CompteEpargneDTO saveCompteEpargne(double soldeInitial, double taux, Long idClient)
			throws ClientNotFoundException {
		// TODO Auto-generated method stub
		Client client = clientRep.findById(idClient).orElse(null);
		if(client == null) 
			throw new ClientNotFoundException("Client not found!");
		CompteEpargne compte = new CompteEpargne();
		compte.setId(UUID.randomUUID().toString());
		compte.setSolde(soldeInitial);
		compte.setDateCreation(new Date());
		compte.setTauxInteret(taux);
		compte.setStatus(StatusDuCompte.CREATED);
		compte.setClient(client);
		CompteEpargne savedCompteEpargne = compteRep.save(compte);
		
		return this.mapper.fromCompteEpargne(savedCompteEpargne);
	}
	
	@Override
	public List<OperationDTO> historiqueCompte(String compteId){
		List<Operation> operationsCompte  = operationRep.findByCompteId(compteId);
		return operationsCompte.stream().map(operation-> this.mapper.fromOperationCompte(operation)).collect(Collectors.toList());
	}
	
	@Override
	public HistoriqueCompteDTO getHistoriqueCompte(String idCompte, int page, int size) throws CompteNotFoundException {
		// TODO Auto-generated method stub
		
		CompteBancaire compte = compteRep.findById(idCompte).orElse(null);
		if(compte==null) throw new CompteNotFoundException("Compte non trouve !");
		
		Page<Operation> operationsCompte = this.operationRep.findByCompteId(idCompte, PageRequest.of(page, size));
		HistoriqueCompteDTO histoCompteDTO = new HistoriqueCompteDTO();
		List<OperationDTO> operationsCompteDTO = operationsCompte.getContent().stream().map(
				op->this.mapper.fromOperationCompte(op)).collect(Collectors.toList());
		
		histoCompteDTO.setIdCompte(compte.getId());
		histoCompteDTO.setOperationsDTO(operationsCompteDTO);
		histoCompteDTO.setPageCourante(page);
		histoCompteDTO.setSize(size);
		histoCompteDTO.setSolde(compte.getSolde());
		histoCompteDTO.setTotalPages(operationsCompte.getTotalPages());
		
		return histoCompteDTO;
	}





	@Override
	public List<ClientDTO> rechercheClientByNomOrPrenom(String motCle) {
		// TODO Auto-generated method stub
		List<Client> clients = clientRep.rechercheClientByNomOrPrenom(motCle);
		
		List<ClientDTO> clientsDTO = clients.stream().map(client-> this.mapper.fromClient(client))
				.collect(Collectors.toList());
		
		return clientsDTO;
	}



	

}
