package main.java.es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.Arrays;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class TFG {
    @Email
    private String email;
    private String pass;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String titulo;
    @Email
    private String tutor;
    private int status;
    private byte[] memoria;
    private double nota;

    public TFG() {
    }

    public TFG(String email, String pass, String nombre, String titulo, String tutor, int status, byte[] memoria,
            double nota) {
        this.email = email;
        this.pass = pass;
        this.nombre = nombre;
        this.titulo = titulo;
        this.tutor = tutor;
        this.status = status;
        this.memoria = memoria;
        this.nota = nota;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
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
    public String getTutor() {
        return tutor;
    }
    public void setTutor(String tutor) {
        this.tutor = tutor;
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

    @Override
    public String toString() {
        return "TFG [email=" + email + ", pass=" + pass + ", nombre=" + nombre + ", titulo=" + titulo + ", tutor="
                + tutor + ", status=" + status + ", memoria=" + Arrays.toString(memoria) + ", nota=" + nota + "]";
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

    
}