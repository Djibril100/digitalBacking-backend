package fr.sid.ebanking;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.sid.ebanking.dtos.ClientDTO;
import fr.sid.ebanking.dtos.CompteBancaireDTO;
import fr.sid.ebanking.dtos.CompteCourantDTO;
import fr.sid.ebanking.dtos.CompteEpargneDTO;
import fr.sid.ebanking.dtos.CompteBancaireDTO;
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
import fr.sid.ebanking.repositories.ClientRepository;
import fr.sid.ebanking.repositories.CompteRepository;
import fr.sid.ebanking.repositories.OperationRepository;
import fr.sid.ebanking.services.CompteService;
import fr.sid.ebanking.services.CompteServiceInt;

@SpringBootApplication
public class DigitalBankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingBackendApplication.class, args);
	}
	
	@Bean
	CommandLineRunner cmdLineRunner(CompteServiceInt compteService) {
		return args->{
			
			String[] noms = {"Barry", "Diallo", "Komara", "Bah", "Soumah", "Bangoura", "Camara"};
			
			Stream.of("Djibril", "Souleymane", "Yaya", "Oumar", "Mohamed").forEach(prenom->{
				ClientDTO clientDTO = new ClientDTO();
				clientDTO.setPrenom(prenom);
				String nom = noms[new Random().nextInt(noms.length)];
				clientDTO.setNom(nom);
				clientDTO.setEmail(prenom+"."+nom+"@gmail.com");
				compteService.saveClient(clientDTO);
			});
			
			compteService.listClients().forEach(client->{
				try {
					compteService.saveCompteCourant(Math.random()*8500, 8500, client.getId());
					compteService.saveCompteEpargne(Math.random()*8500, 0.5, client.getId());
					
					
				} catch (ClientNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
			
			List<CompteBancaireDTO> comptes = compteService.listComptes();
			for(CompteBancaireDTO compte:comptes) {
				for (int i = 0; i < 10; i++) {
					String compteId;
					if(compte instanceof CompteEpargneDTO) {
						compteId = ((CompteEpargneDTO) compte).getId();
						
					} else {
						compteId = ((CompteCourantDTO) compte).getId();
					}
					compteService.crediter(compteId, (250+Math.random()*500), "Operation de credit");
					compteService.debiter(compteId, Math.random()*500, "Operation de debit");
				}	
			}
			
			
				
			
		};
	}
	
	//@Bean
	CommandLineRunner start(ClientRepository clientRep, CompteRepository compteRep, 
							OperationRepository operationRep) {
			return args->{
				
				String[] noms = {"Barry", "Diallo", "Komara", "Bah", "Soumah", "Bangoura", "Camara"};
				
				Stream.of("Djibril", "Souleymane", "Yaya", "Oumar", "Mohamed").forEach(prenom->{
					Client client = new Client();
					client.setPrenom(prenom);
					String nom = noms[new Random().nextInt(noms.length)];
					client.setNom(nom);
					client.setEmail(prenom+"."+nom+"@gmail.com");
					clientRep.save(client);
				});
				
				clientRep.findAll().forEach(client->{
					CompteCourant courant = new CompteCourant();
					courant.setId(UUID.randomUUID().toString());
					courant.setDateCreation(new Date());
					courant.setSolde(Math.random()*8500);
					courant.setStatus(StatusDuCompte.CREATED);
					courant.setDecouvert(8500);
					courant.setClient(client);
					compteRep.save(courant);
					
					CompteEpargne epargne = new CompteEpargne();
					epargne.setId(UUID.randomUUID().toString());
					epargne.setDateCreation(new Date());
					epargne.setSolde(Math.random()*8500);
					epargne.setStatus(StatusDuCompte.CREATED);
					epargne.setTauxInteret(0.5);
					epargne.setClient(client);
					compteRep.save(epargne);
				});
				
				compteRep.findAll().forEach(compte->{
					for (int i = 0; i < 10; i++) {
						Operation operation = new Operation();
						operation.setDateOperation(new Date());
						operation.setMontant(Math.random()*500);
						operation.setTypeOperation(Math.random()>0.5 ? TypeOperation.CREDIT : TypeOperation.DEBIT);
						operation.setCompte(compte);
						operationRep.save(operation);
						
					}
				});	
				
				
			};
		
	}

}
