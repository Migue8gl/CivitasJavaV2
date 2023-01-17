package civitasv2;

import java.util.ArrayList;

abstract class Sorpresa {

    protected String texto;
    protected int valor;
    private MazoSorpresas mazo;

    Sorpresa(String texto, int valor) {
        this.texto = texto;
        this.valor = valor;
        this.mazo = null;
    }

    protected void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se esta aplicando la sorpresa " + this.toString() + " al jugador " + todos.get(actual).getNombre());
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        this.informe(actual, todos);
    }

    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        informe(actual, todos);
        for (int i = 0; i < todos.get(actual).cantidadCasasHoteles(); i++) {
            todos.get(actual).modificarSaldo(this.valor * todos.get(actual).cantidadCasasHoteles());
        }
    }
    
    @Override
    public String toString() {
        return this.texto;
    }
}
