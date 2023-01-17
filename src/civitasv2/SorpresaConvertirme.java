package civitasv2;

import java.util.ArrayList;

public class SorpresaConvertirme  extends Sorpresa{
    public SorpresaConvertirme(String texto, int valor) {
        super(texto, valor);
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        JugadorEspeculador jug = todos.get(actual).convertir();
        todos.set(actual, jug);
    }

    public String toString() {
        return this.texto;
    }
}
