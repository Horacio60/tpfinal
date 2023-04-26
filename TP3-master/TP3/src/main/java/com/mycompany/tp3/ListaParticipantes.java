
package com.mycompany.tp3;
/**
 *
 * @author grupo 8
 */
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ListaParticipantes {
    // atributo
    private List<Participante> participantes;
    private String participantesCSV;

    public ListaParticipantes(List<Participante> participantes, String participantesCSV) {
        this.participantes = participantes;
        this.participantesCSV = participantesCSV;
    }
    
    public ListaParticipantes() {
        this.participantes = new ArrayList<Participante>();
        this.participantesCSV = "./participantes.csv";
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public String getParticipantesCSV() {
        return participantesCSV;
    }

    public void setParticipantesCSV(String participantesCSV) {
        this.participantesCSV = participantesCSV;
    }
    
    // add y remove elementos
    public void addParticipante(Participante p) {
        this.participantes.add(p);
    }
    public void removeParticipante(Participante p) {
        this.participantes.remove(p);
    }
    
    /***
     * Este método devuelve un Participante (o null) buscandolo por idParticipante
     * @param idParticipante Identificador del equipo deseado
     * @return Objeto Participante (o null si no se encuentra)
     */
    public Participante getParticipante (int idParticipante) {
        // Defini un objeto de tipo Participante en dónde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavía.
        Participante encontrado = null;
        // Recorro la lista de participantes que está cargada
        for (Participante eq : this.getParticipantes()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (eq.getIdParticipante() == idParticipante) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = eq;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el Participante, pueden pasar dos cosas:
        // 1- Lo encontré en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontré en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }

    @Override
    public String toString() {
        return "ListaParticipantes{" + "participantes=" + participantes + '}'+"\n";
    }

    public String listar() {
        String lista = "";
        for (Participante participante: participantes) {
            lista += "\n" + participante;
        }           
        return lista;
    }
    
    //Sort para ordenar la lista
    public List <Participante> getOrdenadosPorPuntaje(){
        List <Participante> ordenados = new ArrayList <Participante>();
        ordenados.addAll(participantes);
        
        //Lista ordenada de menor a mayor
        Collections.sort(ordenados, Collections.reverseOrder() );
        return ordenados;
    }   
    
    //Ordenar por Puntajes
    public String listarOrdenadosPorPuntaje (){
        List<Participante> ordenados = this.getOrdenadosPorPuntaje();
        String lista="";
        for (Participante participantes: ordenados){
            lista += "\n"+ participantes;            
        }
        return lista;
    }
    
    // cargar desde el archivo
    public void cargarDeArchivo() {
        // para las lineas del archivo csv
        String datosParticipante;
        // para los datos individuales de cada linea
        String vectorParticipante[];
        // para el objeto en memoria
        Participante participante;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File(this.getParticipantesCSV()));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosParticipante = sc.next();
                //JEPII Descomentar si se quiere mostrar cada línea leída desde el archivo
                // System.out.println("ListaParticipantes: "+datosParticipante);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorParticipante = datosParticipante.split(",");   
                
                // graba el equipo en memoria
                //convertir un string a un entero;
                int idParticipante = Integer.parseInt(vectorParticipante[0]);
                String nombre = vectorParticipante[1];
                // crea el objeto en memoria
                participante = new Participante(idParticipante, nombre);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       
    }
    
    // cargar desde la Base de Datos
    public void cargarDeDB() {     
        
        Connection conn=null;
        try { 
            //Establecer la conexion
            conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            Statement stmt = conn.createStatement();
            
            //String sql;
            String sql =  "SELECT idParticipante, nombre FROM participantes";            
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado  
//JEPII            
//System.out.println ("conectado a: ListaParticipantes");            
            while (rs.next()) {
                // Obtener los objetos que necesito para el constructor
                Participante participante = new Participante (rs.getInt("idParticipante"),
                rs.getString("Nombre"));
                this.addParticipante(participante);
                // llama al metodo add para grabar el equipo en la lista en memoria
                
              

            }
            //closes the scanner
        } catch (SQLException e) {
                System.out.println("Mensaje: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // conn close failed.
                System.out.println(e.getMessage());
            }
        }       
    }    
    
}
