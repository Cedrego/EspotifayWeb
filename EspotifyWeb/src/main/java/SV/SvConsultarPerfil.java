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
import java.util.Map;

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

        // Obtener cliente y sus suscripciones
        DataClienteAlt cliente = ctrl.getDataClienteAlt(NickUsu);
        List<DataSuscripcion> suscripciones = cliente.getDataSuscripcion();

        // Pasar la lista de suscripciones como atributo
        request.setAttribute("suscripciones", suscripciones);

        // Reenviar a la página JSP
        request.getRequestDispatcher("JSP/SUS.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();

        // Iterar sobre las suscripciones y actualizar los estados
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("nuevoEstado_")) {
                Long suscripcionId = Long.parseLong(paramName.split("_")[1]);
                String nuevoEstado = request.getParameter(paramName);
                // Llamada al servicio para actualizar el estado
                ctrl.actualizarEstado(suscripcionId, nuevoEstado);
            }
        }

        // Redirige de vuelta a la página de suscripciones
        response.sendRedirect("SvActualizarSUS");
    }
}
