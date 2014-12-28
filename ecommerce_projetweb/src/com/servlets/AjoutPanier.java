package com.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beanUtils.ItemPanierBean;
import beanUtils.PanierBean;

//import com.persistance.Articles;

import ecommerce.composant.article.itf.ComposantArticleItf;



/**
 * AjouterPanier ajouter les articles dans le panier
 */

@WebServlet("/ajoutPanier")
public class AjoutPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ComposantArticleItf composantArticleItf;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutPanier() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idArticle= request.getParameter("id");
		HttpSession session = request.getSession();
		//on recupere le panier 
		PanierBean panier =(PanierBean) session.getAttribute("panier");
		if(panier==null){
			panier= new PanierBean();
		}
		ItemPanierBean item= new ItemPanierBean();
		 item.setIdArticle(Integer.valueOf(idArticle));
		 item.setQuantite(1);
		panier.addItem(item);
		session.setAttribute("panier",panier);
		RequestDispatcher requestDisp=getServletContext().getRequestDispatcher("/initServlet");
    	requestDisp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// aller dans ton composant article recuperer liste des articles
		
	}

}
