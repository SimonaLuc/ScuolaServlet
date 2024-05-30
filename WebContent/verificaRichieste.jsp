<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo - Richieste</title>
    <style>
        
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .content {
            text-align: center;
        }
        .button-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="content">
        <h1>Verifica Stato Richiesta</h1>
        <form action="ScuolaServlet" method="post">
            <input type="hidden" name="tipo_richiesta" value="verifica_stato_query">
            <label for="queryID">Inserisci l'ID della richiesta:</label>
            <input type="text" name="queryID" required>
            <button type="submit">Verifica Stato</button>
        </form>
        
        <%
            String queryID = (String) request.getParameter("queryID");
            String stato = (String) request.getAttribute("stato");
            
            if (queryID != null && stato != null) {
        %>

            <h2>Stato della richiesta con ID <%= queryID %>: <%= stato %></h2>
        <% } %>
          
    </div>
</body>
</html>