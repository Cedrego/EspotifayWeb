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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
/*
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espotify</title>
        <script>
            function updateUsers() {
                const checkbox = document.getElementById('tipo');
                const clienteComboBox = document.getElementById('cliente');
                const artistaComboBox = document.getElementById('artista');
                const clienteLabel = document.getElementById('label1');
                const artistaLabel = document.getElementById('label2');

                // Mostrar/ocultar combobox y etiquetas según el estado de la checkbox
                if (checkbox.checked) {
                    clienteComboBox.style.display = 'none';
                    clienteLabel.style.display = 'none';
                    artistaComboBox.style.display = 'inline';
                    artistaLabel.style.display = 'inline';
                } else {
                    clienteComboBox.style.display = 'inline';
                    clienteLabel.style.display = 'inline';
                    artistaComboBox.style.display = 'none';
                    artistaLabel.style.display = 'none';
                }
            }

            function redirectToCliente() {
                // Redirigir a la página JSP/Cliente
                window.location.href = 'JSP/Cliente.jsp';
            }
        </script>
    </head>
    <body onload="updateUsers()"> <!-- Ejecutar updateUsers al cargar la página -->
        <h1>Seguir usuario</h1>

        <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvSeguir" method="POST">
            <button type="button" onclick="redirectToCliente()">Volver</button>
            <br>
            <input type="checkbox" id="tipo" name="tipo" onchange="updateUsers()"
                <%= request.getParameter("tipo") != null ? "checked" : "" %>>
            <label for="tipo">Cambiar tipo de usuario</label>
            <br><br>
            
            <!-- ComboBox para clientes -->
            <label id="label1" for="cliente">Selecciona un cliente:</label>
            <select id="cliente" name="cliente"> <!-- Cambié name="comboCliente" a name="cliente" -->
                <!-- Insertar los clientes dinámicamente desde el servidor -->
                <%
                    List<String> listClientes = (List<String>) request.getAttribute("listClientes");
                    if (listClientes != null && !listClientes.isEmpty()) {
                        for (String cliente : listClientes) {
                        %>
                        <option value="<%= cliente%>"><%= cliente%></option>
                        <%
                        }
                    }
                %>
            </select>

            <!-- ComboBox para artistas, inicialmente oculto -->
            <label id="label2" for="artista" style="display: none;">Selecciona un artista:</label>
            <select id="artista" name="artista" style="display: none;">
                <%
                    List<String> listArtistas = (List<String>) request.getAttribute("listArtistas");
                    if (listArtistas != null && !listArtistas.isEmpty()) {
                        for (String artista : listArtistas) {
                        %>
                        <option value="<%= artista%>"><%= artista%></option>
                        <%
                        }
                    }
                %>
            </select>
            <br><br>
            <button type="submit" name="accion" value="seguir">Seguir</button>
        </form>
    </body>
</html>

*/