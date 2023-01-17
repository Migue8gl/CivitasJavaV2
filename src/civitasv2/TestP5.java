package civitasv2;

import GUI.CapturaNombres;
import GUI.CivitasView;
import controladorCivitas.Controlador;
import java.util.ArrayList;

public class TestP5 {
    public static void main(String args[]) {
        CivitasView cv = new CivitasView();
        CapturaNombres cn = new CapturaNombres(cv, true);
        ArrayList<String> nombres = new ArrayList<>();
        nombres = cn.getNombres();
        CivitasJuego cj = new CivitasJuego(nombres, true);
        Controlador controlador = new Controlador(cj, cv);
        cv.setCivitasJuego(cj);
        controlador.juega();
    }
}
