/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvSUS", urlPatterns = {"/SvSUS"})
public class SvSUS extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipoSUS = request.getParameter("TipoSUS");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        System.out.println("=======================================");
        System.out.println("ENTRE A SvSUS");
        System.out.println("=======================================");
        if(tipoSUS == null || tipoSUS.isEmpty()) {
            out.write("<option value=''>Tipo de suscripción no válido</option>");
        } else if(tipoSUS.equalsIgnoreCase("Semanal")) {
            out.write("<option value='" +4.99+ "'>4.99 US$</option>");
        } else if(tipoSUS.equalsIgnoreCase("Mensual")) {
            out.write("<option value='" +9.99+ "'>9.99 US$</option>");
        } else if(tipoSUS.equalsIgnoreCase("Anal")) {
            out.write("<option value='" + 79.99 + "'>79.99 US$</option>");
        } else {
            out.write("<option value=''>Tipo de suscripción no reconocido</option>");
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
//        ACA
//        El caso de uso comienza cuando el Cliente quiere contratar una
//        suscripción. Para ello el Sistema muestra las opciones de suscripción
//        (semanal, mensual o anual) y el cliente elige una. El sistema muestra el
//        monto total correspondiente a la suscripción, y si el Cliente confirma se
//        crea una suscripción con fecha actual del Sistema en estado = “Pendiente”
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
