<%@ page import="java.util.ArrayList" %>
<%@ page import="servlet.Iscrizione" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo - Elenco Iscritti</title>
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
    <h1>Elenco Iscritti per Anno Scolastico</h1>

    <%
        String annoScolastico = (String) request.getAttribute("anno_scolastico");
        ArrayList<Iscrizione> iscrizioni = (ArrayList<Iscrizione>) request.getAttribute("iscrizioni");
    %>

    <h2>Anno Scolastico: <%= annoScolastico %></h2>

    <table>
        <tr>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
            <th>Classe</th>
            <th>Sezione</th>
        </tr>
        <%
            if (iscrizioni != null && !iscrizioni.isEmpty()) {
                for (Iscrizione iscrizione : iscrizioni) {
        %>
        <tr>
            <td><%= iscrizione.getNome() %></td>
            <td><%= iscrizione.getCognome() %></td>
            <td><%= iscrizione.getEmail() %></td>
            <td><%= iscrizione.getLivello() %></td>
            <td><%= iscrizione.getSezione() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">Nessun allievo trovato per l'anno scolastico selezionato.</td>
        </tr>
        <%
            }
        %>
    </table>

    <div id="Indietro" style="text-align: center;">
        <form action="visualizza_iscritti.jsp" method="post">
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