package miniROL;

import java.io.Serializable;

public class Personatge extends Entitat implements Serializable {
    private static final long serialVersionUID = 1L;

    // ... (No necesita cambios adicionales aquí) ...

    private int nivell, or, xp, xpNecessaria;
    private int agilitat;
    private String arma;



    public Personatge(String nom, int atac, int defensa, double vidaMax) {
        super(nom, atac, defensa, vidaMax);
        this.nivell = 1;
        this.or = 0;
        this.xp = 0;
        this.xpNecessaria = 10;
        this.agilitat = 5;
    }

    public Personatge() {
        super();

    }

    public int getNivell() {
        return nivell;
    }

    public void pujarNivell() {
        nivell++;
        setAtac(getAtac() + 2);
        setDefensa(getDefensa() + 1);
        setVidaMax(getVidaMax() * 1.1);
        getBarraVida().setMaximum((int) getVidaMax());
        setVidaActual((int) getVidaMax());
        establirVida((int) getVidaMax());
        xpNecessaria += (xpNecessaria + 5);
    }

    public int getXp() {
        return xp;
    }

    public void pujarXp(int quantitat) {
        xp += quantitat;
        if (xp >= xpNecessaria) pujarNivell();
    }

    public int getXpNecessaria() {
        return xpNecessaria;
    }

    public int getOr() {
        return or;
    }

    public void setOr(int or) {
        this.or = or;
    }

    public int getAgilitat() {
        return agilitat;
    }

    public void setAgilitat(int agilitat) {
        this.agilitat = agilitat;
    }
}