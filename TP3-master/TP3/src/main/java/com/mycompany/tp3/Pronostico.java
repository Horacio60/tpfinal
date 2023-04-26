
package com.mycompany.tp3;

/**
 *
 * @author grupo 8
 */
public class Pronostico {
   //atributos
   private int idPronostico;
   private Equipo equipo;
   private Partido partido;
   private char resultado; 
   private Integer idParticipante;

   public Pronostico(int idPronostico, Equipo equipo, Partido partido, char resultado) {
      this.idPronostico = idPronostico;
      this.equipo = equipo;
      this.partido = partido;
      this.resultado = resultado;      
   }
   
   public Pronostico(int idPronostico, Equipo equipo, Partido partido, char resultado, Integer idParticipante) {
      this.idPronostico = idPronostico;
      this.equipo = equipo;
      this.partido = partido;
      this.resultado = resultado;
      this.idParticipante = idParticipante;
   }
public Pronostico() {      
   }

  
public Integer getIdParticipante() {
      return idParticipante;
   }

public void setIdParticipante(Integer idParticipante){
   this.idParticipante = idParticipante;
   }

   public Equipo getEquipo() {
      return equipo;
   }

   public void setEquipo(Equipo equipo) {
      this.equipo = equipo;
   }

   public Partido getPartido() {
      return partido;
   }

   public void setPartido(Partido partido) {
      this.partido = partido;
   }

   public char getResultado() {
      return resultado;
   }

   public void setResultado(char resultado) {
      this.resultado = resultado;
   }

   public void setIdPronostico(int idPronostico) {
      this.idPronostico = idPronostico;
   }

   public int getIdPronostico() {
      return idPronostico;
   }

  
 
   @Override
   public String toString() {
           return "Pronostico{" + "idPronostico=" + idPronostico + ", equipo=" + equipo + ", partido=" + partido + ", resultado=" + resultado + '}';
   }
   
   public int getPuntaje() {
      char resultado = this.partido.getResultado(this.equipo);
      if (resultado == this.getResultado()){
         return 1;
      } else {
         return 0;
      }
   }
   
}
