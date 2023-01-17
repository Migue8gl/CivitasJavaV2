package civitasv2;

public class JugadorEspeculador extends Jugador {

    private static int FactorEspeculador = 2;

    public JugadorEspeculador(Jugador jug) {
        super(jug);
    }

    @Override
    protected int getCasasMax() {
        return FactorEspeculador * super.getCasasMax();
    }

    @Override
    protected int getHotelesMax() {
        return FactorEspeculador * super.getHotelesMax();
    }
    
    public boolean esEspeculador() {
        return true;
    }

    @Override
    boolean paga(float cantidad) {
        return super.paga(cantidad / 2);
    }

    @Override
    public String toString() {
        return "Jugador Especular: " + this.getNombre() + "\n"
                + "Saldo: " + this.getSaldo() + "\n"
                + "Casilla Actual: " + this.getNumCasillaActual();

    }

}