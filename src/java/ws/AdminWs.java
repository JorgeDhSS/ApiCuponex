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
    
    @Path("create/user")
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
    
    @Path("subirFoto/user/{idUsuario}")
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
    
    @Path("update/user")
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
    
    @Path("delete/user")
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
    
    @Path("create/enterprise")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createEnterprise(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password)
    {
        Empresa medicoNuevo = new Empresa();
        medicoNuevo.setNoPersonal(noPersonal);
        medicoNuevo.setPassword(password);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<Empresa> medicos = conexionDB.selectList("medicos.login", medicoNuevo);
                conexionDB.commit();
                if(medicos.size() > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("Login correcto");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("Login incorrecto");
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
            respuestaWs.setMensaje("No se pudo comprobar la información");
        }
        return respuestaWs;
    }
    
    @Path("update/enterprise")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updateEnterprise(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password)
    {
        Empresa medicoNuevo = new Empresa();
        medicoNuevo.setNoPersonal(noPersonal);
        medicoNuevo.setPassword(password);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<Empresa> medicos = conexionDB.selectList("medicos.login", medicoNuevo);
                conexionDB.commit();
                if(medicos.size() > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("Login correcto");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("Login incorrecto");
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
            respuestaWs.setMensaje("No se pudo comprobar la información");
        }
        return respuestaWs;
    }
    
    @Path("delete/enterprise")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deleteEnterprise(
            @FormParam("noPersonal") String noPersonal,
            @FormParam("password") String password)
    {
        Empresa medicoNuevo = new Empresa();
        medicoNuevo.setNoPersonal(noPersonal);
        medicoNuevo.setPassword(password);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<Empresa> medicos = conexionDB.selectList("medicos.login", medicoNuevo);
                conexionDB.commit();
                if(medicos.size() > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("Login correcto");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("Login incorrecto");
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
            respuestaWs.setMensaje("No se pudo comprobar la información");
        }
        return respuestaWs;
    }
    
    @Path("search/enterprise/{rfc}")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserSystem> getByCategoria(@PathParam("idCategoria") Integer idCategoria )
    {
        List<UserSystem> catalogos = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null)
        {
            try
            {
                catalogos = conexionBD.selectList("catalogos.getCategoria", idCategoria);
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
        return catalogos;
    }
}
