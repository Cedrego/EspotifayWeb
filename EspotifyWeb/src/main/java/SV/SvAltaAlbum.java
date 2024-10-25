/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import Capa_Presentacion.DataTema;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvAltaAlbum", urlPatterns = {"/SvAltaAlbum"})
public class SvAltaAlbum extends HttpServlet {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> listaGeneros = new ArrayList<>();
        System.out.println("Cargando generos");
        
        for(String gen : ctrl.obtenerNombresDeGeneros()){
            listaGeneros.add(gen);
        }
        
        request.setAttribute("generos", listaGeneros);
        request.getRequestDispatcher("/JSP/AltaAlbum.jsp").forward(request, response);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);  
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String nickArtista = (String) request.getAttribute("NickSesion");
                    
        String nombreAlbum = request.getParameter("nombreAlbum");
        String anio = request.getParameter("anio");
        
        String generosSeleccionados = request.getParameter("generosSeleccionados");
        String[] generosArray = generosSeleccionados.split(",");

        String[] nombresTemas = request.getParameterValues("nombresTemas[]");
        String[] duraciones = request.getParameterValues("duraciones[]");
        String[] posiciones = request.getParameterValues("posiciones[]");
        String[] direcciones = request.getParameterValues("direcciones[]");

        // Crear lista de temas
        List<DataTema> temas = new ArrayList<>();
        for (int i = -1; i < nombresTemas.length; i++) {
            if(i==-1){
                
            }else{
                DataTema tema = new DataTema(nombresTemas[i], nombreAlbum, duraciones[i], Integer.parseInt(posiciones[i]), direcciones[i], List.of(generosArray));
                temas.add(tema);
            }
        }

        // Crear el álbum
        ctrl.CrearAlbum(nombreAlbum, nickArtista, Integer.parseInt(anio), List.of(generosArray), temas);
        request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
    }
}