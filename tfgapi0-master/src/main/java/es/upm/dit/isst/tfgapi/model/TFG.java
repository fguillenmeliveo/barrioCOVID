package es.upm.dit.isst.tfgapi.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class TFG {
    @Id //para marcar la clave primaria
    private String email;
    private String password;
    private String nombre;
    private String titulo;
    private int status;
    @Lob // marca que el arrya de bytes es una variable grande
    private byte[] memoria;
    private double nota;
    private String tutor;

    public TFG() {

    }

    public TFG(String email, String password, String nombre, String titulo, int status, byte[] memoria, double nota,
            String tutor) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.titulo = titulo;
        this.status = status;
        this.memoria = memoria;
        this.nota = nota;
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "TFG [email=" + email + ", password=" + password + ", nombre=" + nombre + ", titulo=" + titulo
                + ", status=" + status + ", memoria=" + Arrays.toString(memoria) + ", nota=" + nota + ", tutor=" + tutor
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TFG other = (TFG) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getMemoria() {
        return memoria;
    }

    public void setMemoria(byte[] memoria) {
        this.memoria = memoria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
    
    
}
