package nominas.generador;

import nominas.data.Archivo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import static nominas.data.Constantes.*;


// FUNCION QUE CREA UN ARCHIVO CON UN FORMATO
public class SubApGenFT {

    public static Scanner scanner = new Scanner(System.in);
    private static Properties propiedades = new Properties();

    // FUNCION PARA CARGAR PRIMERO EL PROPIERTIES
    public static void cargarPropiedades() {

        try (FileInputStream entrada = new FileInputStream("transferencias.properties")) {
            propiedades.load(entrada);

        } catch (IOException e) {
            generarErrores("Error al cargar el archivo properties: " + e, CODIGO_ERROR_GENERAR_ARCHIVO);
        }
    }

    public static void generarErrores(String mensaje, int codigo){
        System.out.println(mensaje);
        System.exit(codigo);
    }

    // FUNCION QUE GENERA TRANSFERENCIAS ALEATORIAS Y LAS CONSTRUYE
    public static String generartransferencias(){
        int comienzo =      (int) (Math.random() * 2) + 1;
        int cuerpo =        (int) (Math.random() * 90000000) + 10000000;
        // EL 1 ASEGURA QUE TAMBIEN PUEDA SALIR
        double nomina = ((int) (Math.random() * 150001) + 150000) / 100.0;

        return "" + comienzo + cuerpo + propiedades.getProperty("delimitador") + String.format(Locale.US, "%.2f", nomina);
    }

    // METODO PARA LEER LA ENTRADA DE STIND, COMPRUEBA Y DEVUELVE EL OBJETO ARCHIVO CON RUTA, NOMBRE ARCH, Y N TRANSACCIONES
    public static Archivo lectura (){
        String fichero = "";
        Archivo archivo = new Archivo();
        System.out.println("Introduce: [RUTA ARCHIVO],[NOMBRE ARCHIVO],[NUMERO TRANSFERENCIA A GENRAR]");
        System.out.println("separado por comas");

        fichero = scanner.next();
        String[] ficheroDataSplit =     fichero.split(",");

        // AQUI COMPROBAR LOS DATOS Y MADNAR CODIGO DE ERRORES
        if (ficheroDataSplit.length != 3)                                                           generarErrores("Error: debes introducir  tres parametros",CODIGO_ERROR_PARAMETROS_GENERALES);
        if("".equals(fichero) || "".equals(ficheroDataSplit[0]) || "".equals(ficheroDataSplit[1]))  generarErrores("error parametros generales", CODIGO_ERROR_PARAMETROS_GENERALES);
        if(Integer.parseInt(ficheroDataSplit[2]) <= 0 )                                             generarErrores("error numero transacciones", CODIGO_ERROR_PARAMETROS_GENERALES);

        // SE CONSTRUYE EL OBJETO ARCHIVO CON LOS DATOS
        archivo.setRuta(ficheroDataSplit[0]);
        archivo.setNombre(ficheroDataSplit[1]);
        archivo.setnTransacciones(Integer.parseInt(ficheroDataSplit[2]));
        return archivo;
    }


    // FUNCION PARA GENERAR UN FICHERO
    public static void generarFichero() throws IOException {
        // LLAMAMOS AL METODO LECTURA DONDE ENTRA EL STDIND, COMPRUEBA Y DEVUELVE EL METODO ARCHIVO
        Archivo archivo = lectura();

        // AQUI SE CREA EL ARCHIVO DONDE GUARDA LAS NOMINAS
        Path ruta = Path.of(archivo.getRuta(), archivo.getNombre());

        // AQUI SE CREA LA RUTA DE BLOQUE
        Path rutaBloqueo = Path.of(archivo.getRuta(), propiedades.getProperty("fichero.bloqueo"));

        // SE PREGUNTA SI EXISTE LE DIRECTORIO Y MANDA UN CODIGO DE ERROR
        if(Files.exists(ruta)) generarErrores("fichero ya existe", CODIGO_ERROR_GENERAR_ARCHIVO);


        // SE CREA LA RUTA PRIMERO Y LUEGO EL ARCHIVO
        try {
            Files.createDirectories(ruta.getParent());

            // SE CREA EL FICHERO DE BLOQUEO
            Files.createFile(rutaBloqueo);

            // SE CREA EL ARCHIVO
            Files.createFile(ruta);

            // BUCLE PARA ITERAR LA CANTIDADGENERAR Y LLAMAR A LA FUNCION QUE ESCRIBE EN EL ARCHIVO
            for(int i = 0; i < archivo.getnTransacciones(); i++){
                Files.writeString(ruta, generartransferencias() + System.lineSeparator(), StandardOpenOption.APPEND);
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            Files.deleteIfExists(rutaBloqueo);
            generarErrores("" + e, CODIGO_ERROR_GENERAR_ARCHIVO);
            throw new RuntimeException(e);
        } finally {
            // SE ELIMINA EL FICHERO DE BLOQUEO AL TERMINAR
            Files.deleteIfExists(rutaBloqueo);
        }


    }
    // FUNCION QUE LEE POR STIND LA ENTRADA
    public static void principal(){

        // AQUI SE ELIGE UN NUMERO RANDOM ENTRE 1 Y 5 USANDO MATH.RANDOM() PARA LA ESPERA
        int numEspera  =    (int) (Math.random() * 5) + 1;
        // LO MISMO PARA LAS POSIBLIDADES
        int posibilidadError   =    (int) (Math.random() * 100) + 1;

       // TESTING BORRAR LUEGO PA
        System.out.println("----Esperando----" + numEspera);
        System.out.println("-----Error-----: " + posibilidadError);

        // SE HACE LA ESPERA DE (numEspera) SEGUNDOS usando Thread.sleep()
        try {

            // UN BUCLE PARA QUE SEA LA CUENTA REGRESIVA DEL (numEspera)
            for(int i = numEspera; i > 0; i--){
                System.out.println("esperando... t - " + i);
                Thread.sleep(1000L);
            }
            // AQUI SE PREGUNTA POR LA POSIBILIDAD DE ERROR
            if(posibilidadError >= 31 && posibilidadError <= 100)   generarFichero();
            else if(posibilidadError <= 30)                         generarErrores("error posibilidad >= 31",CODIGO_ERROR_PROBABILIDAD);
        } catch (InterruptedException | IOException e) {
            generarErrores("" + e, CODIGO_ERROR_SORTEO);
            throw new RuntimeException(e);
        } finally {
            // CERRAR SIEMPRE EL SCANNER
            scanner.close();
        }
    }

    public static void main(String[] args) {
        cargarPropiedades();
        principal();
        // Principal/src/nominas/data/,test.csv,50
    }
}
