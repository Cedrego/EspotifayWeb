/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvRankingCanciones", urlPatterns = {"/SvRankingCanciones"})
public class SvRankingCanciones extends HttpServlet {
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
            out.println("<title>Servlet SvRankingCanciones</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvRankingCanciones at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> ranking = ctrl.obtenerRankingCanciones();
        
        int posicion = 1;
        for (String rank : ranking) {
            //en este string, los datos se guardan tal que: 
            //[0-Nombre Tema] [1-Nombre Album] [2-puntaje total]
            //[3-Reproducciones] [4-Descargas] [5-Favoritos] [6-Listas]
            String[] data = rank.split("\\|");
            String nombre = data[0];
            String album = data[1];
            String reproducciones = data[3];
            String descargas = data[4];
            String favoritos = data[5];
            String listas = data[6];
            
            out.println("<tr onclick=\"mostrarDetallesCancion('" + nombre + "','"+album+"')\">");
            out.println("<td style='text-align:center;'>" + posicion + "</td>");
            out.println("<td style='text-align:center;'>" + nombre + "</td>");
            out.println("<td style='text-align:center;'>" + album + "</td>");
            out.println("<td style='text-align:center;'>" + reproducciones + "</td>");
            out.println("</tr>");
            
            // Fila adicional para los detalles del usuario, inicialmente oculta
            out.println("<tr id='detallesCancion" + nombre + album + "' style='display:none;'>");
            StringBuilder datosCancion = new StringBuilder();

            datosCancion.append("<div style='display:flex; flex-direction:column; align-items:left; text-align:left; gap:0px; padding:0px;'>")
                    .append("<p><strong>Reproducciones:</strong> ").append(reproducciones).append("</p>")
                    .append("<p><strong>Descargas:</strong> ").append(descargas).append("</p>")
                    .append("<p><strong>Favoritos:</strong> ").append(favoritos).append("</p>")
                    .append("<p><strong>Listas:</strong> ").append(listas).append("</p>")
                    .append("<p><button>Reproducir</button></p>")
                    .append("</div>");
            
            out.print("<td colspan='4' style='text-align:left;'>" + datosCancion.toString() + "</td>");
            out.println("</tr>");
            posicion++;
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
