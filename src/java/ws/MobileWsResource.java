/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.Categoria;
import pojos.Promocion;
import pojos.Respuesta;
import pojos.UserApp;

/**
 * REST Web Service
 *
 * @author Jorge
 */
@Path("mobileWs")
public class MobileWsResource {

    @Context
    private UriInfo context;

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta login(
            @FormParam("correo") String correo,
            @FormParam("password") String password)
    {
        UserApp user = new UserApp();
        user.setCorreo(correo);
        user.setPassword(password);
        
        Respuesta respuesta = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<UserApp> usuarios = conexionDB.selectList("user_app.login", user);
                conexionDB.commit();
                if(usuarios.size() > 0)
                {
                    respuesta.setError(false);
                    respuesta.setMensaje("Login correcto");
                    respuesta.setUser(usuarios.get(0));
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
            respuesta.setMensaje("No se pudo comprobar la información, error de conexión");
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
            @FormParam("tel") String tel,
            @FormParam("correo") String correo,
            @FormParam("password") String password,
            @FormParam("calle") String calle,
            @FormParam("numero") Integer numero,
            @FormParam("fechaNac") String fechaNac)
    {
        UserApp newUser = new UserApp(nombre, apellidoP, apellidoM, tel, correo, password, calle, numero, fechaNac);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                List<UserApp> usuariosWithMail = conexionDB.selectList("user_app.getUserByEmail", correo);
                conexionDB.commit();
                conexionDB.close();
                SqlSession conexionDB2 = MyBatisUtil.getSession();
                if(conexionDB2 != null)
                {
                    if(usuariosWithMail.size() > 0)
                    {
                        respuestaWs.setError(true);
                        respuestaWs.setMensaje("Este correo ya está en uso");
                    }
                    else
                    {
                        int resultado = conexionDB2.insert("user_app.registrar", newUser);
                        conexionDB2.commit();
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
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar el usuario, error de conexión");
                }
                conexionDB2.close();
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
            respuestaWs.setMensaje("No se ha podido registrar el usuario, error de conexión");
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
                int rowsAfected = conexionDB.update("user_app.subirFoto", parametros);
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
    
    @Path("user/getPhoto/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserApp userPhoto(@PathParam("id") Integer id)
    {
        UserApp user = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                user = conexionDB.selectOne("user_app.obtenerFoto", id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        return user;
    }
    
    @Path("user/byId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserApp userData(@PathParam("id") Integer idUsuario)
    {
        UserApp usuario = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                usuario = conexionDB.selectOne("user_app.getUser", idUsuario);
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
            @FormParam("tel") String tel,
            @FormParam("password") String password,
            @FormParam("calle") String calle,
            @FormParam("numero") Integer numero,
            @FormParam("fechaNac") String fechaNac,
            @FormParam("id") Integer id)
    {
        Respuesta respuestaWs = new Respuesta();
        UserApp newUser = new UserApp(id, nombre, apellidoP, apellidoM, tel, password, calle, numero, fechaNac);

        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("user_app.actualizar", newUser);
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
            respuestaWs.setMensaje("El usuario no se ha modificado, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> buscarTodos()
    {
        List<Categoria> listaCategorias = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                listaCategorias = conexionDB.selectList("promocion.getCategorias");
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
        return listaCategorias;
    }
    
    @Path("promocion/byCategoria/{idCategoria}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> promocionesCategoria(@PathParam("idCategoria") Integer idCategoria)
    {
        List<Promocion> promociones = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null)
        {
            try
            {
                promociones = conexionBD.selectList("promocion.byCategoria", idCategoria);
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
        return promociones;
    }
    
    @Path("promocion/details/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion promocionDetails(@PathParam("id") Integer id)
    {
        Promocion promo = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                promo = conexionDB.selectOne("promocion.details", id);
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
        return promo;
    }
    
    @Path("promocion/getPhoto/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion promocionPhoto(@PathParam("id") Integer id)
    {
        Promocion promo = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                promo = conexionDB.selectOne("promocion.obtenerFoto", id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
               conexionDB.close(); 
            }
        }
        return promo;
    }
}
