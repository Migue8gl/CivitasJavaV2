package civitasv2;

enum EstadosJuego {
  INICIO_TURNO,
  DESPUES_CARCEL,
  DESPUES_AVANZAR,
  DESPUES_COMPRAR,
  DESPUES_GESTIONAR
}

enum TipoCasilla {
    CALLE,
    SORPRESA,
    DESCANSO
}

enum TipoSorpresa {
    PAGARCOBRAR,
    PORCASAHOTEL
}