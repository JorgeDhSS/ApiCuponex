package pojos;

public class Catalogo {
    
    private String idCatalogo;
    private String nombre;

    public Catalogo() {
    }

    public Catalogo(String idCatalogo, String nombre) {
        this.idCatalogo = idCatalogo;
        this.nombre = nombre;
    }
    

    public String getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(String idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
