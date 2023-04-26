
package com.mycompany.tp3;


/**
 *
 * @author GRUPO 8
 */
public class TP3 {
   
   public static PronosticoDeportivo PRODE;

    public static void main(String[] args) {
       System.out.println ("Sistema de simulación de pronósticos deportivos.");
        System.out.println ("Ejecutándose desde:"+System.getProperty("user.dir"));
        
        PRODE = new PronosticoDeportivo();

        PRODE.play();
    }
}
