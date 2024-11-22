import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.event.HyperlinkEvent;
import java.net.URI;
public class DesafioFinalV2GUI extends JFrame {
    private JTextField archivoField, terminoField;
    private JComboBox<String> criterioBox;
    private JTextArea resultadosArea;
    private JButton buscarBtn, limpiarBtn;
    
    // Campos para reemplazar encabezados
    private JTextField encabezado1Field, encabezado2Field, encabezado3Field, encabezado4Field;

    private String archivoEntrada = "MOCK_DATA.csv"; // Variable para el archivo

    public DesafioFinalV2GUI() {
        setTitle("Desafío Final V2 - Sistema de Búsqueda");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Para manejar el cierre manualmente
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de Entrada
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        archivoField = new JTextField("MOCK_DATA.csv"); // Archivo predeterminado
        terminoField = new JTextField();
        
        terminoField = new JTextField();
        terminoField = new JTextField();
        terminoField.setPreferredSize(new Dimension(225, 20)); // Ajustar el tamaño del campo
        terminoField.setHorizontalAlignment(JTextField.LEFT); // Alineación del texto

        String[] criterios = {"País", "Ocupación", "Encabezado Específico"};
        criterioBox = new JComboBox<>(criterios);

        // Campos para encabezados
        encabezado1Field = new JTextField("Nombre", 20);
        encabezado2Field = new JTextField("Teléfono", 20);
        encabezado3Field = new JTextField("País", 20);
        encabezado4Field = new JTextField("Ocupación", 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Archivo de Entrada:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(archivoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Criterio de Búsqueda:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(criterioBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Término a Buscar:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(terminoField, gbc);

        // Agregar los encabezados al panel de entrada
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Encabezado 1 (Ej: Nombre):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(encabezado1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Encabezado 2 (Ej:Teléfono):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(encabezado2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Encabezado 3 (Ej:País):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(encabezado3Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Encabezado 4 (Ej:Ocupación):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(encabezado4Field, gbc);

        // Botones
        buscarBtn = new JButton("Buscar");
        limpiarBtn = new JButton("Guardar y Limpiar");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        inputPanel.add(buscarBtn, gbc);
        gbc.gridy = 8;
        inputPanel.add(limpiarBtn, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Área de Resultados
        resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadosArea);
        add(scrollPane, BorderLayout.CENTER);

        // Acción de Búsqueda
        buscarBtn.addActionListener(e -> realizarBusqueda());
        limpiarBtn.addActionListener(e -> guardarYLimpiar());

        // Acción al cerrar el programa
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                mostrarVentanaValoracion(); // Mostrar la ventana de valoración
            }
        });

        crearMenu(); // Crear el menú
    }

    private void realizarBusqueda() {
        String terminoBusqueda = terminoField.getText().toLowerCase();
        int criterioSeleccionado = criterioBox.getSelectedIndex();

        if (archivoEntrada.isEmpty() || terminoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File archivo = new File(archivoEntrada);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String[]> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Ignorar encabezado
            while ((linea = br.readLine()) != null) {
                registros.add(linea.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String[]> resultados = new ArrayList<>();
        for (String[] registro : registros) {
            boolean coincide = false;
            switch (criterioSeleccionado) {
                case 0: // País
                    if (registro[2].toLowerCase().contains(terminoBusqueda)) coincide = true;
                    break;
                case 1: // Ocupación
                    if (registro[3].toLowerCase().contains(terminoBusqueda)) coincide = true;
                    break;
                case 2: // Encabezado Específico
                    for (String campo : registro) {
                        if (campo.toLowerCase().contains(terminoBusqueda)) {
                            coincide = true;
                            break;
                        }
                    }
                    break;
            }
            if (coincide) {
                resultados.add(registro);
            }
        }

        mostrarResultados(resultados);
    }

    private void mostrarResultados(List<String[]> resultados) {
        resultadosArea.setText("");
        if (resultados.isEmpty()) {
            resultadosArea.append("No se encontraron resultados.");
        } else {
            for (String[] registro : resultados) {
                resultadosArea.append(String.join(", ", registro) + "\n");
            }
        }
    }

    private void guardarYLimpiar() {
        String resultados = resultadosArea.getText();
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay resultados para guardar.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Obtener los encabezados
        String encabezado1 = encabezado1Field.getText();
        String encabezado2 = encabezado2Field.getText();
        String encabezado3 = encabezado3Field.getText();
        String encabezado4 = encabezado4Field.getText();

        // Guardar encabezados
        String termino = terminoField.getText().trim().replaceAll("[^a-zA-Z0-9]", "_");
        String nombreArchivo = termino.isEmpty()
                ? "resultados_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv"
                : "resultados_" + termino + ".csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write(encabezado1 + "," + encabezado2 + "," + encabezado3 + "," + encabezado4);
            bw.newLine();
            bw.write(resultados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los resultados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, "Resultados guardados en '" + nombreArchivo + "'", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    private void limpiarCampos() {
        archivoField.setText("MOCK_DATA.csv"); // Restablecer el archivo predeterminado
        terminoField.setText("");
        resultadosArea.setText("");
    }

    private void mostrarVentanaValoracion() {
        JDialog dialogo = new JDialog(this, "Valorar el Programa", true);
        dialogo.setSize(300, 200);
        dialogo.setLayout(new BorderLayout());
        dialogo.setLocationRelativeTo(this);

        JLabel etiqueta = new JLabel("¿Qué calificación le das al programa?", SwingConstants.CENTER);
        dialogo.add(etiqueta, BorderLayout.NORTH);

        JPanel panelEstrellas = new JPanel(new FlowLayout());
        ButtonGroup grupoEstrellas = new ButtonGroup();
        JRadioButton[] estrellas = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            estrellas[i] = new JRadioButton((i + 1) + " ★");
            grupoEstrellas.add(estrellas[i]);
            panelEstrellas.add(estrellas[i]);
        }
        dialogo.add(panelEstrellas, BorderLayout.CENTER);

        JButton botonCerrar = new JButton("Enviar y Cerrar");
        botonCerrar.addActionListener(e -> {
            int seleccion = -1;
            for (int i = 0; i < 5; i++) {
                if (estrellas[i].isSelected()) {
                    seleccion = i + 1;
                    break;
                }
            }
            if (seleccion != -1) {
                JOptionPane.showMessageDialog(this, "Gracias por valorar con " + seleccion + " estrella(s).");
            } else {
                JOptionPane.showMessageDialog(this, "No seleccionaste ninguna valoración.");
            }
            System.exit(0);
        });
        dialogo.add(botonCerrar, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú de Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem seleccionarArchivoItem = new JMenuItem("Seleccionar Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");

        seleccionarArchivoItem.addActionListener(e -> {
            archivoEntrada = seleccionarArchivo();
            if (archivoEntrada != null) {
                JOptionPane.showMessageDialog(this, "Archivo cargado con éxito.");
            }
        });

        salirItem.addActionListener(e -> System.exit(0));

        archivoMenu.add(seleccionarArchivoItem);
        archivoMenu.add(salirItem);
        menuBar.add(archivoMenu);

        // Menú de Ayuda
        JMenu AcercadeMenu = new JMenu("Acerca de");
        JMenuItem acercaDeItem = new JMenuItem("Creadores");

        acercaDeItem.addActionListener(e -> mostrarAcercaDe());
        AcercadeMenu.add(acercaDeItem);
        menuBar.add(AcercadeMenu);

        // Establecer el menú en la ventana
        setJMenuBar(menuBar);
    }

    private String seleccionarArchivo() {
        JFileChooser archivoChooser = new JFileChooser();
        int seleccion = archivoChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            return archivoChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    private void mostrarAcercaDe() {
        // Crear un JEditorPane para mostrar HTML con los enlaces
        JEditorPane editorPane = new JEditorPane("text/html", "<html><body>Creadores:<br><a href='https://github.com/NestorG25'>https://github.com/NestorG25</a><br><a href='https://github.com/MatyAu'>https://github.com/MatyAu</a></body></html>");
        editorPane.setBackground(UIManager.getColor("Panel.background"));
        editorPane.setEditable(false); // Desactivar la edición del contenido
    
        // Agregar un listener para los enlaces
        editorPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        // Abrir la URL en el navegador predeterminado
                        Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    
        // Crear un JOptionPane para mostrar el JEditorPane
        JOptionPane.showMessageDialog(this, editorPane, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DesafioFinalV2GUI ventana = new DesafioFinalV2GUI();
            ventana.setVisible(true);
        });
    }
}
