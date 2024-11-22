# SistemaDeBusquedaGUI
Ejemplo: Sistema De Búsqueda de Archivos GUI (App Dev) Java y Framework Java Swing <br/>
Flujo General:<br/>
Se carga la ventana con un formulario y un área de resultados.<br/>
El usuario puede buscar en un archivo CSV seleccionando un criterio y proporcionando un término.<br/>
Los resultados se muestran en pantalla y pueden guardarse en un archivo nuevo.<br/>
El usuario puede cerrar el programa después de calificarlo.<br/>

Este código implementa una aplicación gráfica en Java utilizando Swing.<br/>
La aplicación permite buscar registros en un archivo CSV basado en criterios específicos y realizar operaciones adicionales como guardar los resultados o ajustar encabezados.<br/>
Aquí tienes una explicación detallada paso a paso:<br/>

1. Importación de Bibliotecas.<br/>
javax.swing.* y java.awt.*: Para crear la interfaz gráfica.<br/>
java.io.*: Para manejar operaciones de lectura y escritura de archivos.<br/>
java.text.SimpleDateFormat: Para dar formato a las fechas al guardar archivos.<br/>
java.util.*: Para manejar listas y otros utilitarios.<br/>
javax.swing.event.HyperlinkEvent y java.net.URI: Para manejar enlaces interactivos en la ventana "Acerca de".<br/>

2. Clase Principal.<br/>
La clase DesafioFinalV2GUI hereda de JFrame, lo que la convierte en una ventana principal de la aplicación.<br/>

3. Atributos de la Clase.<br/>
-Campos de texto (JTextField): Para entrada de datos (nombre del archivo, términos de búsqueda, y encabezados).<br/>
-ComboBox (JComboBox): Permite seleccionar un criterio de búsqueda.<br/>
-Área de texto (JTextArea): Muestra los resultados de las búsquedas.<br/>
-Botones (JButton): Realizan acciones como buscar o limpiar campos.<br/>
-String archivoEntrada: Nombre del archivo por defecto (MOCK_DATA.csv).<br/>

4. Constructor.<br/>
El constructor inicializa la ventana y configura la interfaz gráfica:<br/>
/-Configuración de la ventana-/<br/>
Título: "Desafío Final V2 - Sistema de Búsqueda".<br/>
Tamaño: 600x500 píxeles.<br/>
Layout: BorderLayout para organizar componentes en áreas específicas (Norte, Centro, etc.).<br/>

Panel de Entrada: (Norte)<br/>
-Se utiliza un JPanel con GridBagLayout para crear un formulario con campos de entrada:<br/>
-Campo de texto para especificar el archivo CSV.<br/>
-ComboBox para seleccionar criterios de búsqueda: "País", "Ocupación" o "Encabezado Específico".<br/>
-Campos de texto para ingresar encabezados personalizados.<br/>
-Botones Buscar y Guardar y Limpiar.<br/>

Área de Resultados: (Centro)<br/>
-JTextArea muestra los resultados de la búsqueda.<br/>
-Se incluye dentro de un JScrollPane para manejar scroll si hay muchos resultados.<br/>

5. Acciones de los Componentes:<br/>
Botón "Buscar":<br/>
Llama al método realizarBusqueda, que:<br/>
-Verifica si el archivo y el término de búsqueda son válidos.<br/>
-Lee el archivo CSV línea por línea y almacena los registros.<br/>
-Filtra los registros basados en el criterio seleccionado.<br/>
-Muestra los resultados en el área de texto. (JTextArea)<br/>

Botón "Guardar y Limpiar":<br/>
-Llama al método guardarYLimpiar, que:<br/>
-Guarda los resultados en un nuevo archivo CSV.<br/>
-Usa los encabezados personalizados.<br/>
-Limpia los campos de texto.<br/>

Al cerrar la ventana: (X)<br/>
Sobrescribe el evento de cierre de ventana (windowClosing).<br/>
Llama a mostrarVentanaValoracion, que:<br/>
Muestra un cuadro de diálogo con opciones de calificación (1-5 estrellas).<br/>
Finaliza el programa al cerrar la ventana.<br/>

Barra Menú:<br/>
Archivo: Permite seleccionar un archivo o salir del programa.<br/>
Acerca de: Muestra un cuadro con enlaces a los perfiles de los creadores.<br/>

6. Métodos principales con excepciones para crear el menu<br/>

realizarBusqueda:<br/>
Filtra el contenido del archivo basado en el criterio de búsqueda.<br/>
Los resultados se almacenan en una lista y se muestran en el área de texto.<br/>

mostrarResultados:<br/>
Muestra los resultados formateados en el área de texto.<br/>
Si no hay resultados, muestra un mensaje adecuado.<br/>

guardarYLimpiar:<br/>
Guarda los resultados en un archivo CSV con un nombre basado en el término de búsqueda o la fecha.<br/>
Limpia todos los campos y la área de texto.<br/>

limpiarCampos:<br/>
Restablecer un archivo predeterminado. (archivoField.setText("MOCK_DATA.csv"))<br/>

mostrarVentanaValoracion:<br/>
Muestra un cuadro de diálogo para calificar el programa antes de salir.<br/>

crearMenu:<br/>
Configura la barra de menú con opciones de archivo y ayuda.<br/>

seleccionarArchivo:<br/>
Muestra un cuadro de busqueda de archivos a traves de un explorador. (JFileChooser)<br/>

mostrarAcercaDe:<br/>
Muestra un cuadro con enlaces clicables hacia los perfiles de los desarrolladores. (JEditorPane)<br/>

7. Método Main<br/>
Usa SwingUtilities.invokeLater para asegurar que la GUI se ejecute en el hilo adecuado.<br/>
Instancia y muestra la ventana principal.<br/>
