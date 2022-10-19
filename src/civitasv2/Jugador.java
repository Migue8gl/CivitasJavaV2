package civitasv2;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {

    final protected int CasasMax = 4;
    final protected int CasasPorHotel = 4;
    final protected int HotelesMax = 4;
    private String nombre;
    private int casillaActual;
    final protected float PasoPorSalida = 1000;
    private boolean puedeComprar;
    private float saldo;
    final private float SaldoInicial = 7500;
    private ArrayList<Casilla> propiedades;

    Jugador(String nomb) {
        this.nombre = nomb;
        this.propiedades = new ArrayList<>();
        this.casillaActual = 0;
        this.puedeComprar = true;
        this.saldo = this.SaldoInicial;
    }

    protected Jugador(Jugador jug) {
        this.propiedades = new ArrayList<>(jug.propiedades);
        this.nombre = jug.getNombre();
        this.casillaActual = jug.getNumCasillaActual();
        this.puedeComprar = jug.getPuedeComprar();
        this.saldo = jug.getSaldo();
    }

    int cantidadCasasHoteles() {
        int cantidad = 0;

        for (int i = 0; i < this.propiedades.size(); i++) {
            cantidad += (this.propiedades.get(i).getNumCasas() + this.propiedades.get(i).getNumHoteles());
        }

        return cantidad;
    }

    @Override
    public int compareTo(Jugador otro) {
        int comp = 0;

        if (this.getSaldo() > otro.getSaldo()) {
            comp = 1;
        } else if (this.getSaldo() < otro.getSaldo()) {
            comp = -1;
        }

        return comp;
    }

    boolean puedeComprarCasilla() {
        this.puedeComprar = true;
        return this.puedeComprar;
    }

    boolean comprar(Casilla titulo) {
        boolean result = false;

        if (this.getPuedeComprar()) {
            float precio = titulo.getPrecioCompra();

            if (this.puedoGastar(precio)) {
                result = titulo.comprar(this);

                if (result) {
                    this.getPropiedades().add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " compra la propiedad " + titulo.toString());
                }

                this.puedeComprar = false;
            }
        }

        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false;
        boolean existe = this.existeLaPropiedad(ip);

        if (existe) {
            Casilla propiedad = this.getPropiedades().get(ip);
            boolean puedoEdificar = this.puedoEdificarCasa(propiedad);
            float precio = propiedad.getPrecioEdificar();

            if (this.puedoEdificarCasa(propiedad)) {
                result = propiedad.construirCasa(this);
                Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " construye casa en propiedad " + this.getPropiedades().get(ip).getNombre());
            }
        }
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;

        if (this.existeLaPropiedad(ip)) {
            Casilla propiedad = this.getPropiedades().get(ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            float precio = propiedad.getPrecioEdificar();

            if (puedoEdificarHotel) {
                result = propiedad.construirHotel(this);
                propiedad.derruirCasas(getCasasPorHotel(), this);
                Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " construye hotel en propiedad " + this.getPropiedades().get(ip).getNombre());
            }
        }

        return result;
    }

    boolean enBancarrota() {
        boolean bancarrota = false;

        if (this.saldo == 0) {
            bancarrota = true;
        }

        return bancarrota;
    }

    private boolean existeLaPropiedad(int ip) {
        boolean existir = false;
        if (this.propiedades.size() > 0) {
            if (this.propiedades.get(ip) != null) {
                existir = true;
            }
        }

        return existir;
    }

    final private int getCasasMax() {
        return this.CasasMax;
    }

    final private int getHotelesMax() {
        return this.HotelesMax;
    }

    int getCasasPorHotel() {
        return this.CasasPorHotel;
    }

    protected String getNombre() {
        return this.nombre;
    }

    int getNumCasillaActual() {
        return this.casillaActual;
    }

    final private float getPremioPorSalida() {
        return this.PasoPorSalida;
    }

    protected ArrayList<Casilla> getPropiedades() {
        return this.propiedades;
    }

    boolean getPuedeComprar() {
        this.puedeComprar = true;
        return this.puedeComprar;
    }

    protected float getSaldo() {
        return this.saldo;
    }

    boolean modificarSaldo(float cantidad) {
        this.saldo += cantidad;
        Diario.getInstance().ocurreEvento("Saldo incrementado en " + cantidad);
        return true;
    }

    boolean moverACasilla(int numCasilla) {
        this.casillaActual = numCasilla;
        this.puedeComprar = false;
        Diario.getInstance().ocurreEvento("Jugador " + this.getNombre() + " se mueve a " + this.getNumCasillaActual());
        return this.puedeComprar;
    }

    boolean paga(float cantidad) {
        return modificarSaldo(cantidad * -1);
    }

    boolean pagaAlquiler(float cantidad) {
        return paga(cantidad);
    }

    boolean pasaPorSalida() {
        modificarSaldo(this.PasoPorSalida);
        Diario.getInstance().ocurreEvento("Jugador " + this.getNombre() + " ha pasado por salida");
        return true;
    }

    private boolean puedoEdificarCasa(Casilla titulo) {
        boolean b = false;

        if (titulo.getNumCasas() < getCasasMax()) {
            if (this.puedoGastar(titulo.getPrecioEdificar())) {
                b = true;
            }
        }

        return b;
    }

    private boolean puedoEdificarHotel(Casilla titulo) {
        boolean b = false;

        if (titulo.getNumHoteles() < getHotelesMax()) {
            if (titulo.getNumCasas() == getCasasPorHotel()) {
                if (this.puedoGastar(titulo.getPrecioEdificar())) {
                    b = true;
                }
            }
        }

        return b;
    }

    boolean puedoGastar(float precio) {
        return precio <= this.getSaldo();
    }

    boolean recibe(float cantidad) {
        Diario.getInstance().ocurreEvento("Jugador " + this.getNombre() + " recibe " + cantidad);
        return this.modificarSaldo(cantidad);
    }

    boolean tieneAlgoQueGestionar() {
        return this.getPropiedades().size() > 0;
    }

    /**
     * @brief Informa del estado completo del jugador
     * @return estado del jugador
     */
    @Override
    public String toString() {
        return "Jugador: " + this.getNombre() + "\n"
                + "Saldo: " + this.getSaldo() + "\n"
                + "Casilla Actual: " + this.getNumCasillaActual();

    }
}
