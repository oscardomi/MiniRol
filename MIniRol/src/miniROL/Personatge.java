package miniROL;

import java.io.Serializable;
import java.util.ArrayList;

public class Personatge extends Entitat implements Serializable {
    private static final long serialVersionUID = 1L;

    private int nivell, or, xp, xpNecessaria;
    private int agilitat;
    private ArrayList<Arma> armes;
    private Arma armaActual;
    private int raca;



    public Personatge(String nom, int atac, int defensa, double vidaMax) {
        super(nom, atac, defensa, vidaMax);
        this.nivell = 1;
        this.or = 0;
        this.xp = 0;
        this.xpNecessaria = 10;
        this.agilitat = 5;
        this.armes = new ArrayList<>();
    }

    public Personatge() {
        super();
        this.armes = new ArrayList<>();
    }

    public ArrayList<Arma> getArmes() {
        return armes;
    }

    public void afegirArma(Arma arma) {
        this.armes.add(arma);
    }

    public Arma getArmaActual() {
        return armaActual;
    }

    public void setArmaActual(Arma armaActual) {
        this.armaActual = armaActual;
    }

    public int getRaca() {
        return raca;
    }

    public void setRaca(int raca) {
        this.raca = raca;
    }


    public void assignarArmesPerRaca() {
        armes.clear();

        switch (raca) {
            case 1 -> { // Humano
                afegirArma(new Arma(6, "Espada corta", 1, null, null));
                afegirArma(new Arma(8, "Maza ligera", 1, null, null));
            }
            case 2 -> { // Elfo
                afegirArma(new Arma(6, "Arco de madera", 2, null, null));
                afegirArma(new Arma(4, "Daga élfica", 2, null, null));
            }
            case 3 -> { // Enano
                afegirArma(new Arma(10, "Martillo de guerra", 3, null, null));
                afegirArma(new Arma(8, "Hacha doble", 3, null, null));
            }
            default -> {
                afegirArma(new Arma(4, "Bastón básico", 0, null, null));
            }
        }

        // Asignamos el primer arma como la equipada
        if (!armes.isEmpty()) {
            armaActual = armes.get(0);
        }
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
    public void restaurarEstatDespresDeCarregar() {
        // Asegurar que la vida actual no supere la máxima
        if (getVidaActual() > getVidaMax()) {
            setVidaActual((int) getVidaMax());
        }

        // Volver a aplicar los límites y recalcular la barra si existe
        establirVida((int) getVidaActual());

        if (getBarraVida() != null) {
            getBarraVida().setMaximum((int) getVidaMax());
            getBarraVida().setValue((int) getVidaActual());
        }

        // Si tienes armas según raza y no estaban asignadas
        if ((armes == null || armes.isEmpty()) && raca > 0) {
            assignarArmesPerRaca();
        }
    }

}
