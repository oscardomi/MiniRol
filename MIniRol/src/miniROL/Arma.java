package miniROL;

import javax.swing.*;
import java.io.Serializable; // Importar Serializable

// Implementar Serializable
public class Arma implements Serializable {
    private static final long serialVersionUID = 3L; // Añadir serialVersionUID

    private int dau;
    private String descripcio;
    private int raca;

    // Marcar como transient
    private transient JLabel imatge;
    private String[] armes;

    public Arma(int dau, String descripcio, int raca, JLabel imatge, String [] armes) {
        this.dau = dau;
        this.descripcio = descripcio;
        this.raca = raca;
        this.imatge = imatge;
        this.armes = armes;
    }

    // ... (El resto de getters y setters, no necesitan más cambios) ...
    public int getDau() {
        return dau;
    }

    public void setDau(int dau) {
        this.dau = dau;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getRaca() {
        return raca;
    }

    public void setRaca(int raca) {
        this.raca = raca;
    }

    public JLabel getImatge() {
        return imatge;
    }

    public void setImatge(JLabel imatge) {
        this.imatge = imatge;
    }
}