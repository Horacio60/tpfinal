//public class PronosticoDeportivo {
//    private ListaEquipos equipos;
//    private ListaPartidos partidos;
//    private ListaParticipantes participantes;
//    private ListaPronosticos pronosticos;
//
//    public PronosticoDeportivo() {
//        equipos = new ListaEquipos();
//        partidos = new ListaPartidos();
//        participantes = new ListaParticipantes();
//        pronosticos = new ListaPronosticos();
//    }
//
//    public void play(){
//        // cargar y listar los equipos
//        equipos.cargarDeArchivo();
//       
//        System.out.println("Los equipos cargados son: " + equipos.listar()+"\n");
//        System.out.println("==========================\n");
//        partidos.cargarDeArchivo(equipos);
//      
//        System.out.println("Los partidos cargados son: " + partidos.listar()+"\n");
//        System.out.println("==========================\n");
//        participantes.cargarDeArchivo();
//        // Una vez cargados los participantes, para cada uno de ellos
//        // cargar sus pronósticos
//        for (Participante p : participantes.getParticipantes()) {
//            p.cargarPronosticos(equipos, partidos);
//        }       
//        System.out.println("Los participantes cargados son: " + participantes.listar()+"\n");
//        
//        // agregar y/o modificar el codigo que quieran
//        
//    }    
//}
package com.mycompany.tp3;

/**
 *
 * @author grupo 8
 */
import de.vandermeer.asciitable.AsciiTable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PronosticoDeportivo {

   private ListaEquipos equipos;
   private ListaPartidos partidos;
   private ListaParticipantes participantes;
   private ListaPronosticos pronosticos;

   public PronosticoDeportivo() {
      equipos = new ListaEquipos();
      partidos = new ListaPartidos();
      participantes = new ListaParticipantes();
      pronosticos = new ListaPronosticos();

   }

/*  
   public void createDatabase() {
      //Se cargan las listas de los .CVS para poder cargar luego de la DB
		equipos.cargarDeArchivo();
		participantes.cargarDeArchivo();
		partidos.cargarDeArchivo(equipos);		
		pronosticos.cargarDeArchivoTodos(equipos, partidos);

      Connection conn = null;
      try {
         System.out.println(" Se conecta");
         conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db");
         System.out.println(" crea la DB");
         Statement stmt = conn.createStatement();
         String insert;
         System.out.println(" crea tabla equipos");
         stmt.executeUpdate("drop table if exists equipos");
         System.out.println("stmt executeUpdate");         
         stmt.executeUpdate("create table equipos (idEquipo integer, Nombre string, Descripcion string");
         
         System.out.println(" cargar tabla equipos");
         insert = "insert into equipos (idEquipo, Nombre, Descripcion) values (?,?,?)";
         
         PreparedStatement pInsert = conn.prepareStatement(insert);
         for (Equipo e : equipos.getEquipos()) {
            pInsert.setInt(1, e.getIdEquipo());
            pInsert.setString(2, e.getNombre());
            pInsert.setString(3, e.getDescripcion());
            pInsert.executeUpdate();
            //JEPII
            System.out.println("Creada tabla: equipos");
         }
         //crea tabla participantes
         stmt.executeUpdate("drop table if exists participantes");
         stmt.executeUpdate("create table participantes (idParticipante integer, Nombre string");
         //cargar tabla participantes
         insert = "insert into participantes (idParticipantes, Nombre) values (?,?)";
         pInsert = conn.prepareStatement(insert);
         for (Participante p : participantes.getParticipantes()) {
            pInsert.setInt(1, p.getIdParticipante());
            pInsert.setString(2, p.getNombre());
            pInsert.executeUpdate();
            //JEPII
            System.out.println("Creada tabla: participantes");
         }
         //crea tabla partidos
         stmt.executeUpdate("drop table if exists partidos");
         stmt.executeUpdate(
            "create table partidos (idPartido integer, idEquipo1 integer, idEquipo2 integer,"
               + "golesEquipo1 integer, golesEquipo2 integer,"
               + "FOREING KEY (idEquipo1) REFERENCES equipos(idEquipo),"
               + "FOREING KEY (idEquipo2) REFERENCES equipos(idEquipo),"
               + "");
         //crear tabla partidos
         insert = "insert into partidos (idPartido, idEquipo1, idEquipo2"
            + "golesEquipo1, golesEquipo2) values (?,?,?,?,?)";
         pInsert = conn.prepareStatement(insert);
         for (Partido p : partidos.getPartidos()) {
            pInsert.setInt(1, p.getIdPartido());
            pInsert.setInt(2, p.getEquipo1().getIdEquipo());
            pInsert.setInt(3, p.getEquipo2().getIdEquipo());
            pInsert.setInt(4, p.getGolesEquipo1());
            pInsert.setInt(5, p.getGolesEquipo2());
            pInsert.executeUpdate();
            //JEPII 
            System.out.println("Creada tabla: partidos");
         }
         //crea tabla pronosticos
         stmt.executeUpdate("drop table if exists pronosticos");
         stmt.executeUpdate(
            "create table pronosticos "
            + "(idPronostico integer,"
            + "idParticipante integer,"
            + "idPartido integer,"
            + "idEquipo integer,"
            + "resultado string,"
            + "FOREING KEY (idParticipante) REFERENCES participantes(idParticipante),"
            + "FOREING KEY (idPartido) REFERENCES partidos(idPartido),"
            + "FOREING KEY (idEquipo) REFERENCES equipos(idEquipo),"
            + "");
         
         insert = "insert into pronosticos (idPronostico, idParticipante, idPartido, idEquipo, resultado"
            + " values (?,?,?,?,?)";
         pInsert = conn.prepareStatement(insert);
         for (Pronostico p : pronosticos.getPronosticos()) {
            pInsert.setInt(1, p.getIdPronostico());
            pInsert.setInt(2, p.getIdParticipante());
            pInsert.setInt(3, p.getPartido().getIdPartido());
            pInsert.setInt(4, p.getEquipo().getIdEquipo());
            pInsert.setInt(5, p.getResultado());            
            pInsert.executeUpdate();
            //JEPII
            System.out.println("Creada tabla: pronosticos");
         }

         

      } catch (SQLException e) {
         System.out.println(e.getMessage());
         System.out.println("Error...");
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }
   }
   */

   public void play() {
      //JEPII crear DB
      //System.out.println("1) createDatabase()");
      //createDatabase();
      //JEPII
      System.out.println("1) equipos.cargarDeDB()");
      equipos.cargarDeDB();
      //System.out.println("Los equipos cargados son: " + equipos.listar());
      //JEPII
      System.out.println("2) partidos.cargarDeBD(equipos)");
      partidos.cargarDeBD(equipos);
      
      //JEPII AsciiTable
      //System.out.println("AsciiTable");
      AsciiTable part = new AsciiTable();
      part.addRule();
      
      part.addRow("EQUIPO 1", "GOLES", "EQUIPO 2", "GOLES");
      part.addRule();
      for (Partido p : partidos.getPartidos()) {
         part.addRow(p.getEquipo1().getNombre(), p.getGolesEquipo1(),
            p.getEquipo2().getNombre(), p.getGolesEquipo2());
      }
      part.addRule();
      System.out.println(part.render());
      
      //JEPII
      System.out.println("3) participantes.cargarDeDB()");
      participantes.cargarDeDB();
      for (Participante p : participantes.getParticipantes()) {
         p.cargarPronosticos(equipos, partidos);
      }
      AsciiTable at = new AsciiTable();
      at.addRule();
      at.addRow("PARTICIPANTE", "PUNTAJE");
      at.addRule();
      for (Participante p : participantes.getParticipantes()) {
         at.addRow(p.getNombre(), p.getPuntaje());
      }
      at.addRule();
      System.out.println(at.render());
      AsciiTable at2 = new AsciiTable();
      at2.addRule();
      at2.addRow("PARTICIPANTE", "PUNTAJE (ORDENADO)");
      
      at2.addRule();
      int i = 0;
      for (Participante p : participantes.getOrdenadosPorPuntaje()) {
         i++;
         at2.addRow(" "+i+")"+p.getNombre(), " "+p.getPuntaje());
      }
      at2.addRule();
      System.out.println(at2.render());
      //JEPII
      System.out.println("FIN PronosticoDeportivo");
   }

}
