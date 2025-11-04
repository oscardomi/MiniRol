package miniROL;

import javax.swing.*;
import java.awt.*;

import marcPanellPersonalitzat.FramePer;

public class FinestraPrincipal {


    private FramePer marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior;

    private JLabel etNom, etNivell, etXp, etOr, etAtributs;
    private JLabel etImatge;

    private JButton botExplorar, botTienda;

    private Personatge pj;

    public FinestraPrincipal(Personatge pj) {

        this.pj = pj;

        marc = new FramePer(600,500,"Mini ROL", true);
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = new JPanel();
        panellInferior = new JPanel();

        etNom = new JLabel(pj.getNom()+ "       ");
        etNivell = new JLabel(" Lvl:" + pj.getNivell());
        etXp = new JLabel(" Xp:"+ pj.getXp() + "/" + pj.getXpNecessaria());
        etOr = new JLabel(" Or: "+ pj.getOr());

        etAtributs = new JLabel(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());
        etImatge = new JLabel();
        botExplorar = new JButton("Explorar");
        botTienda = new JButton("Tienda");

    }

    public void StartJoc(){
        muntarEscena();
        marc.setVisible(true);

    }

    private void muntarEscena() {

        //Elaborem panell superior amb dades del personatge

        modificarFonts();

        panellSuperior.add(etNom);
        panellSuperior.add(etNivell);
        panellSuperior.add(etXp);
        panellSuperior.add(etOr);
        panellSuperior.add(etAtributs);

        //Pepito lvl:1 xp:10/25

        panellSuperior.add(pj.getBarraVida());

        //preparar imatge central

        etImatge.setIcon(new ImageIcon("./imatges/castell.jpg"));
        panellPrincipal.add(etImatge, BorderLayout.CENTER);

        //botó panell inferior
        botExplorar.addActionListener(e->novaExploracio());
        botTienda.addActionListener(e->abrirtienda());
        panellInferior.add(botExplorar);
        panellInferior.add(botTienda);

        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        panellPrincipal.add(panellInferior, BorderLayout.SOUTH);

        marc.add(panellPrincipal);


    }

    private void abrirtienda() {

        Tienda t = new Tienda(this);
        t.abrirTienda();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        marc.repaint();
    }

    private void modificarFonts() {

        Font mevafont= new Font("Roboto", Font.BOLD, 20);

        etNom.setFont(mevafont);
    }

    private void novaExploracio() {

        Exploracio explora = new Exploracio(this);
        explora.startExploracio();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH); //tenia un problema
        marc.repaint();

    }

    public Personatge getPj() {
        return pj;
    }

 public JPanel getPanellSuperior() {
        return panellSuperior;
 }

    public FramePer getMarc() {
        return marc;
    }

    public JLabel getEtAtributs() {
        return etAtributs;
    }

    public JLabel getEtXp() {
        return etXp;
    }

    public JLabel getEtNivell() {
        return etNivell;
    }

    public JLabel getEtOr() {
        return etOr;
    }
}
