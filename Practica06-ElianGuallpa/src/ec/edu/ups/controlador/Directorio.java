
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author SteveMike
 */
public class Directorio {

    private String ruta;
    private File archivo;
    private File[] archivos;

    public Directorio() {
        //constructor vacío
    }

    public boolean validacionRuta(String ruta) {
        
        archivo = new File(ruta);
        if (archivo.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean comprobacionExistencia(String ruta, String nombre) {
        
        archivo = new File(ruta + File.separator + nombre);
        if (archivo.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> listar(String ruta) {

        List<String> l = new ArrayList<>();
        l.clear();
        archivo = new File(ruta);
        archivos = archivo.listFiles();
        for (File elemento : archivos) {
            if (!elemento.isHidden()) {
                l.add(elemento.getName());
            }
        }
        return l;
    }

    public List<String> listarOcultos(String ruta) {
        
        List<String> lis = new ArrayList<>();
        lis.clear();
        archivo = new File(ruta);
        archivos = archivo.listFiles();
        for (File elemento : archivos) {
            if (elemento.isHidden() && elemento.isFile()) {
                lis.add(elemento.getName());
            }
        }
        for (File elemento : archivos) {
            if (elemento.isDirectory()) {
                File[] subdirectorios = elemento.listFiles();
                for (File subelemento : subdirectorios) {
                    if (subelemento.isHidden() && subelemento.isFile()) {
                        lis.add(subelemento.getName());
                    }
                }
            }
        }
        return lis;
    }

    public List<String> listarDirectorios(String ruta) {
        
        List<String> list = new ArrayList<>();
        list.clear();
        archivo = new File(ruta);
        archivos = archivo.listFiles();
        for (File elemento : archivos) {
            if (elemento.isHidden() && elemento.isDirectory()) {
                list.add(elemento.getName());
            }
        }
        return list;
    }

    public String mostrarInformacion(String nombre, String ruta) {

        archivo = new File(ruta);
        archivos = archivo.listFiles();
        String informacion = "Informacion: ";

        for (File elemento : archivos) {
            
            if (elemento.getName().equals(nombre)) {
                
                String path = "Path: ";
                path = path.concat(elemento.getAbsolutePath());
                informacion = informacion.concat("\n");
                informacion = informacion.concat(path);
                String t = "Tamaño: ";
                long bytes = elemento.length();
                bytes = (bytes) / (1024);
                String cad = String.valueOf(bytes);
                cad = cad.concat(".Kb");
                t = t.concat(cad);
                informacion = informacion.concat("\n" + t);
                String leer = "Permisos de lectura: "; //lectura y escritura
                
                if (elemento.canRead()) {
                    leer = leer.concat("- Abierto");
                } else {
                    leer = leer.concat("- Cerrado");
                }
                informacion = informacion.concat("\n" + leer);
                String escribir = "Permisos de escritura: ";
                
                if (elemento.canWrite()) {
                    escribir = escribir.concat("- Abierto");
                } else {
                    escribir = escribir.concat("- Cerrado");
                }
                informacion = informacion.concat("\n" + escribir);
                
                long lastModified = elemento.lastModified();

                String pattern = "yyyy-MM-dd hh:mm aa";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date lastModifiedDate = new Date(lastModified);

                String fecha = "Última modificación: ";
                fecha = fecha.concat(lastModifiedDate.toString());
                informacion = informacion.concat("\n" + fecha);
            }
        }
        return informacion;
    }

    public void crearDirectorio(String ruta, String nombre) {
        archivo = new File(ruta + File.separator + nombre);
        archivo.mkdir();
    }

    public void renombrarDirectorio(String ruta, String actual, String renombre) {
        archivo = new File(ruta + File.separator + actual);
        File nuevo = new File(ruta + File.separator + renombre);
        archivo.renameTo(nuevo);
    }

    public void eliminarDirectorio(String ruta, String eliminar) throws IOException {
        
        archivo = new File(ruta + File.separator + eliminar);
        
        if (archivo.isDirectory()) {
            archivos = archivo.listFiles();

            for (int i = 0; i < archivos.length; i++) {
                if (archivos[i].isDirectory()) {
                    eliminarDirectorios(archivos[i]);
                } else {
                    archivos[i].delete();
                }
            }
            archivo.delete();
        } else {
            archivo.delete();
        }
    }

    public void eliminarDirectorios(File path) {
        
        File[] files = path.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                eliminarDirectorios(files[i]);
            } else {
                files[i].delete();
            }
        }
        path.delete();
    }

    public List<String> buscarArchivo(String ruta, String nombre) {
        
        archivo = new File(ruta + File.separator + nombre);
        archivos = archivo.listFiles();
        List<String> lit = new ArrayList<>();
        
        for (File archivo1 : archivos) {
            lit.add(archivo1.getName());
        }
        return lit;
    }

    public String devolverRuta(String ruta, String nombre) {
        
        archivo = new File(ruta + File.separator + nombre);
        return archivo.getAbsolutePath();
    }
}