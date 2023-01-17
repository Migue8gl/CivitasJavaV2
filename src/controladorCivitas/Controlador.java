package controladorCivitas;

import GUI.CivitasView;
import civitasv2.CivitasJuego;
import civitasv2.Diario;
import civitasv2.GestionInmobiliaria;
import civitasv2.OperacionInmobiliaria;
import civitasv2.OperacionJuego;
import civitasv2.Casilla;
import civitasv2.Jugador;
import java.util.ArrayList;
import GUI.VistaTextual;

public class Controlador {

    private CivitasJuego juego;
    private CivitasView vista;

    public Controlador(CivitasJuego _juego, CivitasView _vista) {
        this.juego = _juego;
        this.vista = _vista;
    }
    

    public void juega() {

        while (!this.juego.finalDelJuego()) {
            this.vista.actualiza();
            this.vista.pausa();
            OperacionJuego operacion = this.juego.siguientePaso();
            this.vista.mostrarSiguienteOperacion(operacion);

            if (operacion != OperacionJuego.PASAR_TURNO) {
                while (Diario.getInstance().eventosPendientes()) {
                    System.out.println("-----------------------------\n" + Diario.getInstance().leerEvento());
                }
            }

            System.out.println("______________________________");

            if (!this.juego.finalDelJuego()) {
                switch (operacion) {
                    case COMPRAR:
                        if (this.vista.comprar() == Respuesta.SI) {
                            this.juego.comprar();
                        }
                        this.juego.siguientePasoCompletado(operacion);
                        break;
                    case GESTIONAR:
                        OperacionInmobiliaria op = this.vista.elegirOperacion();
                        int index_gestion = 0;
                        
                        if(op != OperacionInmobiliaria.TERMINAR)
                            index_gestion = this.vista.elegirPropiedad();
                        
                        GestionInmobiliaria gestion = new GestionInmobiliaria(op, index_gestion);

                        switch (gestion.getGestion()) {
                            case CONSTRUIR_CASA:
                                this.juego.construirCasa(gestion.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                this.juego.construirHotel(gestion.getNumPropiedad());
                            case TERMINAR:
                                this.juego.siguientePasoCompletado(operacion);
                                break;
                        }
                        break;
                }
            }
        }
        System.out.println("\nFIN DEL JUEGO.\n =======RANKING=======");
        ArrayList<Jugador> ranking = juego.ranking();
        for (int i = 0; i < 4.; i++) {
            System.out.println(ranking.get(i).toString() + "\n ---------------");
        }
    }
}
