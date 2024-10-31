/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataArtistaAlt;
import Capa_Presentacion.DataClienteAlt;
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
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String nU = (String) misesion.getAttribute("NickSesion");

        if (ctrl.obtenerNombresDeCliente().contains(nU)) { // Es Cliente
            DataClienteAlt cliente = ctrl.getDataClienteAlt(nU);
            request.setAttribute("usuario", cliente);
            request.setAttribute("tipo", "cliente");
        } else if (ctrl.obtenerNombresDeArtista().contains(nU)) { // Es Artista
            DataArtistaAlt artista = ctrl.getDataArtistaAlt(nU);
            request.setAttribute("usuario", artista);
            request.setAttribute("tipo", "artista");
        } else { // Es Invitado
            List<DataArtistaAlt> dataArtistas = new ArrayList();
            for (String nA : ctrl.obtenerNombresDeArtista()) {
                dataArtistas.add(ctrl.getDataArtistaAlt(nA));
            }
            request.setAttribute("dataArtistas", dataArtistas);
            request.setAttribute("DataClientes", ctrl.getDataClienteMin());
        }
        request.setAttribute("Generos",ctrl.obtenerNombresDeGeneros());
        request.getRequestDispatcher("JSP/ConsultaPerfil.jsp").forward(request, response);
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
