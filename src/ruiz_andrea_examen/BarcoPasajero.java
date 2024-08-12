package ruiz_andrea_examen;
import java.util.Scanner;

public final class BarcoPasajero extends Barco {
    private final String[] pasajeros;
    private final double precioBoleto;
    private int contadorPasajeros;

    public BarcoPasajero(String nombre, int maxPasajeros, double precioBoleto) {
        super(nombre);
        this.pasajeros = new String[maxPasajeros];
        this.precioBoleto = precioBoleto;
        this.contadorPasajeros = 0;
    }

    @Override
    public void agregarElemento() {
        if (contadorPasajeros < pasajeros.length) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nombre del pasajero: ");
            pasajeros[contadorPasajeros] = scanner.nextLine();
            contadorPasajeros++;
        } else {
            System.out.println("No hay espacio para más pasajeros.");
        }
    }

    @Override
    public double vaciarCobrar() {
        double total = contadorPasajeros * precioBoleto;
        contadorPasajeros = 0;
        return total;
    }

    @Override
    public double precioElemento() {
        return precioBoleto;
    }

    @Override
    public String toString() {
        return super.toString() + " (Pasajero, Cantidad de Pasajeros que compraron boleto: " + contadorPasajeros + ")";
    }

    public void listarPasajeros() {
        listarPasajerosRecursivo(0);
    }

    private void listarPasajerosRecursivo(int indice) {
        if (indice < contadorPasajeros) {
            System.out.println(pasajeros[indice]);
            listarPasajerosRecursivo(indice + 1);
        }
    }
}
