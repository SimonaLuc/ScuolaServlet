<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="servlet.Allievo" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo - Visualizza Classi</title>
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
    <h1>Seleziona Classe</h1>

    <form action="ScuolaServlet" method="post">
        <input type="hidden" name="tipo_richiesta" value="visualizza_allievi">
        
        <label for="classe">Classe:</label>
        <select name="classe" id="classe">
            <option value="I">I</option>
            <option value="II">II</option>
        </select>
        
        <label for="sezione">Sezione:</label>
        <select name="sezione" id="sezione">
            <option value="A">A</option>
            <option value="B">B</option>
        </select><br><br>
        
        <label for="anno_scolastico">Anno Scolastico:</label>
        <select name="anno_scolastico" id="anno_scolastico">
            <% ArrayList<String> anniScolastici = (ArrayList<String>) request.getAttribute("anniScolastici");
                
                if (anniScolastici != null && !anniScolastici.isEmpty()) {    
                    for (String anno : anniScolastici) { 
            %>
                <option value="<%= anno %>"><%= anno %></option>
            <% 
                }
            } else {
            %>
                <option value="">Anno scolastico non disponibile</option>
            <%
                }
            %>        
        </select><br><br>
        
        <input type="submit" value="Visualizza Allievi"><br><br>
    </form>

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
    
