package ws;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.Catalogo;


@Path("catalogos")
public class MobileWS {
    
    @Context
    private UriInfo context;
    
    @Path("bycategoria/{idCategoria}")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalogo> getByCategoria(@PathParam("idCategoria") Integer idCategoria )
    {
        List<Catalogo> catalogos = null;
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
