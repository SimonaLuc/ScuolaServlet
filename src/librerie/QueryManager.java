package librerie;

import java.io.*;

public class QueryManager {
    private static int lastID = 0;
    private static String queriesInCoda = "queries.txt";
    private static String registroID = "lastQueryID.txt";

    static {
        loadLastID();
    }

    public static void saveQueryToFile(String query) {
        // forse si può mettere synchronized per il problema degli accessi multipli: se capisco bene serve apposta per i multi-thread
        //String absoluteDiskPath = request.getServletContext().getRealPath("./");
        //String filePath = "C:\\xampp\\tomcat\\webapps\\ScuolaServletApp\\FileDaGestire\\queries.txt";
    		
        		      		
    	int id = generateID();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(queriesInCoda, true))) {
            bw.write("ID: " + id + ", Query: " + query + ", Stato: in attesa");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveLastID();
    }

    public static int generateID() {
    // // forse anche qui si può mettere synchronized 
        return ++lastID;
    }

    public static void loadLastID() {
        try (BufferedReader br = new BufferedReader(new FileReader(registroID))) {
            String line;
            if ((line = br.readLine()) != null) {
                lastID = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLastID() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(registroID))) {
            bw.write(String.valueOf(lastID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateQueryStatus(int queryID, String newStatus) {
        try {
            File inputFile = new File(queriesInCoda);
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.startsWith("ID: " + queryID)) {
                    currentLine = currentLine.replaceFirst("Stato: [^,]+", "Stato: " + newStatus);
                }
                bw.write(currentLine + System.lineSeparator());
            }
            bw.close();
            br.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getQueryStatus(int queryID) {
        try (BufferedReader br = new BufferedReader(new FileReader(queriesInCoda))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.startsWith("ID: " + queryID)) {
                    return currentLine.split("Stato: ") [1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ID non trovato";
    }

    public void executePendingQueries(){
        try {
            File inputFile = new File(queriesInCoda);
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                int queryID = Integer.parseInt(currentLine.split(", ")[0].split(": ")[1]);
                String query = currentLine.split(", Query: ")[1].split(", Stato: ")[0];
                String stato = currentLine.split(", Stato: ")[1];

                if (stato.equals("in attesa")) {
                    // Qui dovrò dirgli di provare a fare la query
                    boolean eseguitaConSuccesso = true; 
                    if (eseguitaConSuccesso) {
                        updateQueryStatus(queryID, "eseguita");
                    } else {
                        updateQueryStatus(queryID, "rifiutata");
                    }
                }

                bw.write(currentLine + System.lineSeparator());
            }
            bw.close();
            br.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
}