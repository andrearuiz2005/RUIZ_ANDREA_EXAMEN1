package ruiz_andrea_examen;

public enum TipoPesquero {
    PEZ(200.0),
    CAMARON(500.0),
    LANGOSTA(1000.0);

    public final double precio;

    TipoPesquero(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}
