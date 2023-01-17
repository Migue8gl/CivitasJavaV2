package GUI;


import civitasv2.OperacionInmobiliaria;
import civitasv2.OperacionJuego;
import controladorCivitas.Respuesta;

public interface Vista {

    public void actualiza();

    public void pausa();

    public Respuesta comprar();

    public OperacionInmobiliaria elegirOperacion();

    public int elegirPropiedad();

    public void mostrarSiguienteOperacion(OperacionJuego operación);

    public void mostrarEventos();

}
