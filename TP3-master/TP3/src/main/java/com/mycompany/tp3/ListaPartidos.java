package com.mycompany.tp3;

/**
 *
 * @author grupo 8
 */
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListaPartidos {

   //atributos
   private List<Partido> partidos;
   private String partidosCSV;

   //metodos
   public ListaPartidos(List<Partido> partidos, String partidosCSV) {
      this.partidos = partidos;
      this.partidosCSV = partidosCSV;
   }

   public ListaPartidos() {
      this.partidos = new ArrayList<Partido>();
      this.partidosCSV = "./partidos.csv";
   }

   public List<Partido> getPartidos() {
      return partidos;
   }

   public void setPartidos(List<Partido> partidos) {
      this.partidos = partidos;
   }

   public String getPartidosCSV() {
      return partidosCSV;
   }

   public void setPartidosCSV(String partidosCSV) {
      this.partidosCSV = partidosCSV;
   }

   @Override
   public String toString() {
      return "ListaPartidos{" + "partidos= " + partidos + "/n" + ", partidosCSV= " + partidosCSV + '}' + "/n";
   }

   //*****
   // add y remove elementos
   public void addPartido(Partido p) {
      this.partidos.add(p);
   }

   public void removePartido(Partido p) {
      this.partidos.remove(p);
   }

   public Partido getPartido(int idPartido) {

      Partido encontrado = null;

      for (Partido pa : this.getPartidos()) {

         if (pa.getIdPartido() == idPartido) {
            encontrado = pa;
            break;
         }
      }
      return encontrado;
   }

   public String listar() {
      String lista = "";
      for (Partido partido : partidos) {
         lista += "\n" + partido;
      }
      return lista;
   }

   public void cargarDeArchivo(ListaEquipos equipos) {

      String datosPartido;

      String vectorPartido[];

      Partido partido;
      int fila = 0;

      try {
         Scanner sc = new Scanner(new File(this.getPartidosCSV()));
         
         sc.useDelimiter("\n");

         while (sc.hasNext()) {

            datosPartido = sc.next();

            fila++;

            if (fila == 1) {
               continue;
            }

            vectorPartido = datosPartido.split(",");

            // graba el equipo en memoria
            //convertir un string a un entero;
            int idPartido = Integer.parseInt(vectorPartido[0]);
            int idEquipo1 = Integer.parseInt(vectorPartido[1]);
            int idEquipo2 = Integer.parseInt(vectorPartido[2]);
            int golesEquipo1 = Integer.parseInt(vectorPartido[3]);
            int golesEquipo2 = Integer.parseInt(vectorPartido[4]);
            Equipo equipo1 = equipos.getEquipo(idEquipo1);
            Equipo equipo2 = equipos.getEquipo(idEquipo2);
            // crea el objeto en memoria
            partido = new Partido(idPartido, equipo1, equipo2, golesEquipo1, golesEquipo2);
            // equipo1, equipo2, golesEquipo1, golesEquipo2)
            //idEquipo1 , idEquipo2, golesEquipo1, golesEquipo2);

            // llama al metodo add para grabar el equipo en la lista en memoria
            this.addPartido(partido);
         }
         //closes the scanner
      } catch (IOException ex) {
         System.out.println("Mensaje: " + ex.getMessage());
      }

   }

   public void cargarDeBD(ListaEquipos listaEquipos) {
        
        Connection conn=null;
        try { 
            //Establecer la conexion
            conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db" );
            Statement stmt = conn.createStatement();
            
            //String sql;
            String sql =  "SELECT idPartido, idEquipo1, idEquipo2, golesEquipo1, golesEquipo2 FROM partidos";        
            ResultSet rs = stmt.executeQuery(sql); //Ejecutar la consulta y obtener resultado
            //JEPII
            //System.out.println ("conectado a: ListaPartidos");    
            while (rs.next()) {
                //Obtener los objetos que necesito para el constructor
                Equipo equipo1 = listaEquipos.getEquipo(rs.getInt("idEquipo1"));
                Equipo equipo2 = listaEquipos.getEquipo(rs.getInt("idEquipo2"));
		// crea el objeto en memoria
                Partido partido = new Partido(
                        rs.getInt("idPartido"),
                        equipo1,
                        equipo2,
                        rs.getInt("golesEquipo1"),
                        rs.getInt("golesEquipo2")
                );

		// llama al metodo add para grabar el equipo en la lista en memoria
                this.addPartido(partido);

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
