<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Ajout de produit </title>
</head>
<body>
	<img alt="icone" src="icon.gif">
	<h1>Nouveau Produit</h1>
	<a href="accueil.jsp">Accueil</a>
	<form action="AjoutProduit" method="get">
		<table>
			<tr>
				<td>Désignation : </td>
				<td><input type="text" name="desig" width="1"></td>
			</tr>
			<tr>
				<td>Description : </td>
				<td><input type="text" name="desc" width="1"></td>
			</tr>
			<tr>
				<td>Prix : </td>
				<td><input type="text" name="prx" width="1"></td>
			</tr>
			<tr>
				<td>Selected : </td>
				<td><input type="text" name="selc" width="1"></td>
			</tr>
			<tr>
				<td>Photo : </td>
				<td><input type="text" name="phot" width="1"></td>
			</tr>
			<tr>
				<td>Quantité : </td>
				<td><input type="text" name="qtte" width="1"></td>
			</tr>
			<tr>
				<td align="right" colspan="3">
					<button name="submit" type="submit">Ajouter</button>
				</td>
				
			</tr>
		</table>
	</form>
</body>
</html>
