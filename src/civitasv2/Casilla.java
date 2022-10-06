package civitasv2;

import java.util.ArrayList;

public class Casilla {

    //Atributos de la clase
    private TipoCasilla tipo;
    private String nombre;
    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;
    private static float FACTORALQUILERCALLE = 1.0f;
    private static float FACTORALQUILERCASA = 1.0f;
    private static float FACTORALQUILERHOTEL = 4.0f;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    private Jugador propietario;

    Casilla(String titulo, float unPrecioCompra, float unPrecioEdificar, float unPrecioAlquilerBase) {
        this.init();
        this.tipo = TipoCasilla.CALLE;
        this.nombre = titulo;
        this.precioCompra = unPrecioCompra;
        this.precioEdificar = unPrecioEdificar;
        this.precioBaseAlquiler = unPrecioAlquilerBase;
    }

    Casilla(String _nombre) {
        this.init();
        this.tipo = TipoCasilla.DESCANSO;
        this.nombre = _nombre;
    }

    Casilla(String _nombre, MazoSorpresas mazo) {
        this.init();
        this.tipo = TipoCasilla.DESCANSO;
        this.nombre = _nombre;
        this.mazo = mazo;
    }

    void init() {
        this.tipo = null;
        this.nombre = "unknown";
        this.mazo = null;
        this.propietario = null;
        this.sorpresa = null;
        this.precioCompra = 0;
        this.precioEdificar = 0;
        this.precioBaseAlquiler = 0;
    }

    String getNombre() {
        return this.nombre;
    }

    static float getFactorAlquilerCalle() {
        return Casilla.FACTORALQUILERCALLE;
    }

    static float getFactorAlquilerCasa() {
        return Casilla.FACTORALQUILERCASA;
    }

    static float getFactorAlquilerHotel() {
        return Casilla.FACTORALQUILERHOTEL;
    }

    float getPrecioAlquilerCompleto() {
        return this.precioBaseAlquiler * (this.getFactorAlquilerCasa() + this.numCasas + this.numHoteles * this.getFactorAlquilerHotel());
    }

    float getPrecioCompra() {
        return this.precioCompra;
    }

    float getPrecioEdificar() {
        return this.precioEdificar;
    }

    float getPrecioAlquiler() {
        return this.precioBaseAlquiler;
    }

    int getNumCasas() {
        return this.numCasas;
    }

    int getNumHoteles() {
        return this.numHoteles;
    }
    
    public int cantidadCasasHoteles() {
        return this.getNumCasas() + this.getNumHoteles();
    }

    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() + " ha caido en la casilla de caracteristicas " + toString());
    }

    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        informe(actual, todos);
    }
    
    boolean derruirCasas(int n, Jugador jugador) {
        boolean derruir = false;
        if (this.esEsteElPropietario(jugador) && this.getNumCasas() >= n) {
            this.numCasas -= n;
            derruir = true;
        }

        return derruir;
    }
    
    boolean tienePropietario() {
        boolean tiene_prop = false;

        if (this.propietario != null) {
            tiene_prop = true;
        }

        return tiene_prop;
    }

    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos) {
        this.informe(actual, todos);
        Jugador jugador = todos.get(actual);
        if (!this.tienePropietario()) {
            jugador.puedeComprarCasilla();
        } else {
            this.tramitarAlquiler(jugador);
        }
    }

    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos) {
        this.informe(actual, todos);
        this.sorpresa = this.mazo.siguiente();
        this.informe(actual, todos);
        this.sorpresa.aplicarAJugador(actual, todos);

    }
    
    boolean comprar(Jugador jugador) {
        boolean result = false;

        if (!this.tienePropietario()) {
            this.propietario = jugador;
            result = true;
            this.propietario.paga(this.getPrecioCompra());
        }

        return result;
    }
    
    boolean construirCasa(Jugador jugador) {
        boolean result = false;
        
        if(this.esEsteElPropietario(jugador)){
            this.propietario.paga(this.getPrecioEdificar());
            this.numCasas++;
            result = true;
        }
        
        return result;
    }

    boolean construirHotel(Jugador jugador) {
        boolean result = false;

        if (esEsteElPropietario(jugador)) {
            this.propietario.paga(this.getPrecioEdificar());
            this.numHoteles++;
            result = true;
        }

        return result;
    }
    
    void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && !this.esEsteElPropietario(jug)) {
            jugador.pagaAlquiler(this.getPrecioAlquiler());
            this.propietario.recibe(this.getPrecioAlquiler());
        }
    }

    @Override
    public String toString() {
        String devolver = "Casilla de nombre " + this.nombre + ". Precios: Compra: " + this.getPrecioCompra()
                + ", Edificar: " + this.getPrecioEdificar() + ", Alquiler base: " + this.getPrecioAlquiler()
                + ", Casas: " + this.getNumCasas() + ", Hoteles: " + this.getNumHoteles();
        
        if(this.tienePropietario())
            devolver += ", Jugador: " + this.propietario.getNombre();
        return devolver;
    }
}
