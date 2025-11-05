package miniROL;

import javax.swing.*;
import java.io.*;

public class Main {

    private static final String FITXER_PARTIDA = System.getProperty("user.home") + File.separator + "partida_guardada.dat";


    public static void main(String[] args) {

        Personatge heroi = null;
        boolean cargar = false;

        // Mostrar menú de selección
        int opcio = JOptionPane.showConfirmDialog(
                null,
                "¿Quieres cargar una partida guardada?",
                "Cargar o Nueva Partida",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (opcio == JOptionPane.YES_OPTION) {
            // Cargar partida
            heroi = cargarPartida();
            if (heroi != null) {
                cargar = true;
                JOptionPane.showMessageDialog(null, "Partida cargada con éxito. Nivel: " + heroi.getNivell());
            } else {
                // Si la carga falla, forzamos a nueva partida
                JOptionPane.showMessageDialog(null, "No se encontró partida guardada o hubo un error. Se iniciará una nueva partida.");
                opcio = JOptionPane.NO_OPTION;
            }
        }

        if (opcio == JOptionPane.NO_OPTION || opcio == JOptionPane.CANCEL_OPTION || heroi == null) {
            // Nueva partida
            heroi = new Personatge("Ilias", 0, 0, 55);
        }

        FinestraPrincipal joc = new FinestraPrincipal(heroi);

        // Si es nueva partida, mostrar selección de raza
        if (!cargar) {
            CatalegRaces cataleg = new CatalegRaces(joc);
            cataleg.obrirCataleg();
        }

        // Iniciar el juego
        joc.StartJoc();

        // **OPCIONAL: Lógica para guardar la partida al cerrar (Depende de cómo manejes el cierre de FinestraPrincipal)**
        // Se puede añadir un gancho de apagado para guardar:
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            guardarPartida(joc.getHeroi());
        }));
    }

    /**
     * Guarda el objeto Personatge serializándolo en un archivo.
     */
    public static void guardarPartida(Personatge heroi) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER_PARTIDA))) {
            oos.writeObject(heroi);
            System.out.println("Partida guardada en " + FITXER_PARTIDA);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la partida: " + e.getMessage(), "Error de Guardado", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga el objeto Personatge deserializándolo desde un archivo.
     */
    public static Personatge cargarPartida() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FITXER_PARTIDA))) {
            Personatge heroiCargado = (Personatge) ois.readObject();

            // Restablecer los componentes transient (JProgressBar)
            heroiCargado.inicialitzarBarraVida();

            return heroiCargado;
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró archivo de partida guardada. Se inicia una nueva partida.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la partida: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}