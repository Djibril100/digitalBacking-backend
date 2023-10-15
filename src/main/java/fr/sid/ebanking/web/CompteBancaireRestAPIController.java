package fr.sid.ebanking.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sid.ebanking.dtos.CompteBancaireDTO;
import fr.sid.ebanking.dtos.HistoriqueCompteDTO;
import fr.sid.ebanking.dtos.OperationDTO;
import fr.sid.ebanking.exceptions.CompteNotFoundException;
import fr.sid.ebanking.services.CompteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin("http://localhost:4200")
public class CompteBancaireRestAPIController {
	
	private CompteService service;
	
	/**
	 * L'injection des dependences en spring == @Autowired
	 * ****/
	public CompteBancaireRestAPIController(CompteService service) {
		this.service = service;
	}
	
	@GetMapping("/comptes/{id}")
	public CompteBancaireDTO getCompteBancaire(@PathVariable(name = "id") String idCompte) throws CompteNotFoundException {
		return this.service.getCompteBancaireDTO(idCompte);
	}
	
	@GetMapping("/comptes")
	public List<CompteBancaireDTO> listComptes(){
		
		return this.service.listComptes();	
	}
	
	@GetMapping("/comptes/{id}/operations")
	public List<OperationDTO> getHistory(@PathVariable(name="id") String compteId){
		return this.service.historiqueCompte(compteId);
	}
	
	@GetMapping("/comptes/{id}/pageOperations")
	public HistoriqueCompteDTO getHistoriqueCompte(
			@PathVariable(name="id") String idCompte, 
			@RequestParam(name="page", defaultValue = "0") int page,
			@RequestParam(name="size", defaultValue = "5") int size) throws CompteNotFoundException {
		
			return this.service.getHistoriqueCompte(idCompte, page, size);
	}

}
