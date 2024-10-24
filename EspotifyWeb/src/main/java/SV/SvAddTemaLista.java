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
@WebServlet(name = "SvAddTemaLista", urlPatterns = {"/SvAddTemaLista"})
public class SvAddTemaLista extends HttpServlet {
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
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        response.setContentType("text/html;charset=UTF-8");
            // Obtener la sesión y el nick del usuario logueado
            HttpSession session = request.getSession(false);
            String nickSesion = (String) session.getAttribute("NickSesion");

            if (nickSesion != null) {                
                // Obtener las listas del cliente desde la lógica
                List<String> NomListUs = ctrl.obtenerNombresDeListPart(nickSesion);
                //obtenerPartPublicaDeDuenio  Obtener lista de clientes y pasarle ese para que valla cambiando
                //obtenerTemasDeAlbum Selecciona un album y consigo sus temas
                
                //obtenerNombresListasPDTODO Obtengo todas las listas por defecto
                //obtenerTemasDePD Obtengo temas de lista por defecto que selecione
               
                // Guardar la lista de nombres en el request para usar en el JSP
                request.setAttribute("nombresListas", NomListUs);

                // Redirigir al JSP usando RequestDispatcher
                request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response);
            } else {
                // Si no hay sesión, redirigir al login o página de error
                response.sendRedirect(request.getContextPath() + "/index.jsp");
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
