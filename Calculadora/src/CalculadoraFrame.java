import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculadoraFrame extends JFrame {
    private JTextField display;
    private double resultado = 0;
    private double ultimoResultado = 0;
    private boolean hayUltimoResultado = false;
    private String operacionPendiente = "";

    public CalculadoraFrame() {
        super("Calculadora");

        // Configuración del frame
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY);

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(500, 300));
        Font font = new Font("Arial", Font.BOLD, 80);
        display.setFont(font);
        add(display, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 4));
        String[] nombresBotones = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                ".", "0", "=", "+",
                "C"
        };

        for (String nombre : nombresBotones) {
            JButton btn = new JButton(nombre);
            btn.addActionListener(new BotonListener());
            btn.setBackground(Color.BLACK);
            panelBotones.add(btn);
        }

        panelBotones.setBackground(Color.GRAY);
        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }

    private class BotonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String botonPulsado = e.getActionCommand();

            switch (botonPulsado) {
                case "+":
                case "-":
                case "*":
                case "/":
                    operacionPendiente = botonPulsado;
                    if (!display.getText().isEmpty()) {
                        resultado = Double.parseDouble(display.getText());
                    }
                    display.setText("");
                    break;
                case "=":
                    if (!display.getText().isEmpty()) {
                        double numero = Double.parseDouble(display.getText());
                        switch (operacionPendiente) {
                            case "+":
                                resultado += numero;
                                break;
                            case "-":
                                resultado -= numero;
                                break;
                            case "*":
                                resultado *= numero;
                                break;
                            case "/":
                                if (numero != 0) {
                                    resultado /= numero;
                                } else {
                                    display.setText("Error");
                                }
                                break;
                        }

                        DecimalFormat df = new DecimalFormat("#.######"); // (#) hasta 6 decimales
                        String resultadoFormateado = df.format(resultado);
                        if (resultado % 1 == 0) {
                            resultadoFormateado = String.valueOf((int) resultado); // Convierte el resultado a entero
                        }
                        display.setText(resultadoFormateado);
                        ultimoResultado = resultado;
                        hayUltimoResultado = true;
                    }
                    break;
                case "C":
                    display.setText("");
                    break;
                case ".":
                    if (!display.getText().contains(".")) {
                        display.setText(display.getText() + ".");
                    }
                    break;
                default:
                    display.setText(display.getText() + botonPulsado);
                    break;
            }
        }
    }
}
