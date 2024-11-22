# SistemaDeBusquedaGUI
Ejemplo: Sistema De Búsqueda de Archivos GUI (App Dev) Java y Framework Java Swing
Flujo General:
Se carga la ventana con un formulario y un área de resultados.
El usuario puede buscar en un archivo CSV seleccionando un criterio y proporcionando un término.
Los resultados se muestran en pantalla y pueden guardarse en un archivo nuevo.
El usuario puede cerrar el programa después de calificarlo.

Este código implementa una aplicación gráfica en Java utilizando Swing. 
La aplicación permite buscar registros en un archivo CSV basado en criterios específicos y realizar operaciones adicionales como guardar los resultados o ajustar encabezados.
Aquí tienes una explicación detallada paso a paso:

1. Importación de Bibliotecas
javax.swing.* y java.awt.*: Para crear la interfaz gráfica.
java.io.*: Para manejar operaciones de lectura y escritura de archivos.
java.text.SimpleDateFormat: Para dar formato a las fechas al guardar archivos.
java.util.*: Para manejar listas y otros utilitarios.
javax.swing.event.HyperlinkEvent y java.net.URI: Para manejar enlaces interactivos en la ventana "Acerca de".

2. Clase Principal 
La clase DesafioFinalV2GUI hereda de JFrame, lo que la convierte en una ventana principal de la aplicación.

3. Atributos de la Clase
-Campos de texto (JTextField): Para entrada de datos (nombre del archivo, términos de búsqueda, y encabezados).<br/>
-ComboBox (JComboBox): Permite seleccionar un criterio de búsqueda.<br/>
-Área de texto (JTextArea): Muestra los resultados de las búsquedas.<br/>
-Botones (JButton): Realizan acciones como buscar o limpiar campos.<br/>
-String archivoEntrada: Nombre del archivo por defecto (MOCK_DATA.csv).<br/>

4. Constructor
El constructor inicializa la ventana y configura la interfaz gráfica: Configuración de la ventana.
Título: "Desafío Final V2 - Sistema de Búsqueda".
Tamaño: 600x500 píxeles.
Layout: BorderLayout para organizar componentes en áreas específicas (Norte, Centro, etc.).

Panel de Entrada: (Norte)
-Se utiliza un JPanel con GridBagLayout para crear un formulario con campos de entrada:
-Campo de texto para especificar el archivo CSV.
-ComboBox para seleccionar criterios de búsqueda: "País", "Ocupación" o "Encabezado Específico".
-Campos de texto para ingresar encabezados personalizados.
-Botones Buscar y Guardar y Limpiar.

Área de Resultados: (Centro)
-JTextArea muestra los resultados de la búsqueda.
-Se incluye dentro de un JScrollPane para manejar scroll si hay muchos resultados.

5. Acciones de los Componentes:
Botón "Buscar":
Llama al método realizarBusqueda, que:
-Verifica si el archivo y el término de búsqueda son válidos.
-Lee el archivo CSV línea por línea y almacena los registros.
-Filtra los registros basados en el criterio seleccionado.
-Muestra los resultados en el área de texto. (JTextArea)

Botón "Guardar y Limpiar":
-Llama al método guardarYLimpiar, que:
-Guarda los resultados en un nuevo archivo CSV.
-Usa los encabezados personalizados.
-Limpia los campos de texto.

Al cerrar la ventana: (X)
Sobrescribe el evento de cierre de ventana (windowClosing).
Llama a mostrarVentanaValoracion, que:
Muestra un cuadro de diálogo con opciones de calificación (1-5 estrellas).
Finaliza el programa al cerrar la ventana.

Barra Menú:
Archivo: Permite seleccionar un archivo o salir del programa.
Acerca de: Muestra un cuadro con enlaces a los perfiles de los creadores.

6. Métodos principales con excepciones para crear el menu

realizarBusqueda:
Filtra el contenido del archivo basado en el criterio de búsqueda.
Los resultados se almacenan en una lista y se muestran en el área de texto.

mostrarResultados:
Muestra los resultados formateados en el área de texto.
Si no hay resultados, muestra un mensaje adecuado.

guardarYLimpiar:
Guarda los resultados en un archivo CSV con un nombre basado en el término de búsqueda o la fecha.
Limpia todos los campos y la área de texto.

limpiarCampos:
Restablecer un archivo predeterminado. (archivoField.setText("MOCK_DATA.csv"))

mostrarVentanaValoracion:
Muestra un cuadro de diálogo para calificar el programa antes de salir.

crearMenu:
Configura la barra de menú con opciones de archivo y ayuda.

seleccionarArchivo:
Muestra un cuadro de busqueda de archivos a traves de un explorador. (JFileChooser)

mostrarAcercaDe:
Muestra un cuadro con enlaces clicables hacia los perfiles de los desarrolladores. (JEditorPane)

7. Método Main
Usa SwingUtilities.invokeLater para asegurar que la GUI se ejecute en el hilo adecuado.
Instancia y muestra la ventana principal.
