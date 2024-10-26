/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataAlbum;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            }
        }
        
        if(nombreAlbum!=null){
            DataAlbum da = ctrl.obtenerDataAlbum(nombreAlbum);
            request.setAttribute("datosAlbum", da);
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
