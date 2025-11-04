package miniROL;

public class ItemTienda {
    String nombre;
    String imagen;
    int precio;
    Runnable accion;
    boolean agotado = false;

    public ItemTienda(String nombre, String imagen, int precio, Runnable accion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.accion = accion;
    }
}
