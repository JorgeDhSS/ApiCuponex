/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Jorge
 */
public class Empresa {
    private Integer id;
    private String nombre;
    private String nombreComercial;
    private String representante;
    private String webPage;
    private String rfc;
    private Integer status;
    private String ciudad;
    private String cp;
    private String calle;
    private Integer numero;
    private String tel;
    private String email;

    public Empresa() {
    }

    public Empresa(Integer id, String nombre, String nombreComercial, String representante, String webPage, String rfc, Integer status, String ciudad, String cp, String calle, Integer numero, String tel, String email) {
        this.id = id;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.representante = representante;
        this.webPage = webPage;
        this.rfc = rfc;
        this.status = status;
        this.ciudad = ciudad;
        this.cp = cp;
        this.calle = calle;
        this.numero = numero;
        this.tel = tel;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
