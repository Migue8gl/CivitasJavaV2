package GUI;

import civitasv2.Casilla;
import civitasv2.CivitasJuego;
import civitasv2.Diario;
import civitasv2.OperacionJuego;
import controladorCivitas.Respuesta;
import civitasv2.OperacionInmobiliaria;
import civitasv2.Jugador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VistaTextual implements Vista {

    private static String separador = "=====================";

    private Scanner in;

    CivitasJuego juegoModel;

    public VistaTextual(CivitasJuego juegoModel) {
        in = new Scanner(System.in);
        this.juegoModel = juegoModel;
    }

    public void pausa() {
        System.out.print("\nPulsa una tecla");
        in.nextLine();
    }

    int leeEntero(int max, String msg1, String msg2) {
        Boolean ok;
        String cadena;
        int numero = -1;
        do {
            System.out.print(msg1);
            cadena = in.nextLine();
            try {
                numero = Integer.parseInt(cadena);
                ok = true;
            } catch (NumberFormatException e) { // No se ha introducido un entero
                System.out.println(msg2);
                ok = false;
            }
            if (ok && (numero < 0 || numero >= max)) {
                System.out.println(msg2);
                ok = false;
            }
        } while (!ok);

        return numero;
    }

    int menu(String titulo, ArrayList<String> lista) {
        String tab = "  ";
        int opcion;
        System.out.println(titulo);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(tab + i + "-" + lista.get(i));
        }

        opcion = leeEntero(lista.size(),
                "\n" + tab + "Elige una opción: ",
                tab + "Valor erróneo");
        return opcion;
    }

    public void actualiza() {
        System.out.println("--- INFO JUGADOR ---");
        System.out.println(this.juegoModel.getJugadorActual().toString());

        System.out.println("--- INFO CASILLA ---");
        System.out.println(this.juegoModel.getCasillaActual().toString());
    }

    public Respuesta comprar() {
        int opcion = this.menu("¿Compras esta propiedad? ",
                new ArrayList<>(Arrays.asList("SI", "NO")));
        return (Respuesta.values()[opcion]);
    }

    public OperacionInmobiliaria elegirOperacion() {
        int opcion = this.menu("¿Qué número de gestión inmobiliaria elige? ",
                new ArrayList<>(Arrays.asList("CONSTRUIR_CASA", "CONSTRUIR_HOTEL", "TERMINAR")));
        return (OperacionInmobiliaria.values()[opcion]);
    }

    public int elegirPropiedad() {
        ArrayList<Casilla> titulos = new ArrayList<>(this.juegoModel.getJugadorActual().getPropiedades());
        ArrayList<String> nombres = new ArrayList<>();
        
        for (int i = 0; i < titulos.size(); i++) {
            nombres.add(titulos.get(i).toString());
        }
        
        int opcion = menu("¿Con que propiedad desea realizar la gestión? ",
                nombres);
        boolean existe = this.juegoModel.getJugadorActual().existeLaPropiedad(opcion);

        while (!existe) {
            opcion = menu("El índice de propiedad es incorrecto. ¿Con que propiedad desea realizar la gestión? ",
                    nombres);
            existe = this.juegoModel.getJugadorActual().existeLaPropiedad(opcion);
        }
        return opcion;
    }
    
    public void mostrarSiguienteOperacion(OperacionJuego operacion) {
        String nombreoperacion = "";
        
        switch (operacion) {
            case AVANZAR:
                nombreoperacion = "avanzar";
                break;
            case COMPRAR:
                nombreoperacion = "comprar";
                break;
            case GESTIONAR:
                nombreoperacion = "gestionar";
                break;
            case PASAR_TURNO:
                nombreoperacion = "pasar turno";
                break;
        }
        System.out.println("La siguiente operacion es " + nombreoperacion + ".");
    }
    
    public void mostrarEventos() {
        while (Diario.getInstance().eventosPendientes()) {
            System.out.println(Diario.getInstance().leerEvento());
        }
    }
}
