/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author camin
 */
@WebServlet(name = "DmIngreso", urlPatterns = {"/DmIngreso"})
public class DmIngreso extends HttpServlet {
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DmIngreso</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DmIngreso at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
        String NOE = request.getParameter("NOE");
        String Contra = request.getParameter("pass");
        HttpSession misesion = request.getSession();
        if (ctrl.obtenerNombresDeCliente().contains(NOE) || ctrl.obtenerMailDeCliente().contains(NOE)) {//Verifico si ingreso bien el nick(DESPUES VER PARA EMAIL)
            if (ctrl.existePassC(NOE, Contra)) {//Verifico si ingreso bien el pass
                List<String> sesion = ctrl.ContraXCliente(NOE, Contra);
                misesion.setAttribute("NickSesion", sesion.getFirst());
                response.sendRedirect("JSP/Cliente.jsp");
            } else {
                String error = "ERROR: El nick, email o contraseña no son validos";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
            }
        } else if (ctrl.obtenerNombresDeArtista().contains(NOE) || ctrl.obtenerMailDeArtista().contains(NOE)) {
            if (ctrl.existePassA(NOE, Contra)) {//Verifico si ingreso bien el pass
                String error = "ERROR: El nick, email o contraseña no son validos";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
            } else {
                String error = "ERROR: El nick, email o contraseña no son validos";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
            }
        } else {
            String error = "ERROR: El nick, email o contraseña no son validos";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
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
