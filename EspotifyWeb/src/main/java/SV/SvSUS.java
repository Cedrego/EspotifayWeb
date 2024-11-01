/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataClienteAlt;
import Capa_Presentacion.DataSuscripcion;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvSUS", urlPatterns = {"/SvSUS"})
public class SvSUS extends HttpServlet {
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
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipoSUS = (String)request.getParameter("TipoSUS");
        response.setContentType("text/plain"); // Cambiar a texto plano
        PrintWriter out = response.getWriter();

        try {
            if (tipoSUS == null || tipoSUS.isEmpty()) {
                out.write("Tipo de suscripción no válido");
            } else if (tipoSUS.equalsIgnoreCase("Semanal")) {
                out.write("4.99 US$");
            } else if (tipoSUS.equalsIgnoreCase("Mensual")) {
                out.write("9.99 US$");
            } else if (tipoSUS.equalsIgnoreCase("Anal")) {
                out.write("79.99 US$");
            } else {
                out.write("Tipo de suscripción no reconocido");
            }
        } catch (Exception e) {
            out.write("Error interno del servidor");
            e.printStackTrace(); // Para verificar errores en los logs
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        HttpSession misesion = request.getSession(false);
        String NickUsu = (String) misesion.getAttribute("NickSesion");
        DataClienteAlt cliente = ctrl.getDataClienteAlt(NickUsu);
        String error = null;
        for(DataSuscripcion DTSus : cliente.getDataSuscripcion()){
            if(DTSus.getEstado().toString().equals("Vigente") || DTSus.getEstado().toString().equals("Pendiente")){
                if(DTSus.getEstado().toString().equals("Vigente")){
                    error = ("El Usuario ya Cuenta con una Suscripcion Vigente");
                }
                if(DTSus.getEstado().toString().equals("Pendiente")){
                    error = ("El Usuario cuenta con una suscripcion con pago/cancelacion pendiente");
                }
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/SUS.jsp").forward(request, response); 
                return;
            }
        }
        String tipoSus = request.getParameter("TipoSus");
        if(tipoSus == null || NickUsu == null){
            System.out.println("Tipo :" + tipoSus +"/nUsuario: " + NickUsu);
        }
        ctrl.crearSucscripcion(NickUsu,tipoSus);
        request.getRequestDispatcher("JSP/Cliente.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
