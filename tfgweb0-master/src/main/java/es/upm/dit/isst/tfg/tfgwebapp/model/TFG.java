package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.Arrays;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class TFG {
    @Email // validación del correo: que haya un nombre a la izquierda, que haya lo que hay que poner después del @ y que no haya valores raros
    private String email;
    private String password;
    @NotEmpty
    private String nombre;
    @NotEmpty // validación para asegurarnos de que el campo no está vacío
    private String titulo;
    private int status;
    private byte[] memoria;
    private double nota;
    @Email 
    private String tutor;
    
    public TFG() {
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

    @Override
    public String toString() {
        return "TFG [email=" + email + ", password=" + password + ", nombre=" + nombre + ", titulo=" + titulo
                + ", status=" + status + ", memoria=" + Arrays.toString(memoria) + ", nota=" + nota + ", tutor=" + tutor
                + "]";
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
    
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setMemoria(byte[] memoria) {
        this.memoria = memoria;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getStatus() {
        return status;
    }
    public byte[] getMemoria() {
        return memoria;
    }
    public double getNota() {
        return nota;
    }
    public String getTutor() {
        return tutor;
    }


}
