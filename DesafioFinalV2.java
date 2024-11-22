import java.io.*;
import java.util.*;

public class DesafioFinalV2 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            // 1) El programa primero le pide al usuario que ingrese el nombre del archivo de entrada.
            System.out.print("Ingrese el nombre del archivo (incluyendo la extensión): ");
            String archivoEntrada = scanner.nextLine();

            // 2) Luego lee el archivo completo, creando un ArrayList dividiendo cada línea en registros individuales.
            List<String[]> registros = new ArrayList<>();
            String[] encabezados = null;
            try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
                String linea;
                if ((linea = br.readLine()) != null) {
                    // La primera línea se toma como encabezados
                    encabezados = linea.split(",");
                }
                // Leer las demás líneas (los registros de datos)
                while ((linea = br.readLine()) != null) {
                    registros.add(linea.split(","));
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
                return;
            }

            // 3) Después de leer el archivo, el programa presenta al usuario un menú de opciones para elegir sus criterios de búsqueda: puede buscar por ciudad o por ocupación o por un encabezado específico
            System.out.println("Seleccione el criterio de búsqueda:");
            System.out.println("1. Buscar por País");
            System.out.println("2. Buscar por Ocupación");
            System.out.println("3. Buscar por Encabezado específico");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea después de la opción

            // 4) Una vez que el usuario selecciona su tipo de búsqueda (ciudad u ocupación), se le solicita que ingrese un término de búsqueda. 
            String terminoBusqueda = "";
            List<String> criteriosBusqueda = new ArrayList<>();
            int columnaBusqueda = -1;  // Variable para almacenar el índice de la columna a buscar

            if (opcion == 1) {
                System.out.print("Ingrese el país a buscar: ");
                terminoBusqueda = scanner.nextLine().toLowerCase();
                criteriosBusqueda.add("pais");
                columnaBusqueda = 2;  // País está en la columna 2
            } else if (opcion == 2) {
                System.out.print("Ingrese la ocupación a buscar: ");
                terminoBusqueda = scanner.nextLine().toLowerCase();
                criteriosBusqueda.add("ocupacion");
                columnaBusqueda = 3;  // Ocupación está en la columna 3
            } else if (opcion == 3) {
                System.out.println("Seleccione un encabezado para buscar:");
                System.out.println("1.Encabezado 1 ");
                System.out.println("2.Encabezado 2  ");
                System.out.println("3.Encabezado 3 ");
                System.out.println("4.Encabezado 4 ");
                System.out.println("5.Encabezado 5 ");
                System.out.println("6.Encabezado 6 ");
                System.out.println("7.Encabezado 7 ");
                System.out.println("8.Encabezado 8 ");
                System.out.println("9.Encabezado 9 ");
                System.out.println("10.Encabezado 10 ");
                int columnaOpcion = scanner.nextInt();
                criteriosBusqueda.add("");
                scanner.nextLine();  // Consumir la nueva línea después de la opción

                // Definir qué columna se va a buscar
                switch (columnaOpcion) {
                    case 1:
                        columnaBusqueda = 0;  // Ej Nombre está en la columna 0
                        break;
                    case 2:
                        columnaBusqueda = 1;  // Ej Teléfono está en la columna 1
                        break;
                    case 3:
                        columnaBusqueda = 2;  // Ej País está en la columna 2
                        break;
                    case 4:
                        columnaBusqueda = 3;  // Ej Ocupación está en la columna 3
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        return;
                }

                System.out.print("Ingrese el término a buscar en la columna seleccionada: ");
                terminoBusqueda = scanner.nextLine().toLowerCase();
            } else {
                System.out.println("Opción no válida.");
                return;
            }

            // 5) Luego, el programa revisa todos los registros del archivo y filtra solo los registros que coinciden con el término de búsqueda del usuario. 
            //Por ejemplo, si el usuario busca "ingeniero" en una ocupación, el programa encontrará y recopilará todos los registros donde la ocupación contenga la palabra "ingeniero".
            List<String[]> resultados = new ArrayList<>();
            for (String[] registro : registros) {
                boolean coincide = false;

                // Búsqueda por columna específica
                if (columnaBusqueda >= 0 && columnaBusqueda < registro.length) {
                    if (registro[columnaBusqueda].toLowerCase().contains(terminoBusqueda)) {
                        coincide = true;
                    }
                }

                // Si el término de búsqueda coincide con algún campo, agregar el registro
                if (coincide) {
                    resultados.add(registro);
                }
            }

            // Si no hay resultados
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron resultados.");
                return;
            }

            // 6) Los resultados filtrados luego se muestran en la pantalla, mostrando todos los registros coincidentes. 
            System.out.println("Resultados encontrados:");
            for (String[] registro : resultados) {
                System.out.println(String.join(", ", registro));
            }

            // 7) Además, el programa crea automáticamente un nuevo archivo CSV con estos resultados de búsqueda, nombrando el archivo de salida según los criterios de búsqueda y el término utilizado. 
            //Esto significa que si busca "Buenos Aires" como ciudad, se creará un archivo llamado "resultados_ciudad_buenosaires.csv". (Utilizando el string termino de busqueda () ingresado... 
            //basandose en el string criterio de busquedad (ciudad o ocupacion).
            String archivoSalida = "resultados_busqueda_" + terminoBusqueda.replaceAll(" ", "") + ".csv";

            // 8) Guardar los resultados en un nuevo archivo CSV
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
                // Escribir los encabezados dinámicamente
                if (encabezados != null) {
                    bw.write(String.join(",", encabezados)); // Escribir encabezados tomados del archivo
                    bw.newLine(); // Añadir salto de línea
                }

                // Escribir los registros filtrados
                for (String[] registro : resultados) {
                    bw.write(String.join(",", registro)); // Escribir cada registro como una línea
                    bw.newLine(); // Añadir salto de línea
                }

                System.out.println("Resultados guardados en: " + archivoSalida);
            } catch (IOException e) {
                System.out.println("Error al guardar los resultados: " + e.getMessage());
            }
        }
    }
}
