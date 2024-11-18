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
            
            out.println("<tr onclick=\"mostrarDetallesUsuario('"+nickname+"','"+tipoUsuario+"')\">");
            out.println("<td>" + posicion + "</td>");
            out.println("<td>" + nickname + "</td>");
            out.println("<td>" + seguidores + "</td>");
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
