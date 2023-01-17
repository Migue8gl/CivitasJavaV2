package civitasv2;

import java.util.ArrayList;

public class Casilla {

    private String nombre;

    Casilla(String _nombre) {
        this.init();
        this.nombre = _nombre;
    }

 
    void init() {
        this.nombre = "unknown";
    }

    public String getNombre() {
        return this.nombre;
    }
    
    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla de caracteristicas " + toString());
    }

    void recibeJugador(int actual, ArrayList<Jugador> todos) {
            informe(actual, todos);
    }
    
    @Override
    public String toString() {
        String devolver = "Casilla de nombre " + this.nombre;
        return devolver;
    }
}
