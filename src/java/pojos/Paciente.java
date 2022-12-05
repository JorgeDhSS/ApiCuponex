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
public class Paciente {
    private Integer idPaciente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String genero;
    private String pesoInicial;
    private String estatura;
    private String tallaInicial;
    private String email;
    private String telefono;
    private String domicilio;
    private String usuario;
    private String password;
    private Integer idMedico;
    private Byte[] foto;
    private Integer idEstatus;

    public Paciente() {
    }

    public Paciente(Integer idPaciente, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String genero, String pesoInicial, String estatura, String tallaInicial, String email, String telefono, String domicilio, String usuario, String password, Integer idMedico, Byte[] foto, Integer idEstatus) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.pesoInicial = pesoInicial;
        this.estatura = estatura;
        this.tallaInicial = tallaInicial;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.usuario = usuario;
        this.password = password;
        this.idMedico = idMedico;
        this.foto = foto;
        this.idEstatus = idEstatus;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(String pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getTallaInicial() {
        return tallaInicial;
    }

    public void setTallaInicial(String tallaInicial) {
        this.tallaInicial = tallaInicial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }
    
    
}
