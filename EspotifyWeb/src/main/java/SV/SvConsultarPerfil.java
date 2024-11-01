/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataArtistaAlt;
import Capa_Presentacion.DataClienteAlt;
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

/**
 *
 * @author cedre
 */
@WebServlet(name = "SvConsultarPerfil", urlPatterns = {"/SvConsultarPerfil"})
public class SvConsultarPerfil extends HttpServlet {
     Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        String NickUsu = (String) sesion.getAttribute("NickSesion");

        String tipo = (String) request.getAttribute("tipo");
        Object usuario = request.getAttribute("usuario");
        if (ctrl.obtenerNombresDeCliente().contains(NickUsu)) {
            sesion.setAttribute("usuario",ctrl.getDataClienteAlt(NickUsu));
            sesion.setAttribute("tipo","cliente");
        }else if (ctrl.obtenerNombresDeArtista().contains(NickUsu)) {
            sesion.setAttribute("usuario",ctrl.getDataArtistaAlt(NickUsu));
            sesion.setAttribute("tipo","artista");
        }else{
            sesion.setAttribute("tipo","");
            List<DataArtistaAlt> DTAA = new ArrayList();
            for(String NA : ctrl.obtenerNombresDeArtista()){
                DTAA.add(ctrl.getDataArtistaAlt(NA));
            }
            request.setAttribute("dataArtistas",DTAA);
            request.setAttribute("DataClientes",ctrl.getDataClienteMin());
        }
        // Reenviar a la página JSP
        request.getRequestDispatcher("JSP/ConsultaPerfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
