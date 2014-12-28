package com.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjoutProduit
 */
@WebServlet("/AjoutProduit")
public class AjoutProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutProduit() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();
		out.write("<html><head><title>Result</title></head><body>");
//		out.write(accueilItf.afficher());
		System.out.println("Ok");
		out.write("</body>");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String desig = request.getParameter("desig");
		String desc = request.getParameter("desc");
		int prix = Integer.valueOf(request.getParameter("prx"));
		boolean selc = true;
		String phot = request.getParameter("phot");
		int qtte = Integer.valueOf(request.getParameter("qtte"));
		Writer out = response.getWriter();
		out.write("<html><head><title>Result</title></head><body>");
//		out.write(accueilItf.ajouterProduit(desig, desc, prix, selc, phot, qtte));
		System.out.println("Ok");
		out.write("</body>");
		out.flush();
		out.close();
	}

}
