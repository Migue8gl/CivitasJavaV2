package civitasv2;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {

    private Jugador propietario;
    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;
    private static float FACTORALQUILERCALLE = 1.0f;
    private static float FACTORALQUILERCASA = 1.0f;
    private static float FACTORALQUILERHOTEL = 4.0f;

    CasillaCalle(String nombre, float unPrecioCompra, float unPrecioEdificar, float unPrecioAlquilerBase) {
        super(nombre);
        this.precioCompra = unPrecioCompra;
        this.precioEdificar = unPrecioEdificar;
        this.precioBaseAlquiler = unPrecioAlquilerBase;
    }

    private boolean esEsteElPropietario(Jugador jugador) {
        boolean esPropietario = false;

        if (this.propietario == jugador) {
            esPropietario = true;
        }

        return esPropietario;
    }
    
    void actualizaPropietarioPorConversion(Jugador jug) {
        this.propietario = jug;
    }

    static float getFactorAlquilerCalle() {
        return CasillaCalle.FACTORALQUILERCALLE;
    }

    static float getFactorAlquilerCasa() {
        return CasillaCalle.FACTORALQUILERCASA;
    }

    static float getFactorAlquilerHotel() {
        return CasillaCalle.FACTORALQUILERHOTEL;
    }

    float getPrecioAlquilerCompleto() {
        return this.precioBaseAlquiler * (CasillaCalle.getFactorAlquilerCasa() + this.numCasas + this.numHoteles * CasillaCalle.getFactorAlquilerHotel());
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

    public int getNumCasas() {
        return this.numCasas;
    }

    public int getNumHoteles() {
        return this.numHoteles;
    }

    public int cantidadCasasHoteles() {
        return this.getNumCasas() + this.getNumHoteles();
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

        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.getPrecioEdificar());
            this.numCasas++;
            result = true;
        }

        return result;
    }

    boolean construirHotel(Jugador jugador) {
        boolean result = false;

        if (this.esEsteElPropietario(jugador)) {
            this.propietario.paga(this.getPrecioEdificar());
            this.numHoteles++;
            result = true;
        }

        return result;
    }

    void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && !this.esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(this.getPrecioAlquiler());
            this.propietario.recibe(this.getPrecioAlquiler());
        }
    }

    @Override
    public String toString() {
        String devolver = super.toString() +  ". Precios: Compra: " + this.getPrecioCompra()
                + ", Edificar: " + this.getPrecioEdificar() + ", Alquiler base: " + this.getPrecioAlquiler()
                + ", Casas: " + this.getNumCasas() + ", Hoteles: " + this.getNumHoteles();
        
        if(this.tienePropietario())
            devolver += ", Jugador: " + this.propietario.getNombre();
        return devolver;
    }

    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos) {
        super.informe(actual, todos);
        Jugador jugador = todos.get(actual);
        if (!this.tienePropietario()) {
            jugador.puedeComprarCasilla();
        } else {
            this.tramitarAlquiler(jugador);
        }
    }

}
