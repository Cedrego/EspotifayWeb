/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import Logica.AltaAlbum;
import Capa_Presentacion.DataTema;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Franco
 */
@WebServlet("/altaAlbum")
public class SvAltaAlbum extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Factory factory = Factory.getInstance();
        ICtrl ctrl = factory.getICtrl();
        // obtener la lista de géneros con la funcion del .jar
        List<String> generos = new ArrayList();
        for(String gen : ctrl.obtenerNombresDeGeneros()){
            generos.add(gen);
        }
        
        // almacenar la lista en el request
        request.setAttribute("generos", generos);
        
        // redirigir a JSP
        request.getRequestDispatcher("/AltaAlbum.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el artista de la sesión
        HttpSession session = request.getSession();
        String nombreArtista = (String) session.getAttribute("nombreArtista");
        
        // Obtener datos del formulario
        String nombreAlbum = request.getParameter("nombreAlbum");
        String anio = request.getParameter("anio");
        String[] generos = request.getParameterValues("generos");
        String[] nombresTemas = request.getParameterValues("nombresTemas");
        String[] duraciones = request.getParameterValues("duraciones");
        String[] posiciones = request.getParameterValues("posiciones");
        String[] direcciones = request.getParameterValues("direcciones");

        // Crear lista de temas
        List<DataTema> temas = new ArrayList();
        for (int i = 0; i < nombresTemas.length; i++) {
            //DataTema(String nombreTema,String alb,String duracionTema, int ordenAlbumT, String guardadoT,List<String> Generos)
            DataTema tema = new DataTema(nombresTemas[i], nombreAlbum, duraciones[i], Integer.parseInt(posiciones[i]), direcciones[i],List.of(generos));
            temas.add(tema);
        }

        // Crear el álbum
        AltaAlbum altaAlbum = new AltaAlbum(nombreAlbum, nombreArtista, Integer.parseInt(anio), List.of(generos), temas);

        // Redirigir a la página de éxito o error
        response.sendRedirect("resultado.jsp?exito=true");
    
    }
}
