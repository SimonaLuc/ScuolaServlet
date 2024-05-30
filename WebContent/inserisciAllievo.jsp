<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Scuola Servlet Demo - Inserisci allievo</title>

    <style>
        /* Centrare il contenuto della pagina */
        body {
            display: flex;
            flex-direction: column;
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

        .ok-message {
            color: green;
        }
        .error-message {
            color: red;
        }
    </style>

</head>
<body>
    <div class="content">
        <h1>Servlet della Scuola</h1>
        <h2>Inserisci un nuovo allievo</h2><br>

        <% 
            String errore = (String) request.getAttribute("errore");
            if (errore != null && !errore.isEmpty()) { 
        %>
            <div class="error-message">
                <%= errore %>
            </div>
        <% 
            } 
        %>

        <% 
            String conferma = (String) request.getAttribute("conferma");
            if (conferma != null && !conferma.isEmpty()) { 
        %>
            <div class="ok-message">
                <%= conferma %>
            </div>
        <% 
            } 
        %>


        <form action="ScuolaServlet" method="post" onsubmit="mostraMessaggioConferma()">
            <input type="hidden" name="tipo_richiesta" value="inserisci_allievo">

            <div style="display: inline-block; text-align: left;">
                <label for="NomeAllievo">Nome:</label>
                <input type="text" id="NomeAllievo" name="NomeAllievo" required minlength="2"><br><br>
                <label for="CognomeAllievo">Cognome:</label>
                <input type="text" id="CognomeAllievo" name="CognomeAllievo" required minlength="2"><br><br>
                <label for="Data_di_nascitaAllievo">Data di nascita:</label>
                <input type="date" id="Data_di_nascitaAllievo" name="Data_di_nascitaAllievo" required><br><br>
                <label for="Luogo_di_nascitaAllievo">Luogo di nascita:</label>
                <input type="text" id="Luogo_di_nascitaAllievo" name="Luogo_di_nascitaAllievo" required><br><br>
                <label for="CF_allievo">Codice fiscale:</label>
                <input type="text" id="CF_allievo" name="CF_allievo" required min="16" maxlength="16" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"><br><br>
                <label for="email_allievo">email:</label>
                <input type="email" id="email_allievo" name="email_allievo" required><br><br><br>
            </div>

            <div class="button-container">
                <input type="submit" value="Inserisci allievo"><br><br>
            </div>
        </form>

        <div class="button-container">
            <form action="loginOk.jsp" method="post">
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
        
    </div>

    <script>
        function mostraMessaggioConferma() {
            var messaggioConferma = document.getElementById("messaggioConferma");
            messaggioConferma.style.display = "block";
        }
    </script>

</body>
</html>