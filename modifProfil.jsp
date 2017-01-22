<%@ page pageEncoding="UTF-8" %>
<%@ page import="web.struct.Personne" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="utf-8">
	<meta content="IE=edge" http-equiv="X-UA-Compatible">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<title>Modification de Compte</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

	<%Personne p = (Personne)session.getAttribute("personne");%>

	<div class="container">
		<div class="page-header">
			<center>
				<h1 class="display-1">Meet'N'Roll</h1>
				
			</center>
	</div>
	<div class="row">
		<div class="col-xs-6 col-xs-offset-3">
			<form id="signupFrom" action="servlet/modifProfil" method="POST">
				<div class="form-group">
					<%out.println("<label class=\"control-label\" for=\"login\">"+p.getLogin()+"</label>");%>
				</div>
				<div class="form-group">
					<label class="control-label" for="password">Prenom</label>
					<%out.println("<input id=\"prenom\" class=\"form-control\" type=\"text\" value=\"" + p.getPrenom() + "\" name=\"prenom\">");%>
				</div>
				<div class="form-group">
					<label class="control-label" for="password">Nom</label>
					<%out.println("<input id=\"nom\" class=\"form-control\" type=\"text\" value=\"" + p.getNom() + "\" name=\"nom\">");%>
				</div>
				<div class="form-group">
					<label class="control-label" for="password">Date de Naissance</label>
					<%out.println("<input id=\"date\" class=\"form-control\" type=\"date\" value=\"" + p.getNaiss() + "\" name=\"naiss\">");%>
				</div>
				<div class="form-group">
					<label class="control-label" for="password">Mail</label>
					<%out.println("<input id=\"mail\" class=\"form-control\" type=\"text\" value=\"" + p.getMail() + "\" name=\"mail\">");%>
				</div>
				<button class="btn btn-success btn-block" type="submit">Enregistrer les modifications</button>
				<a class="btn btn-default btn-block" href="servlet/profil">Annuler</a>
			</form>
		</div>
			<p>
			(* : champs obligatoires)
			</p>
	</div>
</body>
</html>
