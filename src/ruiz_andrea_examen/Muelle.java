package ruiz_andrea_examen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class Muelle extends JFrame {
    private final ArrayList<Barco> barcos;

    public Muelle() {
        barcos = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Gestion de Muelles Andy");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestion de Muelles Andy", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setBackground(Color.WHITE);

        JButton btnAgregarBarco = crearBoton("Agregar Barco");
        btnAgregarBarco.addActionListener(e -> agregarBarco());

        JButton btnAgregarElemento = crearBoton("Agregar Elemento a Barco");
        btnAgregarElemento.addActionListener(e -> agregarElemento());

        JButton btnVaciarBarco = crearBoton("Vaciar y Cobrar Barco");
        btnVaciarBarco.addActionListener(e -> vaciarBarco());

        JButton btnBarcosDesde = crearBoton("Listar Barcos Desde Año");
        btnBarcosDesde.addActionListener(e -> listarBarcosDesde());

        JButton btnSalir = crearBoton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnAgregarBarco);
        panel.add(btnAgregarElemento);
        panel.add(btnVaciarBarco);
        panel.add(btnBarcosDesde);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(120, 30));
        boton.setBackground(new Color(255, 0, 0));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder());
        return boton;
    }

    private void agregarBarco() {
        String tipo = JOptionPane.showInputDialog(this, "Tipo de barco (PESQUERO/PASAJERO):");
        String nombre = JOptionPane.showInputDialog(this, "Nombre del barco:");
        

        if (buscarBarco(nombre).isPresent()) {
            JOptionPane.showMessageDialog(this, "El nombre del barco ya existe.");
            return;
        }

        if ("PESQUERO".equalsIgnoreCase(tipo)) {
            TipoPesquero tipoPesquero = (TipoPesquero) JOptionPane.showInputDialog(this,
                    "Seleccione el tipo de pesquero:",
                    "Tipo de Pesquero",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    TipoPesquero.values(),
                    TipoPesquero.PEZ);
            barcos.add(new BarcoPesquero(nombre, tipoPesquero));
        } else if ("PASAJERO".equalsIgnoreCase(tipo)) {
            int maxPasajeros = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad de pasajeros:"));
            double precioBoleto = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio del boleto:"));

            barcos.add(new BarcoPasajero(nombre, maxPasajeros, precioBoleto));
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de barco no válido.");
        }
    }

    private void agregarElemento() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del barco:");
        Optional<Barco> barco = buscarBarco(nombre);

        barco.ifPresentOrElse(
                Barco::agregarElemento,
                () -> JOptionPane.showMessageDialog(this, "Barco no encontrado.")
        );
    }

    private void vaciarBarco() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del barco:");
        Optional<Barco> barco = buscarBarco(nombre);

        barco.ifPresentOrElse(b -> {
            double totalCobrado = b.vaciarCobrar();
            JOptionPane.showMessageDialog(this, "Total cobrado: $" + totalCobrado);
            if (b instanceof BarcoPasajero) {
                ((BarcoPasajero) b).listarPasajeros();
            }
        }, () -> JOptionPane.showMessageDialog(this, "Barco no encontrado."));
    }

    private void listarBarcosDesde() {
        int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Año:"));
        StringBuilder resultado = new StringBuilder("Barcos desde " + year + ":\n");
        listarBarcosRecursivo(0, year, resultado);

        if (resultado.length() == ("Barcos desde " + year + ":\n").length()) {
            resultado.append("No se encontraron barcos.");
        }

        JOptionPane.showMessageDialog(this, resultado.toString());
    }

    private void listarBarcosRecursivo(int indice, int year, StringBuilder resultado) {
        if (indice < barcos.size()) {
            Barco barco = barcos.get(indice);
            if (barco.getFechaCirculacion().getYear() >= year) {
                resultado.append(barco.getNombre()).append(" - ").append(barco.getFechaCirculacion()).append("\n");
            }
            listarBarcosRecursivo(indice + 1, year, resultado);
        }
    }

    private Optional<Barco> buscarBarco(String nombre) {
        return barcos.stream().filter(b -> b.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Muelle ex = new Muelle();
            ex.setVisible(true);
        });
    }
}