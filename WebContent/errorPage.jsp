<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Scuola Servlet Demo UTF-8 Error</title>
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
        <h1>Errore di connessione al database</h1>
        <p>La tua richiesta è stata presa in carico e verrà  processata nelle prossime ore.</p>
        <p>Potrai controllare lo stato della tua richiesta <a href="verificaRichieste.jsp?queryID=<%= request.getAttribute("queryID") %>">cliccando qui</a> e inserendo il seguente ID: <strong><%= request.getAttribute("queryID") %></strong>.</p>
    </div>
</body>
</html>