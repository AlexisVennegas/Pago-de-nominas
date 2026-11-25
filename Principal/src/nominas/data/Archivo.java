package nominas.data;

public class Archivo {

    private String ruta;
    private String nombre;
    private int nTransacciones;

    public Archivo(String ruta, String nombre, int nTransacciones){
        this.ruta = ruta;
        this.nombre = nombre;
        this.nTransacciones = nTransacciones;
    }
    public Archivo(){};

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getnTransacciones() {
        return nTransacciones;
    }

    public void setnTransacciones(int nTransacciones) {
        this.nTransacciones = nTransacciones;
    }
}
