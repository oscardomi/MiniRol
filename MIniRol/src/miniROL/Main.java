package miniROL;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Personatge heroi = null;

            // 🔹 Comprobar si existe una partida guardada
            if (GestorPartida.existeixPartida()) {
                Object[] opciones = {
                        "Cargar partida",
                        "Nuevo personaje (sobrescribir)",
                        "Cancelar"
                };

                int eleccion = JOptionPane.showOptionDialog(
                        null,
                        "Ya existe una partida guardada.\n¿Qué quieres hacer?",
                        "MiniROL",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                switch (eleccion) {
                    case 0: // Cargar partida existente
                        heroi = GestorPartida.carregarPersonatge();
                        if (heroi == null) {
                            JOptionPane.showMessageDialog(null, "No se pudo cargar la partida. Se creará un nuevo personaje.");
                            heroi = crearNouPersonatge();
                            GestorPartida.guardarPersonatge(heroi);
                        } else {
                            // 🔄 Restaurar estado del personaje tras deserializar
                            heroi.restaurarEstatDespresDeCarregar();
                        }
                        break;

                    case 1: // Crear nuevo (sobrescribir)
                        heroi = crearNouPersonatge();
                        GestorPartida.guardarPersonatge(heroi);
                        break;

                    default: // Cancelar
                        System.exit(0);
                }
            } else {
                // 🔹 No hay partida guardada, crear nueva
                heroi = crearNouPersonatge();
                GestorPartida.guardarPersonatge(heroi);
            }

            // 🔹 Crear ventana principal del juego
            FinestraPrincipal joc = new FinestraPrincipal(heroi);

            // 🔹 Si el personaje es nuevo, abrir selección de raza
            if (heroi.getNivell() == 1 && heroi.getXp() == 0) {
                CatalegRaces cataleg = new CatalegRaces(joc);
                cataleg.obrirCataleg();
            }

            // 🔹 Iniciar el juego
            joc.StartJoc();
        });
    }

    /**
     * Crea un nuevo personaje pidiendo nombre al usuario.
     */
    private static Personatge crearNouPersonatge() {
        String nom = JOptionPane.showInputDialog(null, "Introduce el nombre de tu personaje:");
        if (nom == null || nom.isBlank()) nom = "Heroe";
        return new Personatge(nom, 5, 3, 50);
    }
}
