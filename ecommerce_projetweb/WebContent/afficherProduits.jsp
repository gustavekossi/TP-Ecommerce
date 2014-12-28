 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--     <%@ page import="java.util.ArrayList,com.persistance.Articles"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des produits</title>
</head>
<body>
	LES PRODUITS
	<table>
	<thead>
	<tr>
	<td>NOM</td>
	<td> </td>
	<td>PRIX</td>
	<td> </td>
	<td><a href="afficherPanier.jsp">voir le panier</a></td>
	</tr>
	</thead>
	<c:forEach items="${listeArticle}" var="article">
			<tr>
			<td>nom: </td>
			<td><c:out value="${article.nom}"/> </td> 
			<td>prix: </td>
			<td><c:out value="${article.prix}"/> </td>
			<td><a href="/projetEcommerce/ajoutPanier?id=${article.id}" >ajouter au panier</a></td>
			</tr>
	</c:forEach>	
	</table>
	
	
</body>
</html>