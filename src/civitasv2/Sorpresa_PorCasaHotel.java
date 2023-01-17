package civitasv2;

import java.util.ArrayList;

public class Sorpresa_PorCasaHotel extends Sorpresa{
    
    public Sorpresa_PorCasaHotel(String texto, int valor) {
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

