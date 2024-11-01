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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
/**
 *
 * @author tecnologo
 */
@WebServlet(name = "SvActualizarSUS", urlPatterns = {"/SvActualizarSUS"})
public class SvActualizarSUS extends HttpServlet {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();
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
     * @throws IOException if an I/O error occursActualizarSUS
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession sesion = request.getSession(false);
            String NickUsu = (String) sesion.getAttribute("NickSesion");
            // Obtener cliente y sus suscripciones
            DataClienteAlt cliente = ctrl.getDataClienteAlt(NickUsu);
            request.setAttribute("suscripciones", cliente.getDataSuscripcion());

            // Reenviar a la página JSP
            request.getRequestDispatcher("JSP/ActualizarSUS.jsp").forward(request, response);
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
        Map<String, String[]> parameterMap = request.getParameterMap();
        HttpSession sesion = request.getSession(false);
        String NickUsu = (String) sesion.getAttribute("NickSesion");
        sesion.removeAttribute("suscripciones");
        // Iterar sobre las suscripciones y actualizar los estados
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("nuevoEstado_")) {
                Long suscripcionId = Long.parseLong(paramName.split("_")[1]);
                String nuevoEstado = request.getParameter(paramName);

                // Solo actualizar si el nuevo estado no es vacío
                if (!"".equals(nuevoEstado)) {
                    // Llamada al servicio para actualizar el estado
                    ctrl.actualizarEstado(suscripcionId, nuevoEstado);
                }
            }
        }
        
        // Obtener cliente y sus suscripciones
        DataClienteAlt cliente = ctrl.getDataClienteAlt(NickUsu);
        request.setAttribute("suscripciones", cliente.getDataSuscripcion());
        // Redirige de vuelta a la página de suscripciones
        request.getRequestDispatcher("JSP/ActualizarSUS.jsp").forward(request, response);
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
