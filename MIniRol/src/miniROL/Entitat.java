package miniROL;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Entitat implements IAtacable, Serializable {

    private static final long serialVersionUID = 1L;

    private String nom;
    private int vidaActual, atac, defensa;
    private double vidaMax;
    private boolean estaViu;

    // ⚠️ Marcamos como transient para no serializar la barra
    private transient JProgressBar barraVida;

    public Entitat(String nom, int atac, int defensa, double vidaMax) {
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.vidaMax = vidaMax;
        this.vidaActual = (int) vidaMax;
        this.estaViu = true;

        crearBarraVida();
        establirVida(vidaActual);
    }

    // Constructor vacío requerido para deserialización
    public Entitat() {
        this.nom = "Desconegut";
        this.atac = 1;
        this.defensa = 1;
        this.vidaMax = 10;
        this.vidaActual = 10;
        this.estaViu = true;
        crearBarraVida();
    }

    /** ⚙️ Crea la barra de vida (también usada después de cargar el objeto) */
    public void crearBarraVida() {
        barraVida = new JProgressBar(0, (int) vidaMax);
        barraVida.setPreferredSize(new Dimension(150, 25));
        barraVida.setForeground(Color.RED);
        barraVida.setStringPainted(true);
        establirVida(vidaActual);
    }

    public void establirVida(int vida) {
        if (barraVida == null) crearBarraVida(); // ✅ seguridad post-carga
        barraVida.setValue(vida);
        barraVida.setString(vida + "/" + (int) vidaMax);
    }

    @Override
    public String atacar(IAtacable enemic) {
        Random rnd = new Random();
        int tirada = rnd.nextInt(20) + 1;
        int daño = atac;
        String mensaje = nom + " tira d20: " + tirada + ". ";

        if (tirada == 1) {
            daño = 0;
            mensaje += "¡Fallo crítico!";
        } else if (tirada == 20) {
            daño *= 2;
            mensaje += "¡Golpe crítico!";
        }

        daño += tirada;

        int dañoFinal = daño - enemic.getDefensa();
        if (dañoFinal <= 0) dañoFinal = 1;

        enemic.rebreFerida(dañoFinal);
        mensaje += " Daño total: " + dañoFinal + ".";

        return mensaje;
    }

    @Override
    public void rebreFerida(int quantitat) {
        if (estaViu) {
            int quantitatTotal = quantitat - defensa;
            if (quantitatTotal <= 0) quantitatTotal = 1;
            vidaActual -= quantitatTotal;
            if (vidaActual <= 0) {
                vidaActual = 0;
                estaViu = false;
            }
            establirVida(vidaActual);
        }
    }

    // 🔹 Método especial llamado automáticamente después de leer el objeto
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();   // leer atributos básicos
        crearBarraVida();         // recrear la barra de vida
    }



    // Getters y setters
    public JProgressBar getBarraVida() { return barraVida; }
    public int getDefensa() { return defensa; }
    public boolean isEstaViu() { return estaViu; }
    public int getVidaActual() { return vidaActual; }
    public double getVidaMax() { return vidaMax; }
    public int getAtac() { return atac; }
    public String getNom() { return nom; }

    public void setAtac(int atac) { this.atac = atac; }
    public void setDefensa(int defensa) { this.defensa = defensa; }
    public void setEstaViu(boolean estaViu) { this.estaViu = estaViu; }
    public void setVidaActual(int vidaActual) { this.vidaActual = vidaActual; establirVida(vidaActual); }
    public void setVidaMax(double vidaMax) { this.vidaMax = vidaMax; crearBarraVida(); }
}
