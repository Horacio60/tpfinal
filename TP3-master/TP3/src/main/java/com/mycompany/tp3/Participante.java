
package com.mycompany.tp3;
/**
 *
 * @author grupo 8
 */
public class Participante implements Comparable <Participante> {
    //Atributos
    private Integer idParticipante;
    private String nombre;
    private ListaPronosticos pronosticos;
    
    
    //Constructor
    public Participante(Integer idParticipante, String nombre, ListaPronosticos pronosticos) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.pronosticos = pronosticos;
    }
    
    public Participante(Integer idParticipante, String nombre) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.pronosticos = new ListaPronosticos();
    }

    public Participante() {
        this.idParticipante = null;
        this.nombre = null;
        this.pronosticos = new ListaPronosticos();
    }
    
    
    //Getters and Setters
    public Integer getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Integer idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaPronosticos getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(ListaPronosticos pronosticos) {
        this.pronosticos = pronosticos;
    }

    
    // retorna el puntaje del participante calculando los valores de los pronosticos
    public int  getPuntaje() {
        // Para ver el puntaje debo recorrer los pronosticos y ver el puntaje
        // de cada uno y acumularlo. Debo devolver el total.
        int puntaje = 0;
        // el primer mensaje corresponde al atributo pronosticos de participante
        // el segundo mensaje corresponde a la lista que tiene el atributo pronosticos
        // de esa lista se obtiene cada pronostico y se saca el puntaje acumulandolo en 
        // la variable puntaje
        for (Pronostico p : this.getPronosticos().getPronosticos()) {
            puntaje += p.getResultado();
        }
        return puntaje;
    } 

    @Override
    public String toString() {
        return "Participante{" + "idParticipante=" + idParticipante + ", nombre=" + nombre + ", pronosticos=" + pronosticos + '}';
    }
    
    //Compara por puntaje
    @Override
    public int compareTo(Participante p){
        //devuelve -1 si es menor, 0 si es igual 1 es mayor
        int miPuntaje = this.getPuntaje();
        int otroPuntaje = p.getPuntaje();
        
        if (miPuntaje == otroPuntaje)
            return 0;
        else if (miPuntaje > otroPuntaje)
            return 1;
        else
            return -1;
        
    }
    
    void cargarPronosticos (ListaEquipos equipos, ListaPartidos partidos) {
        this.pronosticos.cargarDeDB(this.getIdParticipante(), equipos, partidos);
    }
}
