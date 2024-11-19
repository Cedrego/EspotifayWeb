/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataClienteAlt;
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
@WebServlet(name = "SvRankingUsuarios", urlPatterns = {"/SvRankingUsuarios"})
public class SvRankingUsuarios extends HttpServlet {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvRankingUsuarios</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvRankingUsuarios at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<String> ranking = ctrl.obtenerRankingUsuarios();
        
        int posicion = 1;
        for (String rank : ranking) {
            String[] data = rank.split("\\|");
            String nickname = data[0];
            String seguidores = data[1];
            String tipoUsuario = data[2];

            out.println("<tr onclick=\"mostrarDetallesUsuario('" + nickname + "')\">");
            out.println("<td style='text-align:center;'>" + posicion + "</td>");
            out.println("<td style='text-align:center;'>" + nickname + "</td>");
            out.println("<td style='text-align:center;'>" + seguidores + "</td>");
            out.println("</tr>");

            // Fila adicional para los detalles del usuario, inicialmente oculta
            out.println("<tr id='detallesUsuario" + nickname + "' style='display:none;'>");
            StringBuilder datosUsuario = new StringBuilder();

            if (tipoUsuario.equalsIgnoreCase("Cliente")) {
                DataClienteAlt DTA = ctrl.getDataClienteAlt(nickname);
                String pic = DTA.getPicture();
                if (pic == null || pic.isBlank()) {
                    pic = "images/profiles/blank.png"; // Imagen predeterminada si no hay imagen
                }

                String imagen = request.getContextPath() + "/" + pic;

                datosUsuario.append("<div style='display:flex; flex-direction:column; align-items:center; text-align:center; gap:10px; padding:10px;'>")
                        .append("<img src=\"").append(imagen).append("\" style=\"width:100px; height:100px; object-fit:cover; border-radius:50%; box-shadow: 0 0 10px #1db954;\"/>")
                        .append("<p><strong>Nombre:</strong> ").append(DTA.getNombre()).append("</p>")
                        .append("<p><strong>Correo:</strong> ").append(DTA.getCorreo()).append("</p>")
                        .append("<p><strong>Fecha de nacimiento:</strong> ").append(DTA.getFecha().getDia()).append("/")
                        .append(DTA.getFecha().getMes()).append("/").append(DTA.getFecha().getAnio()).append("</p>")
                        .append("<div style='margin-top:10px;'>")
                        .append("<p><strong>Listas:</strong></p>")
                        .append("<ul style='list-style:none; padding:0;'>");

                // Mostrar los nombres de las listas
                DTA.getDataPart().forEach(lista -> {
                    datosUsuario.append("<li style='margin:5px 0; background-color:#1db954; color:black; padding:10px; border-radius:5px; font-weight:bold;'>")
                            .append(lista.getNombre())
                            .append("</li>");
                });

                datosUsuario.append("</ul>")
                        .append("</div>")
                        .append("</div>");
            }

            out.print("<td colspan='3' style='text-align:center;'>" + datosUsuario.toString() + "</td>");
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
    }

}
