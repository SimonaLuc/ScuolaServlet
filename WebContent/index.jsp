<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <title>Scuola Servlet Demo - Login</title>

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
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
        }
    </style>

    <!-- Script javascript che, se la login fallisce, fa comparire un messaggio:
    <script>
        function showErrorMessage() {
            var errorMessage = document.getElementById("errorMessage");
            errorMessage.style.display = "block";
        }
    </script> -->

    <!-- Script JS per far comparire la data corrente. Per richiamarlo su altre pagine, basterà
    aggiungere un elemento con ID "currentDate" -->
    <script>
        function updateCurrentDate() {
            var currentDateElement = document.getElementById("currentDate");
            var currentDate = new Date().toLocaleDateString();
            currentDateElement.textContent = "Data corrente: " + currentDate;
        }
        // Aggiorna la data quando la pagina viene caricata
        window.addEventListener('load', updateCurrentDate);
    </script>

</head>
<body>
    <div style="text-align: center;">

        <h1>Benvenuti a scuola!</h1>
        <h2 id="currentDate">Data corrente:</h2>
        <h2>Login</h2>

        <% String errorMessage = (String) request.getAttribute("error_message");
        if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="error-message">
                <%= errorMessage %>
            </div>
        <% } %>
        
        <form action="ScuolaServlet" method="post">
            <input type="hidden" name="tipo_richiesta" value="login"><br>
            <label for="username">Username:</label>
            <input type="text" id="username" name="user" placeholder="Inserisci username" required minlength="2"><br><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="pwd" placeholder="Inserisci password" required ><br><br>
            <input type="submit" value="Invia">
        </form>
    </div>
    
</body>
</html>