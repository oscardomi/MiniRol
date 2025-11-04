package miniROL;

public interface IAtacable {
    public String atacar(IAtacable enemic);
    public void rebreFerida(int quantitat);

    int getDefensa();

    String getNom();
}
 