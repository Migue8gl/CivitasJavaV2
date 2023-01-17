package civitasv2;

import java.util.Random;

public class Dado {

    private Random random;
    private int ultimoResultado;
    private boolean debug;
    static final private Dado instance = new Dado();
    
    private Dado() {
        this.ultimoResultado = -1;
        this.debug = false;
        this.random = new Random();
    }

    static public Dado getInstance() {
        return Dado.instance;
    }

    public int tirar() {
        if (!debug) {
            ultimoResultado = random.nextInt(6) + 1;
            return ultimoResultado;
        } 
        else
            return 1;
    }
    
    public int quienEmpieza(int n) {
        int jug = this.random.nextInt(n);
        
        return jug;
    }
    
    public void setDebug(boolean d) {
        this.debug = d;
        Diario.getInstance().ocurreEvento("Debug mode: " + this.debug);
    }
    
    public int getUltimoResultado() {
        return this.ultimoResultado;
    }
    
    public boolean getDebug() {
        return debug;
    }

}