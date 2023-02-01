package springapp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppController {
	@Autowired
	ClientMongoRepository crud_client;

	public static void main(String[] args) {
		SpringApplication.run(AppController.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "COUCOU SPRING";
	}
	@GetMapping("/listeClientMongo")
	@CrossOrigin
	public List<ClientMongo> findAllClientMongo()throws Exception{
		return crud_client.findAll();
	}
	@PostMapping("/login")
	@CrossOrigin
	public int Login(String email, String mdp) throws Exception {
		Client c = new Client();
		c.setEmail(email);
		c.setMdp(mdp);		
		return c.Login();
	}

	@PostMapping("/updateDemande")
	@CrossOrigin
	public int RechargerCompte (int idClient) throws Exception{
		Client cl = new Client();
		cl.setIdClient(idClient);
		return cl.RechargerCompte();

	}

	@PostMapping("/inscription")
	@CrossOrigin
	public int Inscrisption(@RequestBody Client c) throws Exception{
		return c.Inscrisption();
	}

	@GetMapping("/getNames/{id}")
	@CrossOrigin
	public String findClientNames(@PathVariable Integer id)throws Exception{
		Client cl = new Client();
		cl.setIdClient(id);
		return cl.findNames();
	}

	@GetMapping("/allEncheres")
	@CrossOrigin
	public ArrayList<Produit> AllEncheres()throws Exception{
		return Produit.findAllProduitDispo();
	}

	@GetMapping("/allEncheresClient/{id}")
	@CrossOrigin
	public ArrayList<Produit> AllEncheres(@PathVariable Integer id) throws Exception{
		Client cl = new Client();
		cl.setIdClient(id);
		return cl.findAllProduitDispo();
	}

	@GetMapping("/history/{id}")
	@CrossOrigin
	public ArrayList<Produit> history(@PathVariable Integer id)throws Exception{
		Client cl = new Client();
		cl.setIdClient(id);
		return cl.findHistory();
	}

	@PostMapping("/deposerOffre")
	@CrossOrigin
	public float DeposerOffre(int idClient, int idProduit, float offre) throws Exception{
		try {
			Client c = new Client();
			Produit p = new Produit();
			c.setIdClient(idClient);
			p.setId(idProduit);
			c.setOffre(offre);
			return c.DeposerOffre(p, offre);
		} catch (Exception e) {
			if(e.getMessage().equals("Offre trop basse"))
				return 0;
			else
				return -1;
		}
	}
		
	@GetMapping("/recherchetous")
	@CrossOrigin
	public static ArrayList<Produit> rechercheTous(String nom_produit)throws Exception{
		return Produit.rechercheTous(nom_produit);
	}

	@GetMapping("/recherche/{id}")
	@CrossOrigin
	public ArrayList<Produit> recherche(String nom_produit , @PathVariable Integer id) throws Exception{
		Client cl = new Client();
		cl.setIdClient(id);
		return cl.recherche(nom_produit);
	}

	@GetMapping("/dejaEncheri")
	@CrossOrigin
	public Integer dejaEncheri(Integer idClient , Integer idProduit)throws Exception{
		Client cl = new Client();
		Produit p = new Produit();
		cl.setIdClient(idClient);
		p.setId(idProduit);
		return cl.isAlreadyBid(p);
	}
}
