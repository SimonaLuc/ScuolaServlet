package servlet;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;

public class Allievo extends Persona implements SQLData, Serializable {
	
	private static final long serialVersionUID = -4235126659654360181L;
	private String sql_type;
	
	//Variabili d'istanza: (le abbiamo tolte, perché non sono più attributi dell'allievo)
	// private String classe;
	// private String sezione;
	//private ArrayList<ProvaAllievo> andamento;

	// Costruttore: 
	public Allievo(String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String email) {
		super(nome, cognome, dataDiNascita, luogoDiNascita, email);
		// this.classe = classe;
		// this.sezione = sezione;
		//this.andamento = new ArrayList<>();
	}

	//Aggiungo costruttore:
	public Allievo (String nome, String cognome, String email) {
		super(nome, cognome, email);
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}
	
	@Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;
        
        nome = stream.readString();
        cognome = stream.readString();
        //dataDiNascita = stream.readDate().toLocalDate();
        cf = stream.readString();
       
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
    	/*
        stream.writeString(name);
        stream.writeString(address);
        stream.writeString(phone);
        */
    }
}
	
	
	

	
	
	

	// public String getClasse() {
	// 	return classe;
	// }
	
	// public void setClasse(String classe) {
	// 	this.classe = classe;
	// }
	
	// public String getSezione() {
	// 	return sezione;
	// }
	
	// public void setSezione(String sezione) {
	// 	this.sezione = sezione;
	// }
	
	
