package servlet;

public class Iscrizione {
    private String nome;
    private String cognome;
    private String email;
    private String livello;
    private String sezione;

    public Iscrizione(String nome, String cognome, String email, String livello, String sezione) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.livello = livello;
        this.sezione = sezione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

}