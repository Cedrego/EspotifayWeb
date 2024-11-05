/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

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
import java.util.List;

/**
 *
 * @author tecnologo
 */
@WebServlet(name = "SvBuscador", urlPatterns = {"/SvBuscador"})
public class SvBuscador extends HttpServlet {
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
            out.println("<title>Servlet SvBuscador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvBuscador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        response.setContentType("text/html;charset=UTF-8");

        if (query != null && !query.trim().isEmpty()) {
            List<String> albums = ctrl.buscadorAlbum(query);
            List<DataTema> temas = ctrl.buscadorTema(query);
            List<String> part = ctrl.buscadorPart(query);
            List<String> pd = ctrl.buscadorPD(query);
            try (PrintWriter out = response.getWriter()) {
                if(!albums.isEmpty()){
                    out.println("<h3>Álbumes</h3>");
                    for (String album : albums) {
                        out.println("<p>" + album + "</p>");
                    }
                }

                
                if (!temas.isEmpty()) {
                    out.println("<h3>Temas</h3>");
                    for (DataTema tema : temas) {
                        out.println("<p onclick=\"reproducirTema('" + tema.getDireccion() + "')\">" + tema.getNombre() + "</p>");
                        }
                } else {
                    out.println("<h3>No se encontraron temas para la búsqueda.</h3>");
                }
                
                if(!part.isEmpty()||!pd.isEmpty()){
                    out.println("<h3>Listas de Reproducción</h3>");
                    for (String parti : part) {
                        out.println("<p>" + parti + "</p>");
                    }
                    for (String pordef : pd) {
                        out.println("<p>" + pordef + "</p>");
                    }
                }
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
