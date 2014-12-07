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

import com.persistance.Articles;

import ecommerce.composant.article.itf.ComposantArticleItf;



/**
 * InitServlet pour la gestion des articles
 */
@WebServlet("/initServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ComposantArticleItf composantArticleItf;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// aller dans ton composant article recuperer liste des articles
				ArrayList<Articles> listArticles= (ArrayList<Articles>) composantArticleItf.listerArticles();
				
				
		        RequestDispatcher requestDisp=getServletContext().getRequestDispatcher("/afficherProduits.jsp");
		    	request.setAttribute("listeArticle", listArticles);
		    	requestDisp.forward(request, response);
		    	
		    	
		    	
		    	
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// aller dans ton composant article recuperer liste des articles
	}

}
