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
            out.println("<style>body { color: white; background-color: #000; }</style>");
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
            List<String> clientes = ctrl.buscadorCliente(query);
            List<String> artistas = ctrl.buscadorArtista(query);
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Resultados de Búsqueda</title>");
                out.println("<style>body { color: white; background-color: #000; }</style>");
                out.println("<script type='text/javascript'>");
                out.println("function mostrarDetallesCliente(clienteNick) {");
                out.println("   var xhr = new XMLHttpRequest();");
                out.println("   var contextPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));");
                out.println("   xhr.open('GET', contextPath + '/SvDetallesUsuario?tipo=cliente&nickUsuario=' + clienteNick + '&_=' + new Date().getTime(), true);");
                out.println("   xhr.onreadystatechange = function() {");
                out.println("       if (xhr.readyState == 4 && xhr.status == 200) {");
                out.println("           var detailsContainer = document.getElementById('detalles_' + clienteNick);");
                out.println("           detailsContainer.innerHTML = xhr.responseText;");
                out.println("           detailsContainer.style.display = 'block';");
                out.println("       }");
                out.println("   };");
                out.println("   xhr.send();");
                out.println("}");
                out.println("</script>");
                out.println("</head>");
                out.println("<body>");

                if (artistas.isEmpty() && clientes.isEmpty() && albums.isEmpty() && temas.isEmpty() && part.isEmpty() && pd.isEmpty()) {
                    out.println("<h3>No se encontraron resultados para la búsqueda.</h3>");
                } else {
                    if (!artistas.isEmpty()) {
                        out.println("<h3>Artistas</h3>");
                        for (String art : artistas) {
                            out.println("<p>" + art + "</p>");
                        }
                    }

                    if (!clientes.isEmpty()) {
                        out.println("<h3>Clientes</h3>");
                        for (String cli : clientes) {
                            out.println("<p id='cliente_" + cli + "' onclick='mostrarDetallesCliente(\"" + cli + "\")' style='cursor: pointer; color: lightblue;'>" + cli + "</p>");
                            out.println("<div id='detalles_" + cli + "' style='display: none; padding-left: 20px;'></div>");
                        }
                    }

                    if(!albums.isEmpty()){
                        //estilo
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
                out.println("</body>");
                out.println("</html>");
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
