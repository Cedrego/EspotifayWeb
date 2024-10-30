/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import Capa_Presentacion.DataTema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvGuardarTLA</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvGuardarTLA at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<option value=\"\">Seleccione un género</option>");
        for(String nomGen : ctrl.obtenerNombresDeGeneros()){
            out.write("<option value='" + nomGen + "'>" + nomGen + "</option>");
        }  
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String error;
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String  nickArtista = (String) misesion.getAttribute("NickSesion");
                    
        String nombreAlbum = request.getParameter("nombreAlbum");
        String anio = request.getParameter("anio");
        
        String generosSeleccionados = request.getParameter("generosSeleccionados");
        String[] generosArray = generosSeleccionados.split(",");

        String[] nombresTemas = request.getParameterValues("nombresTemas[]");
        String[] duraciones = request.getParameterValues("duraciones[]");
        String[] posiciones = request.getParameterValues("posiciones[]");
        String[] direcciones = request.getParameterValues("direcciones[]");
        
        // logica de validación de nombres de temas duplicados
        Set<String> nombresSet = new HashSet<>();
        for (String nombreTema : nombresTemas) {
            if (nombresSet.add(nombreTema)) {       
                
            } else {
                // si no se puede añadir, es un duplicado
                error = ("El tema '" + nombreTema + "' ya existe en el album.");
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response); 
                return;
            }
        }
        //logica para ajustar las posiciones
        for (int i = 0; i < posiciones.length; i++) {
            for (int j = i + 1; j < posiciones.length; j++) {
                // si encontramos dos valores iguales, incrementamos el segundo
                while (posiciones[j].equals(posiciones[i])) {
                    int nuevaPos = Integer.parseInt(posiciones[j]) + 1;  // sncrementamos la posición repetida
                    posiciones[j] = String.valueOf(nuevaPos); // actualizamos el valor en el array
                }
                System.out.println("VALOR POS: "+posiciones[j]);
            }
        }
        // si pasa las validaciones anteriores, crea la lista de temas
        List<DataTema> temas = new ArrayList<>();
        for (int i = 0; i < nombresTemas.length; i++) {
            DataTema tema =  new DataTema(nombresTemas[i], nombreAlbum, duraciones[i], Integer.parseInt(posiciones[i]), direcciones[i], List.of(generosArray));
            temas.add(tema);
        }
        //ordeno las posiciones por las dudas
        ctrl.ordenarTemasPorPosicion(temas);
        // crear el álbum
        ctrl.CrearAlbum(nombreAlbum, nickArtista, Integer.parseInt(anio), List.of(generosArray), temas);
        request.getRequestDispatcher("JSP/Artista.jsp").forward(request, response); // Redirige al JSP
    }
}
