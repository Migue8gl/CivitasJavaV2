package civitasv2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class CivitasJuego {

    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private GestorEstados gestor;
    private EstadoJuego estado;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private Random aleatorio = new Random();

    public CivitasJuego(ArrayList<String> nombres, boolean debug) {
        this.jugadores = new ArrayList<Jugador>();

        for (String nomb : nombres) {
            this.jugadores.add(new Jugador(nomb));
        }
        this.gestor = new GestorEstados();
        this.gestor.estadoInicial();
        Dado.getInstance().setDebug(debug);
        this.mazo = new MazoSorpresas(debug);
        this.indiceJugadorActual = Dado.getInstance().quienEmpieza(this.jugadores.size());
        this.estado = EstadoJuego.INICIO_TURNO;
        this.tablero = new Tablero();
        this.inicializarTablero(this.mazo);
        this.inicializarMazoSorpresas();
    }

    private void avanzaJugador() {
        Jugador jugadorActual = this.jugadores.get(this.indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = this.tablero.getCasilla(posicionNueva);

        this.contabilizarPasosPorsalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(this.indiceJugadorActual, this.jugadores);
        this.contabilizarPasosPorsalida(jugadorActual);
        this.jugadores.set(this.indiceJugadorActual, jugadorActual);
    }

    public Casilla getCasillaActual() {
        int indice = this.jugadores.get(this.indiceJugadorActual).getNumCasillaActual();
        return this.tablero.getCasilla(indice);
    }

    public boolean comprar() {
        boolean res = false;
        Jugador jugadorActual = this.jugadores.get(this.indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        CasillaCalle casilla = (CasillaCalle) this.tablero.getCasilla(numCasillaActual);
        res = jugadorActual.comprar(casilla);
        return res;
    }

    public boolean construirCasa(int ip) {
        return this.jugadores.get(this.indiceJugadorActual).construirCasa(ip);
    }

    public boolean construirHotel(int ip) {
        return this.jugadores.get(this.indiceJugadorActual).construirHotel(ip);
    }

    private void contabilizarPasosPorsalida(Jugador jugadorActual) {
        while (this.tablero.getPorSalida()) {
            jugadorActual.pasaPorSalida();
        }
    }

    public boolean finalDelJuego() {
        boolean fin = false;
        for (int i = 0; i < jugadores.size() && !fin; i++) {
            if (jugadores.get(i).getSaldo() == 0) {
                fin = true;
            }
        }
        return fin;
    }

    public Jugador getJugadorActual() {
        return this.jugadores.get(this.indiceJugadorActual);
    }

    private void inicializarMazoSorpresas() {
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                this.mazo.alMazo(new Sorpresa_PagarCobrar("cobrar", 1));
            } else {
                this.mazo.alMazo(new Sorpresa_PagarCobrar("pagar", -1));
            }
        }

        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                this.mazo.alMazo(new Sorpresa_PorCasaHotel("cobrar", 1));
            } else {
                this.mazo.alMazo(new Sorpresa_PorCasaHotel("pagar", -1));
            }
        }

        for (int i = 0; i < 2; i++) {
            this.mazo.alMazo(new SorpresaConvertirme("converison", 0));
        }
    }

    private void inicializarTablero(MazoSorpresas mazo) {
        this.tablero = new Tablero();
        this.tablero.añadeCasilla(new Casilla("PARKING"));
        for (int i = 0; i < 4; i++) {
            this.tablero.añadeCasilla(new CasillaSorpresa("SORPRESA", mazo));
        }

        for (int i = 0; i < 14; i++) {
            this.tablero.añadeCasilla(new CasillaCalle("CALLE", 1000, 1000, 1000));
        }
    }

    private void pasarTurno() {
        this.indiceJugadorActual++;
        if (indiceJugadorActual == jugadores.size()) {
            indiceJugadorActual = 0;
        }
    }

    public ArrayList<Jugador> ranking() {
        ArrayList<Jugador> ranking = new ArrayList<>();
        int comprobante = 0, num_inicial = this.jugadores.size();
        boolean es_maximo;
        while (ranking.size() < num_inicial) {
            for (int i = this.jugadores.size() - 1; i >= 0; i--) {
                Jugador actual = this.jugadores.get(i);
                es_maximo = true;
                for (int j = 0; j < this.jugadores.size() && es_maximo; j++) {
                    comprobante = actual.compareTo(this.jugadores.get(j));
                    if (comprobante == -1) {
                        es_maximo = false;
                    }
                }
                if (es_maximo) {
                    ranking.add(actual);
                    this.jugadores.remove(i);
                }
            }
        }
        return ranking;
    }

    public OperacionJuego siguientePaso() {
        Jugador jugadorActual = this.jugadores.get(this.indiceJugadorActual);
        OperacionJuego operacion = this.gestor.siguienteOperacion(jugadorActual, this.estado);
        if (operacion == OperacionJuego.PASAR_TURNO) {
            this.pasarTurno();
            this.siguientePasoCompletado(operacion);
        } else if (operacion == OperacionJuego.AVANZAR) {
            this.avanzaJugador();
            this.siguientePasoCompletado(operacion);
        }
        return operacion;
    }

    public void siguientePasoCompletado(OperacionJuego operacion) {
        this.estado = this.gestor.siguienteEstado(this.jugadores.get(this.indiceJugadorActual), this.estado, operacion);
    }

}
