package ecommerce_panier;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.persistence.EntityManager;

import com.persistance.Articles;

import ecommerce_panier.itf.Ecommerce_PanierItf;


@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public class Ecommerce_panierImpl implements Ecommerce_PanierItf{
	
	private EntityManager entityManager;
	
	private List articles;

	@Override
	public void ajouterArticleAuPanier(Articles article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProduitPanier ajouterProduit(ProduitPanier ppanier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProduitPanier supprimerProduit(ProduitPanier ppanier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float retournerTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void vider() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProduitPanier> listerProduits() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

}
