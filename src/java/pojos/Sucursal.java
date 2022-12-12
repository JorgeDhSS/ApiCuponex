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
public class Sucursal {
    private Integer id;
    private String nombre;
    private String calle;
    private Integer numero;
    private String cp;
    private String colonia;
    private String ciudad;
    private String tel;
    private String latitud;
    private String longitud;
    private String encargado;
    private Integer enterprise_id;
    private Integer status;
    
    public Sucursal() {
    }

    public Sucursal(String nombre, String calle, Integer numero, String cp, String colonia, String ciudad, String tel, String latitud, String longitud, String encargado, Integer enterprise_id, Integer status) {
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.tel = tel;
        this.latitud = latitud;
        this.longitud = longitud;
        this.encargado = encargado;
        this.enterprise_id = enterprise_id;
        this.status = status;
    }
    
    public Sucursal(Integer id, String nombre, String calle, Integer numero, String cp, String colonia, String ciudad, String tel, String latitud, String longitud, String encargado, Integer enterprise_id) {
        this.id = id;
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.tel = tel;
        this.latitud = latitud;
        this.longitud = longitud;
        this.encargado = encargado;
        this.enterprise_id = enterprise_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNoombre() {
        return numero;
    }

    public void setNombre(Integer numero) {
        this.numero = numero;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(Integer enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
