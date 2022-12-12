/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.UserSystem;
import pojos.Empresa;
import pojos.Sucursal;
import pojos.Respuesta;

/**
 *
 * @author Jorge
 */
@Path("admin")
public class AdminWs {
    @Context
    private UriInfo context;//propiedad global de información compartida entre los servicios (información de AplicationConfig)
    
    
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta login(
            @FormParam("correo") String correo,
            @FormParam("password") String password)
    {
        UserSystem user = new UserSystem();
        user.setCorreo(correo);
        user.setPassword(password);
        
        Respuesta respuesta = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<Empresa> medicos = conexionDB.selectList("user_system.login", user);
                conexionDB.commit();
                if(medicos.size() > 0)
                {
                    respuesta.setError(false);
                    respuesta.setMensaje("Login correcto");
                }
                else
                {
                    respuesta.setError(true);
                    respuesta.setMensaje("Login incorrecto");
                }
            }
            catch(Exception e)
            {
                respuesta.setError(false);
                respuesta.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo comprobar la información");
        }
        return respuesta;
    }
    
    @Path("user/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createUser(
            @FormParam("nombre") String nombre,
            @FormParam("apellidoP") String apellidoP,
            @FormParam("apellidoM") String apellidoM,
            @FormParam("correo") String correo,
            @FormParam("password") String password
    )
    {
        UserSystem newUser = new UserSystem(nombre, apellidoP, apellidoM, correo, password, 1);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int resultado = conexionDB.insert("user_system.registrar", newUser);
                conexionDB.commit();
                if(resultado > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("El usuario ha sido registrado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar el usuario");
                }
            }
            catch(Exception e)
            {
                respuestaWs.setError(false);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("No se ha podido registrar el usuario");
        }
        return respuestaWs;
    }
    
    @Path("user/subirFoto/{idUsuario}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirFoto(byte[] foto, @PathParam("idUsuario") Integer idUsuario)
    {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(Boolean.TRUE);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                HashMap<String, Object> parametros = new HashMap<>(); 
                parametros.put("foto", foto);
                parametros.put("idUsuario", idUsuario);
                int rowsAfected = conexionDB.update("user_system.subirFoto", parametros);
                conexionDB.commit();
                if(rowsAfected > 0)
                {
                    respuesta.setError(false);
                    respuesta.setMensaje("Foto guardada correctamente");
                }
                else
                {
                    respuesta.setMensaje("Foto no guardada");
                }
            }
            catch (Exception e)
            {
                respuesta.setMensaje(e.getMessage());
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        else
        {
            respuesta.setMensaje("Por el momento no hay conexión");
        }
        return respuesta;
    }
    
    @Path("user/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserSystem> userAll()
    {
        List<UserSystem> listaUsuarios = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                listaUsuarios = conexionDB.selectList("user_system.all");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                conexionDB.close();
            }
        }
        return listaUsuarios;
    }
    
    @Path("user/byId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserSystem userData(@PathParam("id") Integer idUsuario)
    {
        UserSystem usuario = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                usuario = conexionDB.selectOne("user_system.getUser", idUsuario);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                conexionDB.close();
            }
        }
        return usuario;
    }

    @Path("user/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updateUser(
            @FormParam("nombre") String nombre,
            @FormParam("apellidoP") String apellidoP,
            @FormParam("apellidoM") String apellidoM,
            @FormParam("password") String password,
            @FormParam("idUsuario") Integer idUsuario
    )
    {
        Respuesta respuestaWs = new Respuesta();
        UserSystem newUser = new UserSystem();
        newUser.setIdUsuario(idUsuario);
        newUser.setNombre(nombre);
        newUser.setApellidoP(apellidoP);
        newUser.setApellidoM(apellidoM);
        newUser.setPassword(password);
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("user_system.actualizar", newUser);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("El usuario se ha modificado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("El usuario no se ha modificado");
                }
            }
            catch (Exception e)
            {
                respuestaWs.setError(true);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("El usuario no se ha modificado");
        }
        return respuestaWs;
    }
    
    @Path("user/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deleteUser(@FormParam("idUsuario") Integer idUsuario)
    {
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("user_system.eliminar", idUsuario);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("El usuario se ha eliminado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("El usuario no se ha eliminado");
                }
            }
            catch (Exception e)
            {
                respuestaWs.setError(true);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("El usuario no se ha eliminado");
        }
        return respuestaWs;
    }
    
    @Path("enterprise/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createEnterprise(
            @FormParam("nombre") String nombre,
            @FormParam("nombreComercial") String nombreComercial,
            @FormParam("representante") String representante,
            @FormParam("webPage") String webPage,
            @FormParam("rfc") String rfc,
            @FormParam("ciudad") String ciudad,
            @FormParam("cp") String cp,
            @FormParam("calle") String calle,
            @FormParam("numero")Integer numero,
            @FormParam("tel") String tel,
            @FormParam("email") String email)
    {
        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setNombreComercial(nombreComercial);
        empresa.setRepresentante(representante);
        empresa.setWebPage(webPage);
        empresa.setRfc(rfc);
        empresa.setCiudad(ciudad);
        empresa.setCp(cp);
        empresa.setCalle(calle);
        empresa.setNumero(numero);
        empresa.setTel(tel);
        empresa.setEmail(email);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int resultado = conexionDB.insert("empresa.registrar", empresa);
                conexionDB.commit();
                if(resultado > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La empresa ha sido registrada");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar la empresa");
                }
            }
            catch(Exception e)
            {
                respuestaWs.setError(false);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("No se ha podido registrar la empresa");
        }
        return respuestaWs;
    }
    
    @Path("enterprise/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updateEnterprise(
            @FormParam("id") Integer id,
            @FormParam("nombre") String nombre,
            @FormParam("nombreComercial") String nombreComercial,
            @FormParam("representante") String representante,
            @FormParam("webPage") String webPage,
            @FormParam("rfc") String rfc,
            @FormParam("ciudad") String ciudad,
            @FormParam("cp") String cp,
            @FormParam("calle") String calle,
            @FormParam("numero")Integer numero,
            @FormParam("tel") String tel,
            @FormParam("email") String email)
    {
        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setNombreComercial(nombreComercial);
        empresa.setRepresentante(representante);
        empresa.setWebPage(webPage);
        empresa.setRfc(rfc);
        empresa.setCiudad(ciudad);
        empresa.setCp(cp);
        empresa.setCalle(calle);
        empresa.setNumero(numero);
        empresa.setTel(tel);
        empresa.setEmail(email);
        empresa.setId(id);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int respuesta = conexionDB.update("empresa.actualizar", empresa);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La empresa se ha modificado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("La empresa no se ha modificado");
                }
            }
            catch(Exception e)
            {
                respuestaWs.setError(false);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("Conexión fallida");
        }
        return respuestaWs;
    }
    
    @Path("enterprise/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deleteEnterprise(@FormParam("id") Integer id)
    {
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("empresa.eliminar", id);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La empresa se ha eliminado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("La empresa no se ha eliminado");
                }
            }
            catch (Exception e)
            {
                respuestaWs.setError(true);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("La empresa no se ha eliminado");
        }
        return respuestaWs;
    }
    
    @Path("enterprise/search/{string}")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> getByString(@PathParam("string") String string)
    {
        List<Empresa> empresas = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null)
        {
            try
            {
                empresas = conexionBD.selectList("empresa.search", string);
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            } 
            finally 
            {
                conexionBD.close();
            }
        }
        return empresas;
    }
    
    @Path("enterprise/byId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa enterpriseData(@PathParam("id") Integer id)
    {
        Empresa empresa = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                empresa = conexionDB.selectOne("empresa.getEnterprise", id);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                conexionDB.close();
            }
        }
        return empresa;
    }
    
    @Path("sucursal/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createSucursal(
            @FormParam("nombre") String nombre,
            @FormParam("calle") String calle,
            @FormParam("numero")Integer numero,
            @FormParam("cp") String cp,
            @FormParam("colonia") String colonia,
            @FormParam("ciudad") String ciudad,
            @FormParam("tel") String tel,
            @FormParam("latitud") String latitud,
            @FormParam("longitud") String longitud,
            @FormParam("encargado") String encargado,
            @FormParam("enterprise_id") Integer enterprise_id)
    {
        Sucursal sucursal = new Sucursal(nombre, calle, numero, cp, colonia, ciudad, tel, latitud, longitud, encargado, enterprise_id, 1);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int resultado = conexionDB.insert("sucursal.registrar", sucursal);
                conexionDB.commit();
                if(resultado > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La sucursal ha sido registrada");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar la sucursal");
                }
            }
            catch(Exception e)
            {
                respuestaWs.setError(false);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("No se ha podido registrar la sucursal");
        }
        return respuestaWs;
    }
    
    @Path("sucursal/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updateSucursal(
            @FormParam("id") Integer id,
            @FormParam("nombre") String nombre,
            @FormParam("calle") String calle,
            @FormParam("numero")Integer numero,
            @FormParam("cp") String cp,
            @FormParam("colonia") String colonia,
            @FormParam("ciudad") String ciudad,
            @FormParam("tel") String tel,
            @FormParam("latitud") String latitud,
            @FormParam("longitud") String longitud,
            @FormParam("encargado") String encargado,
            @FormParam("enterprise_id") Integer enterprise_id)
    {
            
        Sucursal sucursal = new Sucursal(id, nombre, calle, numero, cp, colonia, ciudad, tel, latitud, longitud, encargado, enterprise_id);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int respuesta = conexionDB.update("sucursal.actualizar", sucursal);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La sucursal se ha modificado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("La sucursal no se ha modificado");
                }
            }
            catch(Exception e)
            {
                respuestaWs.setError(false);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
                conexionDB.close();
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("Conexión fallida");
        }
        return respuestaWs;
    }
    
    @Path("sucursal/search/{string}")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> searchSucursal(@PathParam("string") String string)
    {
        List<Sucursal> sucursales = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null)
        {
            try
            {
                sucursales = conexionBD.selectList("sucursal.search", string);
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            } 
            finally 
            {
                conexionBD.close();
            }
        }
        return sucursales;
    }
    
    @Path("sucursal/byId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Sucursal sucursalData(@PathParam("id") Integer id)
    {
        Sucursal sucursal = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                sucursal = conexionDB.selectOne("sucursal.byId", id);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                conexionDB.close();
            }
        }
        return sucursal;
    }
    
    @Path("sucursal/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deleteSucursal(@FormParam("id") Integer id)
    {
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("sucursal.eliminar", id);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La sucursal se ha eliminado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("La sucursal no se ha eliminado");
                }
            }
            catch (Exception e)
            {
                respuestaWs.setError(true);
                respuestaWs.setMensaje(e.getMessage());
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        else
        {
            respuestaWs.setError(true);
            respuestaWs.setMensaje("La sucursal no se ha eliminado");
        }
        return respuestaWs;
    }
}
