/**
 * 
 */
package ecommerce.composant.article.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.persistance.Articles;

import ecommerce.composant.article.itf.ComposantArticleItf;

/**
 * @author gkossi
 *
 */
@Stateless
@Remote(ComposantArticleItf.class)
public class ComposantArticleImpl implements ComposantArticleItf {
	@PersistenceContext(unitName="Persist") 
	EntityManager em;

	@Override
	public List<Articles> listerArticles() {


		ArrayList<Articles> result= new ArrayList<Articles>();
	    Query requete = em.createQuery("SELECT a from Articles a WHERE 1=1", Articles.class);

	    
	    //	   result = 
	    List<Articles> resultList = requete.getResultList();
	    for (Articles object : resultList) {
	    	result.add(object);
	
		}
		return result;
	}

}
