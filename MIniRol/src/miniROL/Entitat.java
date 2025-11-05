package miniROL;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable; // Importar Serializable
import java.util.Random;

// Implementar Serializable
public class Entitat implements IAtacable, Serializable {
    private static final long serialVersionUID = 2L; // Añadir serialVersionUID

    private String nom;
    private int vidaActual, atac, defensa;
    private double vidaMax;
    private boolean estaViu;

    // Marcar como transient, se recreará al cargar
    private transient JProgressBar barraVida;

    public Entitat(String nom, int atac, int defensa, double vidaMax) {
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.vidaMax = vidaMax;
        vidaActual = (int) vidaMax;

        estaViu = true;

        // Inicializar barraVida en el constructor
        inicialitzarBarraVida();
        establirVida(vidaActual);
    }

    public Entitat() {
        // Constructor vacío para deserialización, si es necesario,
        // pero se inicializan los campos transient después.
    }

    // Nuevo método para inicializar la JProgressBar
    public void inicialitzarBarraVida() {
        barraVida = new JProgressBar(0,(int) vidaMax);
        barraVida.setPreferredSize(new Dimension(150,25));
        establirVida(vidaActual);
    }

    public void establirVida(int vida) {
        // Asegurarse de que barraVida no es null, es importante después de la deserialización
        if (barraVida == null) {
            inicialitzarBarraVida();
        }

        vidaActual = vida; // Asignar el valor de vida
        barraVida.setMaximum((int) vidaMax); // Asegurar que el máximo es correcto
        barraVida.setValue(vidaActual);
        barraVida.setForeground( Color.RED);
        barraVida.setStringPainted(true);
        barraVida.setString(vidaActual + "/" + (int) vidaMax);

    }

    @Override
    public String atacar(IAtacable enemic) {
        Random rnd = new Random();
        int tirada = rnd.nextInt(20) + 1; // d20
        int daño = atac; // ataque base
        String mensaje = getNom() + " tira d20: " + tirada + ". ";

        if (tirada == 1) {
            daño = 0;
            mensaje += "¡Fallo crítico!";
        } else if (tirada == 20) {
            daño *= 2;
            mensaje += "¡Golpe crítico!";
        }

        daño += tirada;

        // Aquí restamos la defensa del objetivo
        int dañoFinal = daño - enemic.getDefensa();
        if (dañoFinal <= 0) dañoFinal = 1;

        enemic.rebreFerida(dañoFinal); // aplica daño al enemigo
        mensaje += " Daño total: " + dañoFinal + ".";

        return mensaje;
    }


    @Override
    public void rebreFerida(int quantitat) {

        if (estaViu) {
            int quantitatTotal = quantitat - defensa;
            if (quantitatTotal <=0) quantitatTotal = 1;
            vidaActual-=quantitatTotal;
            if (vidaActual<=0) {
                estaViu = false;
                vidaActual = 0;
            }

            // Actualizar la barra de vida
            establirVida(vidaActual);
        }
    }

    // ... (El resto de getters y setters, no necesitan cambios) ...
    public JProgressBar getBarraVida() {
        // Asegurarse de que barraVida no es null antes de devolverla
        if (barraVida == null) {
            inicialitzarBarraVida();
        }
        return barraVida;
    }

    public int getDefensa() {
        return defensa;
    }

    public boolean isEstaViu() {
        return estaViu;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public double getVidaMax() {
        return vidaMax;
    }

    public int getAtac() {
        return atac;
    }

    public void setAtac(int atac) {
        this.atac = atac;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setEstaViu(boolean estaViu) {
        this.estaViu = estaViu;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public String getNom() {
        return nom;
    }

    public void setVidaMax(double vidaMax) {
        this.vidaMax = vidaMax;
    }
}