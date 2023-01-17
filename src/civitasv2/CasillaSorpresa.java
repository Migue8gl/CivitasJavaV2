package civitasv2;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {

    private Sorpresa sorpresa;
    private MazoSorpresas mazo;

    public CasillaSorpresa(String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.mazo = mazo;
    }

    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla de caracteristicas " + toString());
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        this.sorpresa = this.mazo.siguiente();
        this.sorpresa.aplicarAJugador(actual, todos);
    }

    @Override
    public String toString() {
        return super.toString() + "\nLa sorpresa que saldrá es:\n" + this.mazo.siguiente().toString() + "\n";
    }
}
