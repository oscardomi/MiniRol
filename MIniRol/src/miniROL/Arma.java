package miniROL;

import javax.swing.*;

public class Arma {
    private int dau;          // Daño base (como el dado que lanza)
    private String descripcio;
    private int raca;         // Raza a la que pertenece el arma
    private JLabel imatge;    // Imagen del arma (opcional, si lo usas en UI)

    public Arma(int dau, String descripcio, int raca, JLabel imatge, String[] armes) {
        this.dau = dau;
        this.descripcio = descripcio;
        this.raca = raca;
        this.imatge = imatge;
    }

    public Arma(int dau, String descripcio, int raca, JLabel imatge) {
        this.dau = dau;
        this.descripcio = descripcio;
        this.raca = raca;
        this.imatge = imatge;
    }

    // Getters y setters
    public int getDau() { return dau; }
    public void setDau(int dau) { this.dau = dau; }

    public String getDescripcio() { return descripcio; }
    public void setDescripcio(String descripcio) { this.descripcio = descripcio; }

    public int getRaca() { return raca; }
    public void setRaca(int raca) { this.raca = raca; }

    public JLabel getImatge() { return imatge; }
    public void setImatge(JLabel imatge) { this.imatge = imatge; }

    @Override
    public String toString() {
        return descripcio + " (dau: d" + dau + ")";
    }
}
