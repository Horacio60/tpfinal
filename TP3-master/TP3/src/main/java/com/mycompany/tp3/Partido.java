
package com.mycompany.tp3;

/**
 *
 * @author grupo 8
 */
public class Partido {
   //atributos 
   private int idPartido;
   private Equipo equipo1;
   private Equipo equipo2;
   private int golesEquipo1;
   private int golesEquipo2;
   
   //metodos

   public Partido(int idPartido, Equipo equipo1, Equipo equipo2, int golesEquipo1, int golesEquipo2) {
      this.idPartido = idPartido;
      this.equipo1 = equipo1;
      this.equipo2 = equipo2;
      this.golesEquipo1 = golesEquipo1;
      this.golesEquipo2 = golesEquipo2;
   }
   public Partido() {
      this.idPartido = 0;
      this.equipo1 = null;
      this.equipo2 = null;
      this.golesEquipo1 = 0;
      this.golesEquipo2 = 0;
   }
   public int getIdPartido() {
      return idPartido;
   }

   public void setIdPartido(int idPartido) {
      this.idPartido = idPartido;
   }

   public Equipo getEquipo1() {
      return equipo1;
   }

   public void setEquipo1(Equipo equipo1) {
      this.equipo1 = equipo1;
   }

   public Equipo getEquipo2() {
      return equipo2;
   }

   public void setEquipo2(Equipo equipo2) {
      this.equipo2 = equipo2;
   }

   public int getGolesEquipo1() {
      return golesEquipo1;
   }

   public void setGolesEquipo1(int golesEquipo1) {
      this.golesEquipo1 = golesEquipo1;
   }

   public int getGolesEquipo2() {
      return golesEquipo2;
   }

   public void setGolesEquipo2(int golesEquipo2) {
      this.golesEquipo2 = golesEquipo2;
   }

   @Override
   public String toString() {
      return "Partido{" + " idPartido=" + idPartido 
         + "equipo1=" + equipo1 + ", equipo2=" + equipo2 
         + ", golesEquipo1=" + golesEquipo1 + ", golesEquipo2=" + golesEquipo2 + '}';
   }
   
   
  /*
   public char getResultado(Equipo equipo){
      if (getGolesEquipo1() > getGolesEquipo2())         
    return 'G';
      else if (getGolesEquipo1() < getGolesEquipo2())         
    return 'P';
      else
    return 'E';
   }
   */
   
   public char getResultado(Equipo equipo){
        char resultado = 'X'; //Todavia no se sabe quien gano
        
        if(equipo.getNombre().equals(equipo1.getNombre())){
            if(this.golesEquipo1 > this.golesEquipo2){
                resultado = 'G';
            } else if(this.golesEquipo1 < this.golesEquipo2){
                resultado = 'P';
            } else{
                resultado = 'E';
            }
        }else if(equipo.getNombre().equals(equipo2.getNombre())){
            if(this.golesEquipo2 > this.golesEquipo1){
               resultado = 'G'; 
            }else if(this.golesEquipo2 < this.golesEquipo1){
                resultado = 'P';
            }else{
                resultado = 'E';
            }
        }
        return resultado;
    }
   
     
}
