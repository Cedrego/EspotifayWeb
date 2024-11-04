/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tecnologo
 */
@WebServlet(name = "SvPublicarLista", urlPatterns = {"/SvPublicarLista"})
public class SvPublicarLista extends HttpServlet {
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
            out.println("<title>Servlet SvPublicarLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvPublicarLista at " + request.getContextPath() + "</h1>");
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
            HttpSession session = request.getSession(false);
            String nickSesion = (String) session.getAttribute("NickSesion");
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.write("<option value=''>Seleccionar Lista</option>");
            List<String> ExistParticular = new ArrayList();

            for (String list : ctrl.obtenerPartPrivadaDeDuenio(nickSesion)) {
                //if(ctrl.esPrivado(list,nickSesion)){
                    ExistParticular.add(list);
                    out.write("<option value='" + list + "'>" + list + "</option>");
                //}
            } 
            if(ExistParticular.isEmpty()){
                out.write("<option value=''>No hay Lista disponibles</option>");
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
            HttpSession session = request.getSession(false);
            String nickSesion = (String) session.getAttribute("NickSesion");
            String Lista= request.getParameter("listaPrivada");
            boolean Exist =false;
                for(DataSuscripcion sus: ctrl.ObtenerSubscClietne(nickSesion)){
                    if(sus.getEstado().toString().equals("Vigente")){
                        Exist=true;
                    }
                }
                if(Exist== false){
                    String error = "ERROR: Suscripcion no vigente";
                    session.setAttribute("error", error);
                   request.getRequestDispatcher("JSP/PublicarLista.jsp").forward(request, response); // Redirige al JSP
                }else{
                    if(ctrl.ExisListPartEnCliente(Lista,nickSesion)){
                        ctrl.Publicar(Lista, nickSesion);
                        request.getRequestDispatcher("JSP/PublicarLista.jsp").forward(request, response); // Redirige al JSP
                    }else{
                        String error = "ERROR: Suscripcion no vigente";
                        session.setAttribute("error", error);
                        request.getRequestDispatcher("JSP/PublicarLista.jsp").forward(request, response); 
                    }
                }           
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
