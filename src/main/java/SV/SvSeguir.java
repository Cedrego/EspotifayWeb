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
@WebServlet(name = "SvSeguir", urlPatterns = {"/SvSeguir"})
public class SvSeguir extends HttpServlet {
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
        /*
        HttpSession session = request.getSession(false);
        String nickname = (String) session.getAttribute("NickSesion");
        List<String> listaClientes = ctrl.obtenerNombresDeCliente();
        List<String> listaArtistas = ctrl.obtenerNombresDeArtista();

        for (String nick : ctrl.listaSeguidoresClienteSW(nickname)) {
            listaClientes.remove(nick);
        }

        for (String nick : ctrl.listaSeguidoresClienteSW(nickname)) {
            listaArtistas.remove(nick);
        }

        // Configurar las listas como atributos en la solicitud
        request.setAttribute("listClientes", listaClientes);
        request.setAttribute("listArtistas", listaArtistas);

        // Redirigir a la JSP
        request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
        */
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
        // Obtener las listas desde tu controlador o servicio
        HttpSession session = request.getSession(false);
        String nickname = (String) session.getAttribute("NickSesion");
        String tipoUsuario = request.getParameter("tipoUsuario");
        
        
        List<String> listaClientes = ctrl.obtenerNombresDeCliente();
        List<String> listaArtistas = ctrl.obtenerNombresDeArtista();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(tipoUsuario!=null){
            if(tipoUsuario.equalsIgnoreCase("Artista")){
                for(String art : ctrl.listaSeguidoresClienteSW(nickname)){
                    if(listaArtistas.contains(art)){
                        listaArtistas.remove(art);
                    }
                }
                for(String artFiltrado : listaArtistas){
                    out.write("<option value='" + artFiltrado + "'>" + artFiltrado + "</option>");
                }
            }

            if(tipoUsuario.equalsIgnoreCase("Cliente")){
                for(String cli : ctrl.listaSeguidoresClienteSW(nickname)){
                    if(listaClientes.contains(cli)){
                        listaClientes.remove(cli);
                    }
                }
                listaClientes.remove(nickname);
                for(String cliFiltrado : listaClientes){
                    out.write("<option value='" + cliFiltrado + "'>" + cliFiltrado + "</option>");
                }
            }
        }
        
        // Redirigir a la JSP
        //request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
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
        String nickname = (String) session.getAttribute("NickSesion");
        String tipoUsuario = request.getParameter("tipoUsuario");
        String usuario = request.getParameter("usuario");
        boolean isSuS = false;
        for (DataSuscripcion sus : ctrl.getDataClienteAlt(nickname).getDataSuscripcion()) {
            if (sus.getEstado().name().equals("Vigente")) {
                isSuS = true;
            }
        }
        if (isSuS) {
        System.out.println("Estoy en el servlet");
        if (tipoUsuario.equalsIgnoreCase("Artista")) {
            System.out.println("Entro en seguir artista");
            ctrl.seguirPerfil(nickname, "Artista", usuario);
        } else {
            System.out.println("Entro en seguir cliente");
            ctrl.seguirPerfil(nickname, "Cliente", usuario);
        }
        
        request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
        }else {
            request.setAttribute("errorMessage", "La suscripción no está vigente. No puedes dejar de seguir a otros usuarios.");
            request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
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
