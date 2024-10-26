/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataArtistaAlt;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cedre
 */
@WebServlet(name = "SvConsultarPerfil", urlPatterns = {"/SvConsultarPerfil"})
public class SvConsultarPerfil extends HttpServlet {
    Factory fabric = Factory.getInstance();
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
            String nU = (String) request.getAttribute("NickUsuario");
            if(ctrl.obtenerNombresDeCliente().contains(nU)){//Es Cliente
                request.setAttribute("DataCliente",ctrl.getDataClienteAlt(nU));
            }else if(ctrl.obtenerNombresDeArtista().contains(nU)){//Es Artista
                request.setAttribute("DataArtista",ctrl.getDataArtistaAlt(nU));
            }else{//Es Invitado
                List<DataArtistaAlt>DataArtistas = new ArrayList();
                for(String nA : ctrl.obtenerNombresDeArtista()){
                    DataArtistas.add(ctrl.getDataArtistaAlt(nA));
                }
                request.setAttribute("DataArtistas",DataArtistas);
                request.setAttribute("DataClientes",ctrl.getDataClienteMin());
            }
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
        /*HttpSession misesion = request.getSession();
        List<String>sesion = ctrl.ContraXCliente(NOE,Contra);
        misesion.setAttribute("NickSesion",sesion.getFirst());*/
//        String nick = ctrl.
//        nickname, correo electrónico, nombre, apellido, fecha de nacimiento;
        
        processRequest(request, response);
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
