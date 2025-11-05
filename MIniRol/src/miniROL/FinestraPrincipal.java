package miniROL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import marcPanellPersonalitzat.FramePer;

public class FinestraPrincipal {

    private FramePer marc;
    private JPanel panellPrincipal, panellSuperior, panellInferior;

    private JLabel etNom, etNivell, etXp, etOr, etAtributs;
    private JLabel etImatge;

    private JButton botExplorar, botTienda, guardarButton;

    private Personatge pj;

    public FinestraPrincipal(Personatge pj) {
        this.pj = pj;

        marc = new FramePer(600, 500, "Mini ROL", true);
        panellPrincipal = new JPanel(new BorderLayout());
        panellSuperior = new JPanel();
        panellInferior = new JPanel();

        etNom = new JLabel(pj.getNom() + "       ");
        etNivell = new JLabel(" Lvl:" + pj.getNivell());
        etXp = new JLabel(" Xp:" + pj.getXp() + "/" + pj.getXpNecessaria());
        etOr = new JLabel(" Or: " + pj.getOr());
        etAtributs = new JLabel(" Atc:" + pj.getAtac() + " | Def: " + pj.getDefensa());

        etImatge = new JLabel();
        botExplorar = new JButton("Explorar");
        botTienda = new JButton("Tienda");
        guardarButton = new JButton("Guardar partida"); // 🆕 nuevo botón
    }

    public void StartJoc() {
        muntarEscena();
        configurarCierreSegur(); // 🆕 guarda al cerrar ventana
        marc.setVisible(true);
    }

    private void muntarEscena() {
        modificarFonts();

        // Panel superior con datos del personaje
        panellSuperior.add(etNom);
        panellSuperior.add(etNivell);
        panellSuperior.add(etXp);
        panellSuperior.add(etOr);
        panellSuperior.add(etAtributs);
        panellSuperior.add(pj.getBarraVida());

        // Imagen central
        etImatge.setIcon(new ImageIcon("./imatges/castell.jpg"));
        panellPrincipal.add(etImatge, BorderLayout.CENTER);

        // Panel inferior con botones
        botExplorar.addActionListener(e -> novaExploracio());
        botTienda.addActionListener(e -> abrirtienda());
        guardarButton.addActionListener(e -> guardarPartidaManual()); // 🆕

        panellInferior.add(botExplorar);
        panellInferior.add(botTienda);
        panellInferior.add(guardarButton); // 🆕

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
        Font mevafont = new Font("Roboto", Font.BOLD, 20);
        etNom.setFont(mevafont);
    }

    private void novaExploracio() {
        Exploracio explora = new Exploracio(this);
        explora.startExploracio();
        panellPrincipal.add(panellSuperior, BorderLayout.NORTH);
        marc.repaint();
    }

    // 🆕 NUEVO: guardar al pulsar el botón
    private void guardarPartidaManual() {
        Main.guardarPartida(pj);
        JOptionPane.showMessageDialog(marc, "Partida guardada correctamente.");
    }

    // 🆕 NUEVO: guardar automáticamente al cerrar la ventana
    private void configurarCierreSegur() {
        marc.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.guardarPartida(pj);
                System.out.println("Partida guardada al cerrar ventana.");
            }
        });
    }

    // --- Getters ---

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

    // 🧩 Corregido: antes hacía recursión infinita
    public Personatge getHeroi() {
        return pj;
    }
}
