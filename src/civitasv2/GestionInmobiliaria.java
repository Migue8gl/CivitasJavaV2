package civitasv2;

public class GestionInmobiliaria {
    
    private int numPropiedad;
    private OperacionInmobiliaria gestion;
    
    public GestionInmobiliaria(OperacionInmobiliaria gest, int ip) {
        this.gestion = gest;
        this.numPropiedad = ip;
    }
    
    public OperacionInmobiliaria getGestion() {
        return this.gestion;
    }
    
    public int getNumPropiedad() {
        return this.numPropiedad;
    }
}