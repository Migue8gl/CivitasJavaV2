package civitasv2;

public class TestP4 {

    public static void main(String args[]) {
        Jugador jugador = new Jugador("Manolo");
        CasillaCalle titulo = new CasillaCalle("Finca Manoli", 1000, 1000, 1000);
        jugador.getPropiedades().add(titulo);

        System.out.println("---Jugador antes de convertir:");
        System.out.println(jugador.toString());

        JugadorEspeculador especulador = new JugadorEspeculador(jugador);
        System.out.println("\n---Jugador después de convertirse:");
        System.out.println(especulador.toString());
        System.out.println("Propiedad: " + especulador.getPropiedades().get(0).getNombre());
    }
}
