/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

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
 * @author camin
 */
@WebServlet(name = "SvDejarSeguir", urlPatterns = {"/SvDejarSeguir"})
public class SvDejarSeguir extends HttpServlet {
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
//        response.setContentType("text/html;charset=UTF-8");
//        HttpSession session = request.getSession(false);
//        String nickname = (String) session.getAttribute("NickSesion");
//        
//        List<String> listClientes = ctrl.listaSeguidoresClienteSW(nickname);
//
//        request.setAttribute("listClientes", listClientes);
//
//        // Redirigir a la JSP
//        request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
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
        
        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String nickname = (String) session.getAttribute("NickSesion");
        
        List<String> listClientes = ctrl.listaSeguidoresClienteSW(nickname);

        request.setAttribute("listClientes", listClientes);

        //Redirigir a la JSP
        request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
        
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
        
        response.setContentType("text/html;charset=UTF-8");
        List<String> listClientes = new ArrayList<>();
        HttpSession session = request.getSession(false);
        String nickname = (String) session.getAttribute("NickSesion");
        String perfil = request.getParameter("perfil");
        
        if(ctrl.obtenerNombresDeCliente().contains(perfil)){
            ctrl.dejarSeguirPerfil(nickname, "Cliente", perfil);
        }else{
            ctrl.dejarSeguirPerfil(nickname, "Artista", perfil);
        }
        
        request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
        
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
