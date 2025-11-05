package miniROL;

public interface IAtacable {
    String atacar(IAtacable enemic);
    void rebreFerida(int quantitat);
    int getDefensa();
    boolean isEstaViu();
}