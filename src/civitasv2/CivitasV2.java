package civitasv2;

public class CivitasV2 {

    public static void main(String[] args) {
        int n;
        int jug1 = 0, jug2 = 0, jug3 = 0, jug4 = 0;

        for (int i = 0; i < 100; i++) {
            n = Dado.getInstance().quienEmpieza(4);
            switch (n) {
                case 0:
                    jug1++;
                    break;
                case 1:
                    jug2++;
                    break;
                case 2:
                    jug3++;
                    break;
                case 3:
                    jug4++;
                    break;
            } 
        }
        
        System.out.println("Jugador 1 salió " + jug1 + " veces.");
        System.out.println("Jugador 2 salió " + jug2 + " veces.");
        System.out.println("Jugador 3 salió " + jug3 + " veces.");
        System.out.println("Jugador 4 salió " + jug4 + " veces.");
        
        Dado.getInstance().setDebug(false);
        System.out.println(Dado.getInstance().tirar());
        System.out.println(Dado.getInstance().tirar());
        System.out.println(Dado.getInstance().tirar());
        
        Dado.getInstance().setDebug(true);
        System.out.println(Dado.getInstance().tirar());
        System.out.println(Dado.getInstance().tirar());
        System.out.println(Dado.getInstance().tirar());
        
        System.out.println(Dado.getInstance().getUltimoResultado());
        System.out.println(TipoCasilla.CALLE);
        System.out.println(TipoSorpresa.PAGARCOBRAR);
        System.out.println(EstadoJuego.DESPUES_COMPRAR);
        
        Tablero tablero = new Tablero();
        Casilla c1 = new Casilla("Sala Dos Hermanas", 100, 120, 300);
        Casilla c2 = new Casilla("Sala Cuatro Amigos", 1, 2, 300);
        tablero.añadeCasilla(c1);
        tablero.añadeCasilla(c2);
        Casilla aux1 = tablero.getCasilla(0);
        Casilla aux2 = tablero.getCasilla(1);
        Casilla aux3 = tablero.getCasilla(2);
        System.out.println(aux1.toString());
        System.out.println(aux2.toString());
        System.out.println(aux3.toString());
        
        System.out.println(Diario.getInstance().toString());
    }
}
