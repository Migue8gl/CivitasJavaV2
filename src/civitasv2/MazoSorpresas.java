package civitasv2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazoSorpresas {

    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;

    void init() {
        this.sorpresas = new ArrayList<>();
        this.barajada = false;
        this.usadas = 0;
    }

    MazoSorpresas(boolean d) {
        this.debug = d;
        this.init();
        if (this.debug == true) {
            Diario.getInstance().ocurreEvento("Se ha creado el mazo de sorpresas");
        }
    }

    MazoSorpresas() {
        this.init();
        this.debug = false;
    }

    void alMazo(Sorpresa s) {
        if (!this.barajada) {
            this.sorpresas.add(s);
        }
    }

    Sorpresa siguiente() {
        if (!this.barajada || this.usadas == this.sorpresas.size()) {
            if (!this.debug) {
                Collections.shuffle(this.sorpresas);
                this.usadas = 0;
                this.barajada = true;
            }
        }
        this.usadas++;
        Sorpresa sorpresa = this.sorpresas.get(0);
        this.sorpresas.remove(0);
        this.sorpresas.add(sorpresa);
        return sorpresa;
    }
}
