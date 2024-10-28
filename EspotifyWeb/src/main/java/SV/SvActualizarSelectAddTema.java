/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "SvActualizarSelectAddTema", urlPatterns = {"/SvActualizarSelectAddTema"})
public class SvActualizarSelectAddTema extends HttpServlet {
    Factory fabric =Factory.getInstance();
    ICtrl ctrl = fabric.getICtrl();
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvActualizarSelectAddTema</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvActualizarSelectAddTema at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String tipoSeleccion = request.getParameter("tipoDelObjeto");
        System.out.println("Tipo de objeto recibido2: " + tipoSeleccion);
        PrintWriter out = response.getWriter();
        
        if ("Album".equals(tipoSeleccion)) {
            for (String album : ctrl.obtenerNombresDeAlbumes()) {
                out.write("<option value='" + album + "'>" + album + "</option>");
                System.out.println("Album "+album);
            }    
        } else if ("ListaPorDef".equals(tipoSeleccion)) {
            for(String pd : ctrl.obtenerNombresListasPDTODO()){
                out.write("<option value='" + pd + "'>" + pd + "</option>");
            }
        } else if ("ListaPart".equals(tipoSeleccion)) {
                for(String p :  ctrl.obtenerNombresDeCliente()){
                    out.write("<option value='" + p + "'>" + p + "</option>");
                }
        }
        out.flush(); // Asegúrate de que los datos se envíen
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
