package miniROL;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Tienda {

    private JDialog marco;

    private JPanel panelPrincipal, panelSuperior, panelInferior, panelTienda;
    private JPanel panelEspada, panelEscudo, panelPocion, panelMapa;

    private JLabel imagenEspada, imagenEscudo, imagenPocion, imagenMapa;
    private JLabel desEspada, desEscudo, desPocion, desMapa;

    private static JButton botEspada, botEscudo, botPocion, botMapa;
    private static boolean agoEspada = false, agoEscudo = false, agoPocion = false, agoMapa = false;
    private JButton botSalir;

    private FinestraPrincipal fp;
    private Personatge pj;

    public Tienda(FinestraPrincipal fp) {
        this.fp = fp;

        pj = fp.getPj();

        panelPrincipal = new JPanel(new BorderLayout());
        panelSuperior = fp.getPanellSuperior();
        panelInferior = new JPanel();
        panelTienda = new JPanel(new GridLayout(2, 2));

        panelEspada = new JPanel();
        panelEscudo = new JPanel();
        panelPocion = new JPanel();
        panelMapa = new JPanel();

        imagenEspada = new JLabel(new ImageIcon("imatges/espasa.png"));
        imagenEscudo = new JLabel(new ImageIcon("imatges/escut.png"));
        imagenPocion = new JLabel(new ImageIcon("imatges/pocio.png"));
        imagenMapa = new JLabel(new ImageIcon("imatges/mapa.png"));

        desEspada = new JLabel("Espada - 75 oro.");
        desEscudo = new JLabel("Escudo - 65 oro.");
        desPocion = new JLabel("Pocion - 25 oro.");
        desMapa = new JLabel("Mapa - 15 oro.");

        botEspada = new JButton("Comprar");
        botEscudo = new JButton("Comprar");
        botPocion = new JButton("Comprar");
        botMapa = new JButton("Comprar");

        botSalir = new JButton("Salir");
    }

    public void abrirTienda(){

        montarInterfaz();
        marco.setVisible(true);
    }

    private void montarInterfaz() {
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        addObjeto(panelEspada, imagenEspada, desEspada, botEspada, "espada", agoEspada);
        addObjeto(panelEscudo, imagenEscudo, desEscudo, botEscudo, "escudo", agoEscudo);
        addObjeto(panelPocion, imagenPocion, desPocion, botPocion, "pocion", agoPocion);
        addObjeto(panelMapa, imagenMapa, desMapa, botMapa, "mapa", agoMapa);

        panelPrincipal.add(panelTienda, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        panelInferior.add(botSalir);

        marco = new JDialog(fp.getMarc(), "Tienda", true);
        marco.setSize(600, 600);
        marco.setLocationRelativeTo(null);
        marco.add(panelPrincipal);

        botSalir.addActionListener(e -> marco.dispose());
    }

    private void addObjeto(JPanel panelObjeto, JLabel imagen, JLabel descripcion, JButton boton, String nombre, boolean agotado) {
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (agotado) boton.setEnabled(false);
        boton.addActionListener(e -> comprarObjeto(boton,nombre));

        panelObjeto.setLayout(new BoxLayout(panelObjeto, BoxLayout.Y_AXIS));
        panelObjeto.add(imagen);
        panelObjeto.add(descripcion);
        panelObjeto.add(boton);
        panelTienda.add(panelObjeto);
    }

    private void comprarObjeto(JButton boton, String nombre) {
        switch (nombre){
            case "espada":
                if (pj.getOr() >= 75){
                    pj.setAtac(pj.getAtac() + 5);
                    fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());
                    pj.setOr(pj.getOr() - 75);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boton.setEnabled(false);
                    agoEspada = true;
                }
                break;
            case "escudo":
                if (pj.getOr() >= 65){
                    pj.setDefensa(pj.getDefensa() + 7);
                    fp.getEtAtributs().setText(" Atc:" + pj.getAtac()+ "| Def: "+ pj.getDefensa());
                    pj.setOr(pj.getOr() - 65);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boton.setEnabled(false);
                    agoEscudo = true;
                }
                break;
            case "pocion":
                if (pj.getOr() >= 25){
                    pj.setVidaActual(pj.getVidaActual() + 5);
                    pj.establirVida(pj.getVidaActual());
                    pj.setOr(pj.getOr() - 25);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boton.setEnabled(false);
                    agoPocion = true;
                }
                break;
            case "mapa":
                if (pj.getOr() >= 15){
                    Exploracio.setNumExploracio(250);
                    pj.setOr(pj.getOr() - 15);
                    fp.getEtOr().setText(" Or: "+ pj.getOr());
                    boton.setEnabled(false);
                    agoMapa = true;
                }
                break;
        }
    }

}
