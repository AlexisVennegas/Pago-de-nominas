package nominas.generador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;


// FUNCION QUE CREA UN ARCHIVO CON UN FORMATO
public class SubApGenFT {

    public static Scanner scanner = new Scanner(System.in);

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

        return "" + comienzo + cuerpo + ";" + nomina;
    }


    public static String lectura(){
        String fichero = "";
        System.out.println("Introduce: [RUTA ARCHIVO],[NOMBRE ARCHIVO],[NUMERO TRANSFERENCIA A GENRAR]");
        System.out.println("separado por comas cabron");

        fichero = scanner.next();
        if("".equals(fichero)) System.out.println("error");

        return fichero;
    }


    // FUNCION PARA GENERAR UN FICHERO
    public static void generarFichero(String ficheroData){
        String[] ficheroDataSplit =     ficheroData.split(",");
        String nombreFichero =          ficheroDataSplit[1];
        int cantidadGenerar =           Integer.parseInt(ficheroDataSplit[2]);

        // PEQUEÑA COMPROBAR DE LA EXTENSION CSV
        if (!nombreFichero.toLowerCase().endsWith(".csv"))   nombreFichero += ".csv";
        Path ruta =                                         Path.of(ficheroDataSplit[0], nombreFichero);

        // SE PREGUNTA SI EXISTE LE DIRECTORIO Y MANDA UN CODIGO DE ERROR
        if(Files.exists(ruta))                              generarErrores("fichero ya existe", 2);


        // SE CREA LA RUTA PRIMERO Y LUEGO EL ARCHIVO
        try {
            Files.createDirectories(ruta.getParent());

            // SE CREA EL ARCHIVO
            Files.createFile(ruta);

            // BUCLE PARA ITERAR LA CANTIDADGENERAR Y LLAMAR A LA FUNCION QUE ESCRIBE EN EL ARCHIVO
            for(int i = 0; i < cantidadGenerar; i++){
                Files.writeString(ruta, generartransferencias() + System.lineSeparator(), StandardOpenOption.APPEND);
                Thread.sleep(10);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
    // FUNCION QUE LEE POR STIND LA ENTRADA
    public static void principal(){
        String ficheroData =    lectura();
        // AQUI SE ELIGE UN NUMERO RANDOM ENTRE 1 Y 5 USANDO MATH.RANDOM() PARA LA ESPERA
        int numEspera  =    (int) (Math.random() * 5) + 1;
        // LO MISMO PARA LAS POSIBLIDADES
        int posibilidadError   =    (int) (Math.random() * 100) + 1;

       // TESTING BORRAR LUEGO PA
        System.out.println("esprando .." + numEspera);
        System.out.println("posibilidadError: " + posibilidadError);

        // SE HACE LA ESPERA DE (numEspera) SEGUNDOS usando Thread.sleep()
        try {

            // UN BUCLE PARA QUE SEA LA CUENTA REGRESIVA DEL (numEspera)
            for(int i = numEspera; i > 0; i--){
                System.out.println("esperando... t - " + i);
                Thread.sleep(1000L);
            }
            // AQUI SE PREGUNTA POR LA POSIBILIDAD DE ERROR
            if(posibilidadError >= 31 && posibilidadError <= 100)   generarFichero(ficheroData);
            else if(posibilidadError <= 30)                         generarErrores("error posibilidad >= 31",2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            // CERRAR SIEMPRE EL SCANNER
            scanner.close();
        }



    }
}
