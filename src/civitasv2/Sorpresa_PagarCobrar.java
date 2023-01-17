package civitasv2;

import java.util.ArrayList;


public class Sorpresa_PagarCobrar extends Sorpresa{
    
    public Sorpresa_PagarCobrar(String texto, int valor) {
        super(texto, valor);
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        todos.get(actual).modificarSaldo(this.valor);
    }

    public String toString() {
        return this.texto;
    }
}
