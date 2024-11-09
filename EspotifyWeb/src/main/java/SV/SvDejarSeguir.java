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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String nickname = (session != null) ? (String) session.getAttribute("NickSesion") : null;

        // Obtener la lista de clientes que sigues
        List<String> clientes = ctrl.listaSeguidoresClienteSW(nickname);
        if (!clientes.isEmpty()) {
            for (String cliente : clientes) {
                out.write("<option value='" + cliente + "'>" + cliente + "</option>");
            }
        } else {
            out.write("<option value=''>No se encontraron resultados</option>");
        }
        
        out.flush();
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
        boolean isSuS = false;
        for (DataSuscripcion sus : ctrl.getDataClienteAlt(nickname).getDataSuscripcion()) {
            if (sus.getEstado().name().equals("Vigente")) {
                isSuS = true;
            }
        }
        if (isSuS) {
            System.out.println("PERFIL: "+nickname);
            String perfil = request.getParameter("cliente"); // Aquí se obtiene el cliente correctamente
            System.out.println("ELEGIDO: "+perfil);
            if(perfil != null){
                if(ctrl.obtenerNombresDeCliente().contains(perfil)){
                    ctrl.dejarSeguirPerfil(nickname, "Cliente", perfil);
                    System.out.println("DEJAR SEGUIR CLIENTE: "+nickname+" - "+perfil);
                }else{
                    ctrl.dejarSeguirPerfil(nickname, "Artista", perfil);
                    System.out.println("DEJAR SEGUIR ARTISTA: "+nickname+" - "+perfil);
                }

                request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
            }
        }else {
            request.setAttribute("errorMessage", "La suscripción no está vigente. No puedes dejar de seguir a otros usuarios.");
            request.getRequestDispatcher("JSP/DejarSeguir.jsp").forward(request, response);
        }
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}