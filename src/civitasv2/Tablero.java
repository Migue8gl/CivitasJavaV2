package civitasv2;

import java.util.ArrayList;

public class Tablero {

    private ArrayList<Casilla> casillas;
    private boolean porSalida;

    public Tablero() {

        this.casillas = new ArrayList<>();
        Casilla obj = new Casilla("SALIDA");
        this.casillas.add(obj);
        this.porSalida = false;
    }

    private boolean correcto(int numCasilla) {
        boolean correct = false;

        if (this.casillas.size() > numCasilla) {
            correct = true;
        }

        return correct;
    }

    boolean getPorSalida() {
        boolean valorPorSalida = this.porSalida;
        this.porSalida = false;

        return valorPorSalida;
    }

    void añadeCasilla(Casilla casilla) {
        this.casillas.add(casilla);
    }

    Casilla getCasilla(int numCasilla) {
        if (this.correcto(numCasilla)) {
            return this.casillas.get(numCasilla);
        } else {
            return null;
        }
    }

    int nuevaPosicion(int actual, int tirada) {
        int posicion = -1;

        posicion = (actual + tirada) % 20;

        if (posicion != actual + tirada) {
            this.porSalida = true;
        }

        return posicion;
    }

    int calcularTirada(int origen, int destino) {
        int tirada = destino - origen;

        if (tirada < 0) {
            tirada += 20;
        }

        return tirada;
    }
}
