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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
       String tipoSeleccion = request.getParameter("tipoSeleccion");
        List<String> opciones = null;

        if ("album".equals(tipoSeleccion)) {
            opciones = ctrl.obtenerNombresDeAlbumes(); // Método para obtener álbumes
        } else if ("listasPorDefecto".equals(tipoSeleccion)) {
            opciones = ctrl.obtenerNombresListasPDTODO(); // Método para obtener listas por defecto
        } else if ("listasPublicas".equals(tipoSeleccion)) {
            opciones = ctrl.obtenerNombresDeCliente(); // Método para obtener listas públicas
        }
        // Crear respuesta JSON manualmente
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        
        if (opciones != null && !opciones.isEmpty()) {
            for (int i = 0; i < opciones.size(); i++) {
                jsonBuilder.append("\"").append(opciones.get(i)).append("\"");
                if (i < opciones.size() - 1) {
                    jsonBuilder.append(","); // Agregar coma entre elementos
                }
            }
        }
        
        jsonBuilder.append("]");

        // Enviar la respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonBuilder.toString()); // Enviar la cadena JSON
        out.flush();    
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
