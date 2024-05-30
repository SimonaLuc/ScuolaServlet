package servlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;


import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import librerie.ManageDb;
import librerie.QueryManager;
import servlet.Iscrizione;

public class ScuolaServlet extends HttpServlet {
	private QueryManager qm;

    public void init() {
        try {
			super.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Servlet is being initialized");
        qm = new QueryManager();
        QueryManager.loadLastID();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doPost called");
        //ManageDb mioDB = new ManageDb();
        String formArrivata = request.getParameter("tipo_richiesta");
        ResultSet res = null;
        HttpSession session = request.getSession();

        try{
        
                if (formArrivata.equals("login")) {
                    ManageDb mioDB = new ManageDb();
                    try {
                        boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                        if (connectionOk) {
                            System.out.println("Database connection successful");
                            String username = request.getParameter("user");
                            String password = request.getParameter("pwd");
                            res = mioDB.ReadInDB("SELECT profilo FROM utenti WHERE username = '" + username + "' AND password = '" + password + "' AND status=1");             
                
                            if (res != null && res.next()) {
                                // Login OK, controlla profilo
                                System.out.println("Login successful");
                                String profilo = res.getString("profilo");
                                session.setAttribute("login", username);
                                session.setAttribute("profilo", profilo);
                                response.sendRedirect("loginOk.jsp");
                            } else {
                                // Login fallita
                                System.out.println("Login failed");
                                request.setAttribute("error_message", "Login failed! Username o password errati");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                                return;
                            }
                        } else {
                            System.out.println("Connessione al Database fallita. Riprova più tardi");
                            request.setAttribute("error_message", "Connessione al Database fallita. Riprova più tardi");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                
                    } finally {
                        mioDB.Disconnect();
                    }

                } else if (formArrivata.equals("inserisci_allievo")) {
                    session = request.getSession();
                    QueryManager qm = new QueryManager();
                    
                    if (session.getAttribute("login") != null) {
                        String sParamNomeAllievo = request.getParameter("NomeAllievo");
                        String sParamCognomeAllievo = request.getParameter("CognomeAllievo");
                        String paramDataAllievo = request.getParameter("Data_di_nascitaAllievo");
                        String sParamLuogoAllievo = request.getParameter("Luogo_di_nascitaAllievo");
                        String sParamCFAllievo = request.getParameter("CF_allievo");
                        String sParamEmailAllievo = request.getParameter("email_allievo");
                
                        if (paramDataAllievo == null || paramDataAllievo.isEmpty() ||
                                sParamNomeAllievo == null || sParamNomeAllievo.isEmpty() || 
                                sParamCognomeAllievo == null || sParamCognomeAllievo.isEmpty() || 
                                sParamLuogoAllievo == null || sParamLuogoAllievo.isEmpty() ||
                                sParamCFAllievo == null || sParamCFAllievo.isEmpty() || 
                                sParamEmailAllievo == null || sParamEmailAllievo.isEmpty()) {
                            session.setAttribute("errore", "Uno dei parametri dell'allievo non è corretto. Controlla l'inserimento.");
                            response.sendRedirect("inserisciAllievo.jsp");
                        } else { 
                            ManageDb mioDB = new ManageDb();  
                            boolean connectionOk = false;                
                            try {
                                connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                                if (connectionOk) {
                                    ResultSet rs = mioDB.ReadInDB("SELECT * FROM allievi WHERE CF_allievo = '" + sParamCFAllievo + "'");
                                    if (rs.next()) {
                                        request.setAttribute("errore", "Esiste già un allievo con questo codice fiscale.");
                                        request.getRequestDispatcher("inserisciAllievo.jsp").forward(request, response);
                                    } else {
                                        LocalDate dataAllievo = LocalDate.parse(paramDataAllievo, DateTimeFormatter.ISO_DATE);
                                        String query = ("INSERT INTO allievi (nome, cognome, data_di_nascita, luogo_di_nascita, CF_allievo, email_allievo) VALUES ('"
                                        + sParamNomeAllievo.trim().toUpperCase() + "', '" + sParamCognomeAllievo.trim().toUpperCase() + "', '" + dataAllievo + "', '" + sParamLuogoAllievo.trim().toUpperCase() + "', '" + sParamCFAllievo
                                        + "', '" + sParamEmailAllievo + "')");
                                        mioDB.WriteInDB(query);
                                        // mioDB.WriteInDB("INSERT INTO allievi (nome, cognome, data_di_nascita, luogo_di_nascita, CF_allievo, email_allievo) VALUES ('"
                                        // + sParamNomeAllievo.trim().toUpperCase() + "', '" + sParamCognomeAllievo.trim().toUpperCase() + "', '" + dataAllievo + "', '" + sParamLuogoAllievo.trim().toUpperCase() + "', '" + sParamCFAllievo
                                        // + "', '" + sParamEmailAllievo + "')");
                                        request.setAttribute("conferma", "Inserimento dell'allievo avvenuto con successo.");
                                    } 
                                } else {
                                    // Prendi la query e scrivila in un file
                                    String query = ("INSERT INTO allievi (nome, cognome, data_di_nascita, luogo_di_nascita, CF_allievo, email_allievo) VALUES ('"
                                        + sParamNomeAllievo.trim().toUpperCase() + "', '" + sParamCognomeAllievo.trim().toUpperCase() + "', '" + paramDataAllievo + "', '" + sParamLuogoAllievo.trim().toUpperCase() + "', '" + sParamCFAllievo
                                        + "', '" + sParamEmailAllievo + "')");
                                    int id = qm.generateID();
                                    QueryManager.saveQueryToFile(query);
                                    request.setAttribute("info_message", "Connessione al database fallita. La tua richiesta è stata presa in carico e verrà gestita più tardi.");
                                    request.setAttribute("queryID", id);
                                    request.getRequestDispatcher("errorPage.jsp").forward(request, response);
                                }
                                     
                            } catch (DateTimeParseException e) {
                                e.printStackTrace();
                                request.setAttribute("errore", "Errore. Controllare i dati inseriti");
                            } finally {
                                // Chiudi la connessione al database
                                if (connectionOk) {
                                    mioDB.Disconnect();
                                    System.out.println("Database connection closed");
                                }
                                request.getRequestDispatcher("inserisciAllievo.jsp").forward(request, response);
                            }                    
                        }
                    } else {
                        System.out.println("Utente non loggato");
                        request.setAttribute("errore", "Utente non loggato.");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }

                } else if (formArrivata.equals("visualizza_classi")) {
                    ManageDb mioDB = new ManageDb();
                    session = request.getSession();
                    try {
                        boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                        if (connectionOk) {
                            System.out.println("Invio request a visualizza_classi.jsp");//Debug
                            ArrayList<String> anniScolastici = getAnniScolasticiFromDB();
                            request.setAttribute("anniScolastici", anniScolastici);
                            System.out.println("Anni scolastici recuperati dal DB: " + anniScolastici); 
                            request.getRequestDispatcher("visualizza_classi.jsp").forward(request, response);
                        } else {
                            System.out.println("Connessione al database fallita. Riprova più tardi");
                        }
                    
                    } finally {
                        mioDB.Disconnect();
                    }

                } else if (formArrivata.equals("visualizza_allievi")) {
                    String classe = request.getParameter("classe");
                    String sezione = request.getParameter("sezione");
                    String annoScolastico = request.getParameter("anno_scolastico");
                    

                    ManageDb mioDB = new ManageDb();
                    try {
                        boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                        if (connectionOk) {                    
                            ArrayList<Allievo> allievi = getAllieviFromDB(classe, sezione, annoScolastico);

                            request.setAttribute("allievi", allievi);
                            request.setAttribute("classe", classe);
                            request.setAttribute("sezione", sezione);
                            request.setAttribute("annoScolastico", annoScolastico);

                            ArrayList<String> anniScolastici = getAnniScolasticiFromDB();
                            request.setAttribute("anniScolastici", anniScolastici);

                            request.getRequestDispatcher("visualizza_allievi_classe.jsp").forward(request, response);
                        } else {
                            System.out.println("Connesione al Databse fallita. Riprova più tardi");        
                        }
                    
                    } finally {
                        mioDB.Disconnect();
                    }
                } else if (formArrivata.equals("visualizza_iscritti")) {
                    session = request.getSession();
                    ManageDb mioDB = new ManageDb();
                    try{
                        boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                        if (connectionOk) {
                            System.out.println("Invio request a visualizza_iscritti.jsp");//Debug
                            ArrayList<String> anniScolastici = getAnniScolasticiFromDB();
                            request.setAttribute("anniScolastici", anniScolastici);
                            System.out.println("Anni scolastici recuperati dal DB: " + anniScolastici); 
                            request.getRequestDispatcher("visualizza_iscritti.jsp").forward(request, response);
                        } else {
                            System.out.println("Connessione al Database fallita. Riprova più tardi");
                        }
                    
                    } finally {
                        mioDB.Disconnect();
                    }

                } else if (formArrivata.equals("visualizza_elenco_iscritti"))  {
                    String annoScolastico = request.getParameter("anno_scolastico");
                    ManageDb mioDB = new ManageDb();
                    try {
                        boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
                        if (connectionOk) {
                    
                            ArrayList<Iscrizione> iscrizioni = getIscrizioniFromDB(annoScolastico);
                            request.setAttribute("iscrizioni", iscrizioni);
                            request.setAttribute("anno_scolastico", annoScolastico);
                            request.getRequestDispatcher("visualizza_elenco_iscritti.jsp").forward(request, response);
                        } else {
                            System.out.println("Connessione al Database fallita, riprova più tardi");
                        }
                    
                    } finally {
                        mioDB.Disconnect();
                    }

                } else if (formArrivata.equals("verifica_stato_query")) {
                    int queryID = Integer.parseInt(request.getParameter("queryID"));
                    String stato = QueryManager.getQueryStatus(queryID);
                    request.setAttribute("queryID", queryID);
                    request.setAttribute("stato", stato);
                    request.getRequestDispatcher("verificaRichiesta.jsp").forward(request, response);

                } else if (formArrivata.equals("logout")) {
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                } else {
                    System.out.println("Tipo di richiesta ignota " + formArrivata);
                }

        //     } else {  
        //         // Connessione al database fallita
        //         System.out.println("Database connection failed");
        //     }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void destroy() {
        System.out.println("Servlet is being destroyed");
        QueryManager.saveLastID();
    }
     

    private ArrayList<String> getAnniScolasticiFromDB() throws SQLException {
        ManageDb mioDB = new ManageDb();
        ArrayList<String> anniScolastici = new ArrayList<>();
        
        try {
            boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
            
            if (connectionOk) {
                ResultSet res = mioDB.ReadInDB("SELECT DISTINCT Anno_scolastico FROM allievo_classe ORDER BY Anno_scolastico DESC");
                try {
                
                    while (res != null && res.next()) {
                        String annoScolastico = res.getString("Anno_scolastico");
                        anniScolastici.add(annoScolastico);
                    }
                } finally {
                    try {
                        if (res != null) {
                            res.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        
        } finally {
            mioDB.Disconnect();
        }
        
        return anniScolastici;
    }

    private ArrayList<Allievo> getAllieviFromDB(String classe, String sezione, String annoScolastico) {
        ManageDb mioDB = new ManageDb();
        ArrayList<Allievo> allievi = new ArrayList<>();
        
        try {
            boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
            
            if (connectionOk) {
                String query = "SELECT a.nome, a.cognome, a.email_allievo " +
                               "FROM allievi a " +
                               "JOIN allievo_classe ac ON a.CF_allievo = ac.CF_allievo " +
                               "WHERE ac.Livello = '" + classe + "' " +
                               "AND ac.Sezione = '" + sezione + "' " +
                               "AND ac.Anno_scolastico = '" + annoScolastico + "'";
                
                ResultSet res = mioDB.ReadInDB(query);

                try {

                    while (res != null && res.next()) {
                        String nome = res.getString("nome");
                        String cognome = res.getString("cognome");
                        String email = res.getString("email_allievo");
                        Allievo allievo = new Allievo(nome, cognome, email);
                        allievi.add(allievo);
                    }
                
                } finally {
                    try {
                        if (res != null) {
                        res.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mioDB.Disconnect();
        }
        
        return allievi;
    }

    private ArrayList<Iscrizione> getIscrizioniFromDB(String annoScolastico) {
        ManageDb mioDB = new ManageDb();
        ArrayList<Iscrizione> iscrizioni = new ArrayList<>();
        
        try {
            boolean connectionOk = mioDB.Connect("localhost", 3306, "scuola", "server_scuola", "server_scuola123");
            
            if (connectionOk) {
                String query = "SELECT a.Nome, a.Cognome, a.email_allievo, ac.Livello, ac.Sezione "
                    + "FROM allievi a "
                    + "JOIN allievo_classe ac ON a.CF_allievo = ac.CF_allievo "
                    + "WHERE ac.Anno_scolastico = '" + annoScolastico + "'";
                
                ResultSet res = mioDB.ReadInDB(query);

                
                while (res != null && res.next()) {
                    String nome = res.getString("nome");
                    String cognome = res.getString("cognome");
                    String email = res.getString("email_allievo");
                    String livello = res.getString("Livello");
                    String sezione = res.getString("Sezione");

                    Iscrizione iscrizione = new Iscrizione(nome, cognome, email, livello, sezione);
                    
                    iscrizioni.add(iscrizione);
                }
            
                res.close();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            mioDB.Disconnect();
        }
        
        return iscrizioni;
    }

    
}
