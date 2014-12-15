package ecommerce_panier.itf;

import java.util.List;

import com.persistance.Articles;

import ecommerce_panier.ProduitPanier;

public interface Ecommerce_PanierItf {

	public void ajouterArticleAuPanier(Articles article);
	public ProduitPanier ajouterProduit(ProduitPanier ppanier);
	public ProduitPanier supprimerProduit(ProduitPanier ppanier);
	public float retournerTotal();
	public void vider(); 
	public List<ProduitPanier> listerProduits();

}
