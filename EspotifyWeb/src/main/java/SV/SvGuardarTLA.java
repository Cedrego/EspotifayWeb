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
 * @author Franco
 */
@WebServlet(name = "SvGuardarTLA", urlPatterns = {"/SvGuardarTLA"})
public class SvGuardarTLA extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipoDelObjeto = request.getParameter("tipoDelObjeto");
        String filtroPrincipal = request.getParameter("filtroPrincipal");
        String filtroSecundario = request.getParameter("filtroSecundario");
        
        System.out.println("Tipo: "+tipoDelObjeto);
        System.out.println("Filtro: "+filtroPrincipal);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //cargar datos para tema
        if (tipoDelObjeto.equalsIgnoreCase("Tema")){
            if(filtroPrincipal.equalsIgnoreCase("Album")) {
                //tercera combobox
                if(filtroSecundario == null){
                    for (String album : ctrl.obtenerNombresDeAlbumes()) {
                        out.write("<option value='" + album + "'>" + album + "</option>");
                        System.out.println("Album "+album);
                    }
                    //cuarta comobox
                } else{
                    for(String tema : ctrl.obtenerTemasDeAlbum(filtroSecundario)){
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                    }
                }
            }
            
            if(filtroPrincipal.equalsIgnoreCase("Genero")) {
                //tercera combobox
                if(filtroSecundario == null){
                    for (String gen : ctrl.obtenerNombresDeGeneros()) {
                        if(gen.equalsIgnoreCase("Genero")){

                        }else{
                        out.write("<option value='" + gen + "'>" + gen + "</option>");
                        }
                    }
                    //cuarta combobox
                }else{
                    for(String tema : ctrl.obtenerTemasDeGenero(filtroSecundario)){
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                    }
                }
            } 
            
            if(filtroPrincipal.equalsIgnoreCase("ListaParticular")){
                if(filtroSecundario == null){
                    for(String part : ctrl.obtenerNombresListasPartTODO()){
                        if(!(ctrl.esPrivado(part, ctrl.obtenerDuenioPart(part)))){
                            out.write("<option value='" + part + "'>" + part + "</option>");
                        }
                    }
                }else{
                    for(String tema : ctrl.obtenerTemasDeParticular(filtroSecundario)){
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                    }
                }
            }    
            
            if(filtroPrincipal.equalsIgnoreCase("ListaPorDefecto")){
                if(filtroSecundario == null){
                    for(String pd : ctrl.obtenerNombresListasPDTODO()){
                        out.write("<option value='" + pd + "'>" + pd + "</option>");
                    }
                }else{
                    for(String tema : ctrl.obtenerTemasDePD(filtroSecundario)){
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                    }
                }
            }       
        }
        
        //cargar segunda combobox para Album
        if (tipoDelObjeto.equalsIgnoreCase("Album")){
            if(filtroPrincipal.equalsIgnoreCase("Artista")){
                if(filtroSecundario == null){
                    for (String art : ctrl.obtenerNombresDeArtista()) {
                        out.write("<option value='" + art + "'>" + art + "</option>");
                    }
                }else{
                    for (String alb : ctrl.obtenerAlbumesDeArtista(filtroSecundario)) {
                        out.write("<option value='" + alb + "'>" + alb + "</option>");
                    }
                }
            }
            
            if(filtroPrincipal.equalsIgnoreCase("Genero")){
                if(filtroSecundario==null){
                    for(String gen : ctrl.obtenerNombresDeGeneros()){
                        if (gen.equalsIgnoreCase("Genero")){

                        }else{
                            out.write("<option value='" + gen + "'>" + gen + "</option>");
                        }
                    }
                }else{
                    for(String alb : ctrl.obtenerAlbumesDeGenero(filtroSecundario)){
                        out.write("<option value='" + alb + "'>" + alb + "</option>");
                    }
                }
            } 
        }
        
        //cargar segunda combobox para Lista
        if(tipoDelObjeto.equalsIgnoreCase("Lista")){
            if(filtroPrincipal.equalsIgnoreCase("Particular")){
                if(filtroSecundario == null){
                    for (String cli : ctrl.obtenerNombresClienteConParticular()) {
                        out.write("<option value='" + cli + "'>" + cli + "</option>");
                    }
                }else{
                    if(ctrl.obtenerPartPublicaDeDuenio(filtroSecundario)!=null){
                        for (String part : ctrl.obtenerPartPublicaDeDuenio(filtroSecundario)){
                            out.write("<option value='" + part + "'>" + part + "</option>");
                        }
                    }
                }
            }
            
            if(filtroPrincipal.equalsIgnoreCase("PorDefecto")){
                if(filtroSecundario == null){
                    for(String gen : ctrl.obtenerNombresDeGenerosConPorDefecto()){
                        if(gen.equalsIgnoreCase("Genero")){

                        }else{
                            out.write("<option value='" + gen + "'>" + gen + "</option>");
                        }
                    }
                }else{
                    if(ctrl.obtenerListasDeGenero(filtroSecundario)!=null){
                        for (String pd : ctrl.obtenerListasDeGenero(filtroSecundario)){
                            out.write("<option value='" + pd + "'>" + pd + "</option>");
                        }
                    }
                }
            }  
        }     
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String error;
        
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String nickCliente = (String) misesion.getAttribute("NickSesion");
        
        String tipoDelObjeto = request.getParameter("tipoDelObjeto");
        String nombreDelObjeto = request.getParameter("NombreDelObjeto");
        String filtroPrincipal = request.getParameter("filtroPrincipal");
        String filtroSecundario = request.getParameter("filtroSecundario");
        
        
        
        
        if(tipoDelObjeto.equalsIgnoreCase("Tema")){
            if(ctrl.chequearFavorito(tipoDelObjeto, nombreDelObjeto, nickCliente)){
                error = (nombreDelObjeto+" ya esta en favoritos del cliente");
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/GuardarTLA.jsp").forward(request, response); 
            }else{
                ctrl.guardarTLA(tipoDelObjeto, nombreDelObjeto, nickCliente,filtroSecundario);
            }
        }
        
        if(tipoDelObjeto.equalsIgnoreCase("Album")){
            if(ctrl.chequearFavorito(tipoDelObjeto, nombreDelObjeto, nickCliente)){
                error =(nombreDelObjeto+" ya esta en favoritos del cliente");
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/GuardarTLA.jsp").forward(request, response); 
            }else{
                ctrl.guardarTLA(tipoDelObjeto, nombreDelObjeto, nickCliente,"Potato");
            }
        }
        
        if(tipoDelObjeto.equalsIgnoreCase("Lista")){
            if(filtroPrincipal.equalsIgnoreCase("Particular")){
                if(ctrl.chequearFavorito(filtroPrincipal, nombreDelObjeto, nickCliente)){
                    error =(nombreDelObjeto+" ya esta en favoritos del cliente");
                    sesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/GuardarTLA.jsp").forward(request, response); 
                }else{
                    ctrl.guardarTLA("Particular", nombreDelObjeto, nickCliente,filtroSecundario);
                }
            }
            
            if(filtroPrincipal.equalsIgnoreCase("PorDefecto")){
                if(ctrl.chequearFavorito("Por Defecto", nombreDelObjeto, nickCliente)){
                    error = (nombreDelObjeto+" ya esta en favoritos del cliente");
                    sesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/GuardarTLA.jsp").forward(request, response); 
                }else{
                    ctrl.guardarTLA("Por Defecto", nombreDelObjeto, nickCliente,"Potato");
                }
            }
        }
        
        request.getRequestDispatcher("JSP/GuardarTLA.jsp").forward(request, response); // Redirige al JSP
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
