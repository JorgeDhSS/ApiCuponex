/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

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
import pojos.RespuestaLogin;

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
    
    @Path("create/user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createUser(
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
    
    @Path("update/user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updateUser(
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
    
    @Path("delete/user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deleteUser(
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
