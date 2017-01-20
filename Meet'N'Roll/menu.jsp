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
	<h1 class="display-1">MENU PRINCIPAL</h1><br>
	<% if(((Personne)session.getAttribute("personne")).getRole().equals("etu"))
	out.println("<li role='presentation' class='btn btn-default btn-lg'><a href='servlet/Select'>Consulter vos absences</a></li>");
	%>
	<% if(((Personne)session.getAttribute("personne")).getRole().equals("prof") || ((Personne)session.getAttribute("personne")).getRole().equals("secr"))
	out.println("<li role='presentation' class='btn btn-default btn-lg'><a href='servlet/SelectAll'>Consulter toutes les absences</a></li>");
	%>
	<% if(((Personne)session.getAttribute("personne")).getRole().equals("prof"))
	out.println("<li role='presentation' class='btn btn-default btn-lg'><a href='servlet/abs'>Ajouter des absences</a></li>");
	%>
	<% if(((Personne)session.getAttribute("personne")).getRole().equals("secr"))
	out.println("<li role='presentation' class='btn btn-default btn-lg'><a href='servlet/justif'>Justifier des absences</a></li>");
	%>
	</center>
</body>
</html>

