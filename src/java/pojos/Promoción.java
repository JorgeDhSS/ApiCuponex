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
public class Promoción {
    private Integer id;
    private String nombre; 
    private String fechaIni;
    private String fechaFin;
    private String descripcion;
    private String restricciones; 
    private Integer tipo;
    private Integer porcentaje;
    private Integer costo;
    private Integer status;
    private Integer categoria;
    private Integer enterprise_id;

    public Promoción() {
    }

    public Promoción(Integer id, String nombre, String fechaIni, String fechaFin, String descripcion, String restricciones, Integer tipo, Integer porcentaje, Integer costo, Integer status, Integer categoria, Integer enterprise_id) {
        this.id = id;
        this.nombre = nombre;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.restricciones = restricciones;
        this.tipo = tipo;
        this.porcentaje = porcentaje;
        this.costo = costo;
        this.status = status;
        this.categoria = categoria;
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

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(Integer enterprise_id) {
        this.enterprise_id = enterprise_id;
    }
}
