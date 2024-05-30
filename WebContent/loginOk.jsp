<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo</title>
    <style>
        /* Stile per centrare il contenuto della pagina */
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
        <h1>Login successful</h1>
        <h3>Cosa vuoi fare?</h3>
        <div class="button-container">
            <%
                // Controllo quale sia il profilo
                String profilo = (String) session.getAttribute("profilo");

                if (profilo.equals("amministrativo")) {
            %>
                    <div>
                        <a href="inserisciAllievo.jsp"><button>Inserisci un nuovo allievo</button></a><br><br>
                    </div>
                    <div>
                        <form action="ScuolaServlet" method="post">
                            <input type="hidden" name="tipo_richiesta" value="visualizza_classi">
                            <button type="submit">Visualizza classi</button><br><br>
                        </form>
                    </div>
                    <div>
                        <form action="ScuolaServlet" method="post">
                            <input type="hidden" name="tipo_richiesta" value="visualizza_iscritti">
                            <button type="submit">Visualizza iscritti</button><br><br>
                        </form>
                    </div>
                    <div>
                        <a href="index.jsp"><button>Logout</button></a><br><br>
                    </div>
            <%
                } else if (profilo.equals("insegnante")) {
            %>
                    <div>
                        <a href="inserisciEsitoProve.html"><button>Inserisci l'esito di una prova</button></a><br><br>
                    </div>
                    <div>
                        <form action="ScuolaServlet" method="post">
                            <input type="hidden" name="tipo_richiesta" value="visualizza_classi">
                            <button type="submit">Visualizza classi</button><br><br>
                        </form>
                    </div>
                    <div>
                        <a href="index.jsp"><button>Logout</button></a><br><br>
                    </div>
            <%
                } else if (profilo.equals("allievo")) {
            %>
                    <div>
                        <a href="visualizzaMediaVoti.html"><button>Visualizza media voti</button></a><br><br>
                    </div>
                    <div id="logout">
                        <form action="ScuolaServlet" method="post">
                            <input type="hidden" name="tipo_richiesta" value="logout">
                            <input type="submit" value="Logout">
                        </form>
                    </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>