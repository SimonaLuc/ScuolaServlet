<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="servlet.Allievo" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo - Visualizza Allievi Classe</title>
    <style>
        body {
            text-align: center;
        }
        h1 {
            margin-top: 20px;
        }
        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
        }
    </style>
</head>
<body>
    <h1>Elenco Allievi</h1>
    <table>
        <thead>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <% ArrayList<Allievo> allievi = (ArrayList<Allievo>) request.getAttribute("allievi");
                if (allievi != null && !allievi.isEmpty()) {
                    for (Allievo allievo : allievi) {
            %>
                <tr>
                    <td><%= allievo.getNome() %></td>
                    <td><%= allievo.getCognome() %></td>
                    <td><%= allievo.getEmail() %></td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="3">Nessun allievo trovato.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <div id="Indietro" text-align: center;>
        <form action="loginOk.jsp" method="post">
            <!-- Assicurati di passare il profilo utente come parametro -->
            <input type="hidden" name="profilo" value="<%= session.getAttribute("profilo") %>">
            <input type="submit" value="Torna Indietro">
        </form>
    </div>

    <div id="logout" style="position: fixed; bottom: 0; left: 0; width: 100%; background-color: #f2f2f2; padding: 15px; text-align: center;">
        <form action="ScuolaServlet" method="post">
            <input type="hidden" name="tipo_richiesta" value="logout">
            <input type="submit" value="Logout">
        </form>
    </div>
</body>
</html>