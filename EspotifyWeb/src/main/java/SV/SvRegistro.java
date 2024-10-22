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
@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
public class SvRegistro extends HttpServlet {
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
            out.println("<title>Servlet SvRegistro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvRegistro at " + request.getContextPath() + "</h1>");
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
        String error = null; // Inicializa el mensaje de error como null
        String nick = request.getParameter("nick");
        String nom = request.getParameter("nom");
        String ape = request.getParameter("ape");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        String pass2 = request.getParameter("pass2");
        String web = request.getParameter("web");
        String bio = request.getParameter("bio");
        String fecha = request.getParameter("fech");
        String artista = request.getParameter("esArtista");
        HttpSession sesion = request.getSession();
        List<String> nicknames = new ArrayList<>();
        List<String> mails = new ArrayList<>();
        for (String Nickname : ctrl.obtenerNombresDeCliente()) {
            nicknames.add(Nickname);
        }
        for (String Nickname : ctrl.obtenerNombresDeArtista()) {
            nicknames.add(Nickname);
        }
        for (String Correo : ctrl.obtenerNombresDeCliente()) {
            mails.add(Correo);
        }
        for (String Correo : ctrl.obtenerNombresDeArtista()) {
            mails.add(Correo);
        }
        
        // Verificación de errores
        if (nick == null || nick.isEmpty()) {
            error = "ERROR: campo nickname vacío";
        } else if (nom == null || nom.isEmpty()) {
            error = "ERROR: campo nombre vacío";
        } else if (ape == null || ape.isEmpty()) {
            error = "ERROR: campo apellido vacío";
        } else if (mail == null || mail.isEmpty()) {
            error = "ERROR: campo correo vacío";
        } else if (pass == null || pass.isEmpty()) {
            error = "ERROR: campo contraseña vacío";
        } else if (fecha == null || fecha.isEmpty()) {
            error = "ERROR: campo fecha de nacimiento vacío";
        } else if (nicknames.contains(nick)) {
            error = "ERROR: ese nickname ya está en uso";
        } else if (mails.contains(mail)) {
            error = "ERROR: ese correo ya está en uso";
        } else if (!pass.equals(pass2)) {
            error = "ERROR: las contraseñas no coinciden";
        }
        
        if (artista != null && artista.equals("on")) {
            if (web == null || web.isEmpty()) {
                error = "ERROR: campo página vacío";
            } else if (bio == null || bio.isEmpty()) {
                error = "ERROR: campo biografía vacío";
            }
        }
        
        if (error != null) {
            sesion.setAttribute("error", error);
            request.getRequestDispatcher("Registro.jsp").forward(request, response); // Redirige al JSP
        } else {
            // Lógica para crear usuario
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
