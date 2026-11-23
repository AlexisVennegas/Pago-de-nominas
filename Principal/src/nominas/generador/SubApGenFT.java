package nominas.generador;

import java.util.Scanner;


// FUNCION QUE CREA UN ARCHIVO CON UN FORMATO
public class SubApGenFT {

    public static Scanner scanner = new Scanner(System.in);

    public static void generarErrores(int codigo){
        System.out.println("hubo un error mi compa");
        System.exit(codigo);
    }


    public static String lectura(){
        String fichero = "";
        System.out.println("fichero de transferencia: ");

        fichero = scanner.next();
        if("".equals(fichero)) System.out.println("error");

        return fichero;
    }

    public static void generarFichero(String ficheroData){

        System.out.println("que onda soy generarfichero y me llego: " + ficheroData);

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
        System.out.println("posibilidadError" + posibilidadError);

        // SE HACE LA ESPERA DE (numEspera) SEGUNDOS usando Thread.sleep()
        try {

            // UN BUCLE PARA QUE SEA LA CUENTA REGRESIVA DEL (numEspera)
            for(int i = numEspera; i > 0; i--){
                System.out.println("esperando... t - " + i);
                Thread.sleep(1000L);
            }
            // AQUI SE PREGUNTA POR LA POSIBILIDAD DE ERROR
            if(posibilidadError >= 31 && posibilidadError <= 100)   generarFichero(ficheroData);
            else if(posibilidadError <= 30)                         generarErrores(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            // CERRAR SIEMPRE EL SCANNER
            scanner.close();
        }



    }
}
