/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataAlbum;
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
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvConsultarAlbum", urlPatterns = {"/SvConsultarAlbum"})
public class SvConsultarAlbum extends HttpServlet {
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
            out.println("<title>Servlet SvConsultarAlbum</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvConsultarAlbum at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String filtroBusqueda = request.getParameter("filtroBusqueda");
        String artGen = request.getParameter("ArtGen");
        String nombreAlbum = request.getParameter("NombreAlbum");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(filtroBusqueda.equalsIgnoreCase("Artista")){  
            if(nombreAlbum==null){
                if(artGen==null){
                    for (String art : ctrl.obtenerNombresDeArtistaConAlbum()){
                        out.write("<option value='" + art + "'>" + art + "</option>");
                    }
                }else{
                    if(ctrl.obtenerAlbumesDeArtista(artGen)!=null){
                        for(String alb : ctrl.obtenerAlbumesDeArtista(artGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre del album:"+nombreAlbum);
                DataAlbum da = ctrl.obtenerDataAlbum(nombreAlbum);

                // crear una respuesta HTML
                StringBuilder albumData = new StringBuilder();
                albumData.append("<h3>Información del Álbum</h3>")
                         .append("<p>Nombre: ").append(da.getNombre()).append("</p>")
                         .append("<p>Año de creación: ").append(da.getCreacion()).append("</p>")
                         .append("<p>Géneros: ").append(String.join(" || ", da.getGeneros())).append("</p>")
                         .append("<h4>Temas:</h4>");

                for (DataTema tema : da.getDataTemas()) {
                    albumData.append("<p>")
                            .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                            .append("Nombre: ").append(tema.getNombre()).append(" - ")
                            .append("Duración: ").append(tema.getDuracion()).append(" mins")
                            .append(" - <a href='").append(tema.getDireccion()).append("' download>")
                            .append("<button>Descargar</button></a>")
                            .append("</p>");
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(albumData.toString());
            }
        }
        
        if(filtroBusqueda.equalsIgnoreCase("Genero")){
            if(nombreAlbum==null){
                if(artGen==null){
                    for (String gen : ctrl.obtenerNombresDeGenerosConAlbum()){
                        if(gen.equalsIgnoreCase("Genero")){

                            }else{
                                out.write("<option value='" + gen + "'>" + gen + "</option>");
                            }
                    }
                }else{
                    if(ctrl.obtenerAlbumesDeGenero(artGen)!=null){
                        for(String alb : ctrl.obtenerAlbumesDeGenero(artGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                
                System.out.println("Nombre del album:"+nombreAlbum);
                DataAlbum da = ctrl.obtenerDataAlbum(nombreAlbum);
                String imagen = request.getContextPath()+"/"+da.getPic();
                // crear una respuesta HTML
                StringBuilder albumData = new StringBuilder();
                albumData.append("<h3>Información del Álbum</h3>")
                         .append("<p>Nombre: ").append(da.getNombre()).append("</p>")
                         .append("<p>Imagen: <img src='").append(imagen).append("' alt='Imagen del Álbum' style='width:200px;height:auto;'/></p>")
                         .append("<p>Año de creación: ").append(da.getCreacion()).append("</p>")
                         .append("<p>Géneros: ").append(String.join(" || ", da.getGeneros())).append("</p>")
                         .append("<h4>Temas:</h4>");

                for (DataTema tema : da.getDataTemas()) {
                    albumData.append("<p>")
                             .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                             .append("Nombre: ").append(tema.getNombre()).append(" - ")
                             .append("Duración: ").append(tema.getDuracion()).append(" mins")
                             .append("</p>");
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(albumData.toString());

            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
