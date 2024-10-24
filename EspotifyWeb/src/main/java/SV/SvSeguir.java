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
        List<String> listClientes = new ArrayList<>();
        List<String> listArtistas = new ArrayList<>();

        if (listClientes.isEmpty()) {
            System.out.println("listClientes in empty");
            listClientes = ctrl.obtenerNombresDeCliente();
        } else {
            System.out.println("listClientes else");
        }

        if (listArtistas.isEmpty()) {
            System.out.println("listArtistas in empty");
            listArtistas = ctrl.obtenerNombresDeArtista();
        } else {
            System.out.println("listArtistas else");
        }

        // Configurar las listas como atributos en la solicitud
        request.setAttribute("listClientes", listClientes);
        request.setAttribute("listArtistas", listArtistas);

        // Redirigir a la JSP
        request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
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
        List<String> listaClientes = new ArrayList<>();
        List<String> listaArtistas = new ArrayList<>();
        
        for(String nick : ctrl.obtenerNombresDeCliente()){
            if(ctrl.listaClientesQueSiguesSW(nickname).contains(nick)){
                //omitir
            }else if (nick.equals(nickname)){
                //omitir
            }else{
                listaClientes.add(nick);
            }
        }
        
        for(String nick : ctrl.obtenerNombresDeArtista()){
            if(ctrl.listaArtistasQueSiguesSW(nickname).contains(nick)){
                listaArtistas.remove(nick);
            }else{
                listaArtistas.add(nick);
            }
        }
        
        listaArtistas = ctrl.obtenerNombresDeArtista();
        
        
        // Configurar las listas como atributos en la solicitud
        request.setAttribute("listClientes", listaClientes);
        request.setAttribute("listArtistas", listaArtistas);
        
        // Redirigir a la JSP
        request.getRequestDispatcher("JSP/Seguir.jsp").forward(request, response);
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
        String cliente = request.getParameter("cliente");
        String artista = request.getParameter("artista");
        String lista = request.getParameter("tipo");
        List<String> listClientes = ctrl.obtenerNombresDeCliente(); // Asegúrate que esta línea funciona correctamente
        List<String> listArtistas = ctrl.obtenerNombresDeArtista(); // Verifica también esta línea
        
        System.out.println("=============================");
        System.out.println("CUENTA: " + nickname);
        System.out.println("CLIENTE: "+ cliente);
        System.out.println("ARTISTA: "+ artista);
        System.out.println("TIPO SEGUIR: "+ lista);
        
        if(cliente == null && artista == null){
            System.out.println("NO SELECCIONO USUARIO");
        }else{
            if (lista != null) {
                ctrl.seguirPerfil(nickname, "Artista", artista);
            } else {
                ctrl.seguirPerfil(nickname, "Cliente", cliente);
            }
        }
        doGet(request, response);
        response.sendRedirect("JSP/Seguir.jsp");
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
