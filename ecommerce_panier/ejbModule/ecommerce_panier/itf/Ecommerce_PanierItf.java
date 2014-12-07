package ecommerce_panier.itf;

import com.persistance.Articles;

public interface Ecommerce_PanierItf {

	public void ajouterArticleAuPanier(Articles article);
	public void checkOut();

}
