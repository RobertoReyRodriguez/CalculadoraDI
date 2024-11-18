import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase principal para la interfaz gráfica de la calculadora.
 * Proporciona la lógica de la interfaz para realizar operaciones básicas
 * de suma, resta, multiplicación y división.
 * Extiende JFrame para crear la ventana principal de la aplicación.
 */
public class CalculadoraGUI extends JFrame implements ActionListener {
    private Calculadora calculadora;

    private JTextField pantallaEntrada;
    private JLabel pantallaResultado;

    private StringBuilder entrada;

    /**
     * Constructor de la clase CalculadoraGUI.
     * Inicializa la interfaz de usuario y los componentes de la calculadora.
     */
    public CalculadoraGUI() {
        calculadora = new Calculadora();
        entrada = new StringBuilder();
        
        setTitle("Calculadora Rober");  
        setSize(400, 600);  
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pantallaEntrada = new JTextField();
        pantallaEntrada.setEditable(false);
        pantallaEntrada.setHorizontalAlignment(JTextField.RIGHT);
        pantallaResultado = new JLabel("0", SwingConstants.RIGHT);

        JPanel panelPantalla = new JPanel(new GridLayout(2, 1));
        panelPantalla.add(pantallaEntrada);
        panelPantalla.add(pantallaResultado);

        JPanel panelNumeros = new JPanel(new GridLayout(4, 3));
        for (int i = 1; i <= 9; i++) {
            agregarBoton(panelNumeros, String.valueOf(i));
        }
        agregarBoton(panelNumeros, "0");
        agregarBoton(panelNumeros, ".");

        JPanel panelOperaciones = new JPanel(new GridLayout(5, 1));
        agregarBoton(panelOperaciones, "+");
        agregarBoton(panelOperaciones, "-");
        agregarBoton(panelOperaciones, "*");
        agregarBoton(panelOperaciones, "/");
        agregarBoton(panelOperaciones, "=");

        JPanel panelBotonLimpieza = new JPanel(new FlowLayout());
        agregarBoton(panelBotonLimpieza, "C");

        add(panelPantalla, BorderLayout.NORTH);
        add(panelNumeros, BorderLayout.CENTER);
        add(panelOperaciones, BorderLayout.EAST);
        add(panelBotonLimpieza, BorderLayout.SOUTH);
    }

    /**
     * Método para agregar un botón al panel especificado.
     * 
     * @param panel El panel al que se agregará el botón.
     * @param nombre El nombre que se mostrará en el botón.
     */
    private void agregarBoton(JPanel panel, String nombre) {
        JButton boton = new JButton(nombre);
        boton.addActionListener(this);
        panel.add(boton);
    }

    /**
     * Maneja los eventos de acción de los botones.
     * 
     * @param e El evento de acción generado por los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        switch (input) {
            case "+":
            case "-":
            case "*":
            case "/":
                calculadora.setOperando1(Double.parseDouble(pantallaResultado.getText()));
                calculadora.setOperacion(input);
                entrada.setLength(0);  
                break;
            case "=":
                calculadora.setOperando2(Double.parseDouble(pantallaResultado.getText()));
                try {
                    double resultado = calculadora.calcular();
                    pantallaResultado.setText(String.valueOf(resultado));
                } catch (ArithmeticException ex) {
                    pantallaResultado.setText("Error");
                }
                entrada.setLength(0);  
                break;
            case "C":
                pantallaResultado.setText("0");
                entrada.setLength(0);
                break;
            case ".":
                if (!entrada.toString().contains(".")) {
                    entrada.append(".");
                    pantallaResultado.setText(entrada.toString());
                }
                break;
            default:  
                entrada.append(input);
                pantallaResultado.setText(entrada.toString());
                break;
        }
    }

    /**
     * Método principal para iniciar la aplicación de la calculadora.
     * 
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraGUI calculadoraGUI = new CalculadoraGUI();
            calculadoraGUI.setVisible(true);
        });
    }
}