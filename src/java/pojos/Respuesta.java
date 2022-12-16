/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

/**
 *
 * @author Jorge
 */
public class Respuesta {
    private Boolean error;
    private String mensaje;
    private UserApp user;

    public Respuesta(Boolean error, String mensaje, UserApp user) {
        this.error = error;
        this.mensaje = mensaje;
        this.user = user;
    }

    public Respuesta() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public UserApp getUser() {
        return user;
    }

    public void setUser(UserApp user) {
        this.user = user;
    }
}
