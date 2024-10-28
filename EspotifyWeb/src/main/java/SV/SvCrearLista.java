/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataSuscripcion;
import Logica.Factory;
import Logica.ICtrl;
import Logica.Suscripcion;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "SvCrearLista", urlPatterns = {"/SvCrearLista"})
@MultipartConfig(maxFileSize = 16177215) // Tamaño máximo del archivo (15MB)
public class SvCrearLista extends HttpServlet {
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
            out.println("<title>Servlet SvCrearLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvCrearLista at " + request.getContextPath() + "</h1>");
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
        String NombreLista = request.getParameter("NomLista");
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String nickSesion = (String) misesion.getAttribute("NickSesion");
        if(!NombreLista.isEmpty()){
            String error = "ERROR: Escriba un nombre para la lista";
           misesion.setAttribute("error", error);
           request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        }
        /* Obtengo el archivo de imagen
        Part filePart = request.getPart("imagen"); // Obtengo la parte del archivo
        InputStream inputStream = null;
        if (filePart != null) {
            // Obtengo el InputStream de la imagen subida
            inputStream = filePart.getInputStream();
        }
        */
        if(ctrl.ExisListPartEnCliente(NombreLista, nickSesion)){//Return false si es == a null osea no existe  
          String error = "ERROR: Ya existe una lista con ese nombre";
           misesion.setAttribute("error", error);
           request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        }
        boolean Exist =false;
        for(DataSuscripcion sus: ctrl.ObtenerSubscClietne(nickSesion)){
            if(sus.getEstado().toString().equals("Vigente")){
                Exist=true;
            }
        }
        if(Exist== false){
            String error = "ERROR: Suscripcion no vigente";
           misesion.setAttribute("error", error);
           request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        }else{
            // Obtener solo la fecha actual sin la hora
            LocalDate fechaActual = LocalDate.now();
            // Formatear la fecha como String (ejemplo: "dd-MM-yyyy")
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaFormateada = fechaActual.format(formato);
            ctrl.CreateLista(NombreLista, "Particular", nickSesion,fechaFormateada);
            request.getRequestDispatcher("JSP/Cliente.jsp").forward(request, response); // Redirige al JSP
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
