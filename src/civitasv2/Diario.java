package civitasv2;

import java.util.ArrayList;

public class Diario {
  static final private Diario instance = new Diario();
  
  private final ArrayList<String> eventos;
  
  static public Diario getInstance() {
    return Diario.instance;
  }

    public ArrayList<String> getEventos() {
        return this.eventos;
    }
  
  
  
  private Diario () {
    this.eventos = new ArrayList<>();
  }
  
  void ocurreEvento (String e) {
    this.eventos.add (e);
  }
  
  public boolean eventosPendientes () {
    return !this.eventos.isEmpty();
  }
  
  public String leerEvento () {
    String salida = "";
    if (!this.eventos.isEmpty()) {
      salida = this.eventos.remove(0);
    }
    return salida;
  }
}
