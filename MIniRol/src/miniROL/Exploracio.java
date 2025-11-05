package miniROL;

import javax.swing.*;
import java.awt.*;

public class Exploracio {

    private JDialog marc;

    private JPanel panellPrincipal, panellSuperior, panellInferior, panellMonstre, panellMonstreSec;

    private JButton botAtacar, botFugir;

    private JTextArea infoExploracio;
    private JScrollPane barraDes;

    private Personatge pj;

    private Monstre enemic;

    private static int numExploracio = 0;

    private FinestraPrincipal fp; //per poder accedir a les etiquetes

    public Exploracio(FinestraPrincipal fp){

        this.fp = fp;

        pj = fp.getPj();

        marc = new JDialog();

        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = fp.getPanellSuperior();
        panellInferior = new JPanel();
        panellMonstre = new JPanel();
        panellMonstreSec = new JPanel();

        infoExploracio = new JTextArea();
        infoExploracio.setEditable(false);


        barraDes = new JScrollPane(infoExploracio);
        barraDes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        botAtacar = new JButton("Atacar");
        botFugir = new JButton("Fugir");
    }


    public void startExploracio() {
        
        decidirDificultat();
        montarInteficie();
    }

    private void decidirDificultat() {

        int numAlea = (int) (Math.random()*100) + numExploracio;

        numExploracio++;

        //ara aquí instanciarem un objecte de la classe monstre

        enemic = Monstre.generaMontre(numAlea);
    }

    private void montarInteficie() {

        //panell superior llest

        //afegir l'area de text al panell princpi

        panellPrincipal.add(barraDes, BorderLayout.CENTER);

        //afegir tot el del mosntre

        panellMonstreSec.add(enemic.getEtNom());
        panellMonstreSec.add(enemic.getBarraVida());

        panellMonstre.setLayout(new BoxLayout(panellMonstre, BoxLayout.Y_AXIS));
        panellMonstre.add(enemic.getImatge());
        panellMonstre.add(panellMonstreSec);


        //fem panell inferior
        botAtacar.addActionListener(e->atacar());
        botFugir.addActionListener(e->marc.dispose());

        panellInferior.add(botAtacar);
        panellInferior.add(new JLabel("          "));
        panellInferior.add(botFugir);
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        panellPrincipal.add(panellMonstre,BorderLayout.EAST);

        marc.add(panellPrincipal);
        marc.setSize(750,500);
        marc.setLocationRelativeTo(null);
        marc.setModal(true);

        marc.setVisible(true);


    }

    private void atacar() {

        // Ataque del jugador al enemigo
        String mensajePJ = pj.atacar(enemic); // la defensa del enemigo ya se resta dentro
        infoExploracio.append(mensajePJ + "\n");
        infoExploracio.append(enemic.getNom() + " vida restante: " + enemic.getVidaActual() + "\n\n");
        enemic.establirVida(enemic.getVidaActual());

        if (!enemic.isEstaViu()) {
            enemicDerrotat();
            return;
        }

        // Ataque del enemigo al jugador
        String mensajeEnemigo = enemic.atacar(pj); // la defensa del jugador se resta dentro
        infoExploracio.append(mensajeEnemigo + "\n");
        infoExploracio.append(pj.getNom() + " vida restante: " + pj.getVidaActual() + "\n\n");
        pj.establirVida(pj.getVidaActual());

        if (!pj.isEstaViu()) derrota();
    }



    private void derrota() {
        botAtacar.setEnabled(false);
        botFugir.setText("Sortir");

        infoExploracio.setText(infoExploracio.getText() + pj.getNom() + " ha sigut derrotat...\n\nGAME OVER\n");

        marc.dispose();

        FinestraFinal f = new FinestraFinal(FinestraFinal.DERROTA, pj);
        f.obrir();
    }

    private void enemicDerrotat() {

        botAtacar.setEnabled(false);
        botFugir.setText("Sortir");

        infoExploracio.setText(infoExploracio.getText() + enemic.getNom() + " ha sigut derrotat \n"
                    + "Has obtingut " + enemic.getPremiOr() + " or.\n"
                    + "Guanyes " + enemic.getPremiXp() + " punts d'experiència.");

        pj.pujarXp(enemic.getPremiOr());
        fp.getEtXp().setText(" Xp:"+ pj.getXp() + "/" + pj.getXpNecessaria());
        fp.getEtNivell().setText(" Lvl:" + pj.getNivell());
        fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());

        pj.setOr(pj.getOr() + enemic.getPremiOr());
        fp.getEtOr().setText(" Or: "+ pj.getOr());

        if (enemic.getNom().equals("Boss")){
            FinestraFinal f = new FinestraFinal(FinestraFinal.VICTORIA, pj);
            f.obrir();

        }

    }

    public static void setNumExploracio(int numExploracio) {
        Exploracio.numExploracio = numExploracio;
    }
}
