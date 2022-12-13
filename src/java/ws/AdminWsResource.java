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
import javax.ws.rs.DELETE;
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
import pojos.Empresa;
import pojos.Promocion;
import pojos.Respuesta;
import pojos.Sucursal;
import pojos.UserSystem;

/**
 * REST Web Service
 *
 * @author Jorge
 */
@Path("adminWs")
public class AdminWsResource {

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
                List<UserSystem> usuarios = conexionDB.selectList("user_system.login", user);
                conexionDB.commit();
                if(usuarios.size() > 0)
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
    
    @Path("user/getPhoto/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserSystem userPhoto(@PathParam("id") Integer id)
    {
        UserSystem user = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                user = conexionDB.selectOne("user_system.obtenerFoto", id);
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
    @PUT
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
            respuestaWs.setMensaje("El usuario no se ha modificado, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("user/delete")
    @DELETE
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
            respuestaWs.setMensaje("El usuario no se ha eliminado, error de conexión");
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
            respuestaWs.setMensaje("No se ha podido registrar la empresa, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("enterprise/update")
    @PUT
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
    @DELETE
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
            respuestaWs.setMensaje("La empresa no se ha eliminado, error de conexión");
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
            respuestaWs.setMensaje("No se ha podido registrar la sucursal, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("sucursal/update")
    @PUT
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
    @DELETE
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
            respuestaWs.setMensaje("La sucursal no se ha eliminado, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("promocion/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createPromocion(
            @FormParam("nombre") String nombre,
            @FormParam("fechaIni") String fechaIni,
            @FormParam("fechaFin") String fechaFin,
            @FormParam("descripcion") String descripcion,
            @FormParam("restricciones") String restricciones,
            @FormParam("tipo") Integer tipo,
            @FormParam("porcentaje") Integer porcentaje,
            @FormParam("costo") Integer costo,
            @FormParam("categoria_id")Integer categoria_id,
            @FormParam("enterprise_id") Integer enterprise_id)
    {
        Promocion promo = new Promocion(nombre, fechaIni, fechaFin, descripcion, restricciones, tipo, porcentaje, costo, categoria_id, enterprise_id);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int resultado = conexionDB.insert("promocion.registrar", promo);
                conexionDB.commit();
                if(resultado > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La promoción ha sido registrada");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar la promoción");
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
            respuestaWs.setMensaje("No se ha podido registrar la promoción, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("promocion/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta updatePromocion(
            @FormParam("id") Integer id,
            @FormParam("nombre") String nombre,
            @FormParam("fechaIni") String fechaIni,
            @FormParam("fechaFin") String fechaFin,
            @FormParam("descripcion") String descripcion,
            @FormParam("restricciones") String restricciones,
            @FormParam("tipo") Integer tipo,
            @FormParam("porcentaje") Integer porcentaje,
            @FormParam("costo") Integer costo,
            @FormParam("categoria_id")Integer categoria_id,
            @FormParam("enterprise_id") Integer enterprise_id)
    {
        Promocion promo = new Promocion(id, nombre, fechaIni, fechaFin, descripcion, restricciones, tipo, porcentaje, costo, categoria_id, enterprise_id);
        
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int resultado = conexionDB.insert("promocion.actualizar", promo);
                conexionDB.commit();
                if(resultado > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La promoción ha sido actualizada");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido actualizar la promoción");
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
            respuestaWs.setMensaje("No se ha podido actualizar la promoción, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("promocion/delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta deletePromocion(@FormParam("id") Integer id)
    {
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try
            {
                int respuesta = conexionDB.update("promocion.eliminar", id);
                conexionDB.commit();
                if(respuesta > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La promoción se ha eliminado");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("La promoción no se ha eliminado");
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
            respuestaWs.setMensaje("La promoción no se ha eliminado, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("promocion/search/{string}")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> searchPromocion(@PathParam("string") String string)
    {
        List<Promocion> promociones = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null)
        {
            try
            {
                promociones = conexionBD.selectList("promocion.search", string);
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
    
    @Path("promocion/aisgnarsucursales/{idPromocion}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta createPromocion(
            @PathParam("idPromocion") Integer id,
            @FormParam("sucursales") Integer[] sucursales)
    {
        Respuesta respuestaWs = new Respuesta();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if(conexionDB != null)
        {
            try 
            {
                int counter = 0;
                for(int i=0; i <= sucursales.length; i++)
                {
                    HashMap<String, Object> parametros = new HashMap<>(); 
                    parametros.put("promocion_id", id);
                    parametros.put("sucursal_id", sucursales[i]);
                    int resultado = conexionDB.insert("promocion.asignarSucursal", parametros);
                    conexionDB.commit();
                    if(resultado > 0)
                    {
                        counter++;
                    }
                }
                if(counter > 0)
                {
                    respuestaWs.setError(false);
                    respuestaWs.setMensaje("La promoción se ha registrado en "+counter+" sucursales");
                }
                else
                {
                    respuestaWs.setError(true);
                    respuestaWs.setMensaje("No se ha podido registrar la promoción en las sucursales");
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
            respuestaWs.setMensaje("No se ha podido registrar la promoción en las sucursales, error de conexión");
        }
        return respuestaWs;
    }
    
    @Path("promocion/subirFoto/{idUsuario}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirFotoPromocion(byte[] foto, @PathParam("id") Integer id)
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
                parametros.put("id", id);
                int rowsAfected = conexionDB.update("promocion.subirFoto", parametros);
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
}
