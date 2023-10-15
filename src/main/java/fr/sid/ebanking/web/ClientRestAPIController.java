package fr.sid.ebanking.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.sid.ebanking.dtos.ClientDTO;
import fr.sid.ebanking.exceptions.ClientNotFoundException;
import fr.sid.ebanking.services.CompteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:4200")
public class ClientRestAPIController {
	
	private CompteService service;
	
	@GetMapping("/clients")
	public List<ClientDTO> clients(){
		return this.service.listClients();
	}
	
	@GetMapping("/clients/{id}")
	public ClientDTO getClient(@PathVariable(name = "id") Long idClient) throws ClientNotFoundException {
		return this.service.getClient(idClient);
	}
	
	@PostMapping("/clients")
	public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
		return this.service.saveClient(clientDTO);
	}
	
	@PutMapping("/clients/{id}")
	public ClientDTO updateClient(@PathVariable(name = "id") Long idClient, @RequestBody ClientDTO clientDTO) {
		clientDTO.setId(idClient);
		return this.service.updateClient(clientDTO);
	}
	
	@DeleteMapping("/clients/{id}")
	public void deleteClient(@PathVariable(name = "id") Long idClient) {
		this.service.deleteClient(idClient);
	}
	
	@GetMapping("/clients/recherche")
	public List<ClientDTO> rechercheClientByNomOrPrenom(@RequestParam(name = "motCle", defaultValue="") String motCle){
		return this.service.rechercheClientByNomOrPrenom("%"+motCle+"%");
	}
	

}
