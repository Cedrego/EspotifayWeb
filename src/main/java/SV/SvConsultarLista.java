/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;


import Capa_Presentacion.DataParticular;
import Capa_Presentacion.DataPorDefecto;
import Capa_Presentacion.DataTema;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvConsultarLista", urlPatterns = {"/SvConsultarLista"})
public class SvConsultarLista extends HttpServlet {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvConsultarLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvConsultarLista at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtroBusqueda = request.getParameter("filtroBusqueda");
        String cliGen = request.getParameter("CliGen");
        String nombreLista = request.getParameter("NombreLista");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(filtroBusqueda.equalsIgnoreCase("Cliente")){  
            if(nombreLista==null){
                if(cliGen==null){
                    for (String cli : ctrl.obtenerNombresClienteConParticular()){
                        out.write("<option value='" + cli + "'>" + cli + "</option>");
                    }
                }else{
                    if(ctrl.obtenerPartPublicaDeDuenio(cliGen)!=null){
                        for(String lis : ctrl.obtenerPartPublicaDeDuenio(cliGen)){
                            out.write("<option value='" + lis + "'>" + lis + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre de la lista:"+nombreLista);
                System.out.println("Cliente duenio:"+cliGen);
                DataParticular dp = ctrl.obtenerDataParticular(nombreLista,cliGen);
                if (dp == null) {
                    System.out.println("Error: dp es null.");
                } else if (dp.getDataTemas() == null) {
                    System.out.println("Error: dp.getDataTemas() es null.");
                } else {
                    System.out.println("DataParticular obtenida correctamente.");
                }
                // crear una respuesta HTML
                StringBuilder listaData = new StringBuilder();
                listaData.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");
                
                listaData.append("<h3>Información de la Lista</h3>");
                        String imagen = dp.getImagen();
                        if (imagen == null || imagen.isBlank()) {
                            imagen = "images/noImageSong.png"; // Imagen predeterminada si no hay imagen
                        }
                        String imagenURL = request.getContextPath() + "/" + imagen;

                        listaData.append("")
                        .append("<p><img src=\"").append(imagenURL).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>")
                        .append("<p>Nombre: ").append(dp.getNombre()).append("</p>")
                        .append("<p>Cliente Dueño: ").append(dp.getCliente()).append("</p>")
                        .append("<h4>Temas:</h4>");

                for (DataTema tema : dp.getDataTemas()) {
                    listaData.append("<p>")
                            .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                            .append("Nombre: ").append(tema.getNombre()).append(" - ")
                            .append("Duración: ").append(tema.getDuracion()).append(" mins")
                            .append(" - <a href='")
                            .append(request.getContextPath()) // Esto obtiene el contexto de la aplicación, e.g., /EspotifyWeb
                            .append("/" + tema.getDireccion()) // Ruta completa desde el contexto raíz
                            .append("' download>")
                            .append("<button>Descargar</button></a>")
                            .append("</p>");
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(listaData.toString());
                }
        }
            
        
        
        if(filtroBusqueda.equalsIgnoreCase("Genero")){
            if(nombreLista==null){
                if(cliGen==null){
                    for (String gen : ctrl.obtenerNombresDeGenerosConPorDefecto()){
                        if(gen.equalsIgnoreCase("Genero")){

                            }else{
                                out.write("<option value='" + gen + "'>" + gen + "</option>");
                            }
                    }
                }else{
                    if(ctrl.obtenerListasDeGenero(cliGen)!=null){
                        for(String alb : ctrl.obtenerListasDeGenero(cliGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre de la lista:"+nombreLista);
                DataPorDefecto dpd = ctrl.obtenerDataPorDefecto(nombreLista);

                // crear una respuesta HTML
                StringBuilder listaData = new StringBuilder();
                listaData.append("<h3>Información de la Lista</h3>")
                        .append("<p>Nombre: ").append(dpd.getNombre()).append("</p>")
                        .append("<p>Genero: ").append(dpd.getGenero()).append("</p>")
                        .append("<h4>Temas:</h4>");

                for (DataTema tema : dpd.getDataTemas()) {
                    listaData.append("<p>")
                            .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                            .append("Nombre: ").append(tema.getNombre()).append(" - ")
                            .append("Duración: ").append(tema.getDuracion()).append(" mins")
                            .append(" - <a href='")
                            .append(request.getContextPath()) // Esto obtiene el contexto de la aplicación, e.g., /EspotifyWeb
                            .append("/" + tema.getDireccion()) // Ruta completa desde el contexto raíz
                            .append("' download>")
                            .append("<button>Descargar</button></a>")
                            .append("</p>");
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(listaData.toString());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
