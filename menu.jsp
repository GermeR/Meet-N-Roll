<%@ page pageEncoding="UTF-8" %>
<%@ page import="web.struct.Personne" %>
<!DOCTYPE html>
<html lang="fr">
	<head>
	<meta charset="utf-8">
	<meta content="IE=edge" http-equiv="X-UA-Compatible">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<title>GTB.com - Menu</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
	<div class="page-header">
	<center>
	<%
		out.println("<h1 class=display-1> Bienvenu "+ ((Personne)session.getAttribute("personne")).getNom() +"</h1><br>");
	%>
	<li role='presentation' class='btn btn-default btn-lg'><a href='servlet/profil'>Consulter vos absences</a></li>
	</center>
</body>
</html>