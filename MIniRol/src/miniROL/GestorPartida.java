package miniROL;

import java.io.*;

public class GestorPartida {

    private static final String FITXER = "partida.dat";

    public static void guardarPersonatge(Personatge p) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(p);
            System.out.println("✅ Partida guardada correctamente.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar la partida: " + e.getMessage());
        }
    }

    public static Personatge carregarPersonatge() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FITXER))) {
            return (Personatge) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ No se pudo cargar la partida: " + e.getMessage());
            return null;
        }
    }

    public static boolean existeixPartida() {
        return new File(FITXER).exists();
    }
}
