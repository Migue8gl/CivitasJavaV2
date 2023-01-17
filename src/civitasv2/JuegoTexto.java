package civitasv2;
import GUI.VistaTextual;
import controladorCivitas.Controlador;

import java.util.ArrayList;

public class JuegoTexto {

    public static void main(String[] args) {
                
        ArrayList<String> jugadores = new ArrayList<>();
        jugadores.add("Miguel");
        jugadores.add("Martita");
        jugadores.add("Santana");
        jugadores.add("Centeno");
        CivitasJuego juego = new CivitasJuego(jugadores, true);
        VistaTextual vista = new VistaTextual(juego);
        //Controlador controlador = new Controlador(juego, vista);
        //controlador.juega();
    }
}
