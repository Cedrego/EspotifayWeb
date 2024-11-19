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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author User
 */
@WebServlet(name = "SvActualizarSelectAddTema", urlPatterns = {"/SvActualizarSelectAddTema"})
public class SvActualizarSelectAddTema extends HttpServlet {
    Factory fabric =Factory.getInstance();
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvActualizarSelectAddTema</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvActualizarSelectAddTema at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String  nickCliente = (String) misesion.getAttribute("NickSesion");
        String listaAgregar = request.getParameter("listaAgregar");
        String tipoSeleccion = request.getParameter("tipoDelObjeto");//Album, lista por defecto o pblicas
        System.out.println("Tipo de objeto recibido1: " + tipoSeleccion);
        PrintWriter out = response.getWriter();
        String filtroSecundarioSeleccionado = request.getParameter("filtroPrincipal");//Filtro 2
        System.out.println("Tipo de objeto recibido2: " + filtroSecundarioSeleccionado);//Si es TipoSeleccion es Lista particualr mostraremos los cliente con lista particualres publicas
        String listaCliente = request.getParameter("listaCliente");
        List<String> TemasDis = new ArrayList();
        if ("Album".equals(tipoSeleccion)) {
            if(filtroSecundarioSeleccionado == null){               
                 out.write("<option value=''>Seleccionar Album</option>");
                for (String album : ctrl.obtenerNombresDeAlbumes()) {
                    out.write("<option value='" + album + "'>" + album + "</option>");
                    System.out.println("Album "+album);
                }  
            } else{
                out.write("<option value=''>Seleccionar Tema</option>");
                for (String tema : ctrl.obtenerTemasDeAlbum(filtroSecundarioSeleccionado)) {
                    if(ctrl.obtenerTemasDeParticular(listaAgregar).contains(tema)){
                        //El tema ya esta agregado a la lista
                    }else{
                        TemasDis.add(tema);
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                        System.out.println("Tema "+tema);
                
                    }
                }
                if(TemasDis.isEmpty()){
                    out.write("<option value=''>No hay temas disponibles</option>");
                }
            }                         
        } else if ("ListaPorDef".equals(tipoSeleccion)) {
            if(filtroSecundarioSeleccionado == null){
                out.write("<option value=''>Seleccionar Lista</option>");
                for(String pd : ctrl.obtenerNombresListasPDTODO()){
                    out.write("<option value='" + pd + "'>" + pd + "</option>");
                }
            }else{
                out.write("<option value=''>Seleccionar Tema</option>");
                 for (String tema : ctrl.obtenerTemasDePD(filtroSecundarioSeleccionado)) {
                    if(ctrl.obtenerTemasDeParticular(listaAgregar).contains(tema)){
                        //El tema ya esta agregado a la lista
                    }else{
                        TemasDis.add(tema);
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                        System.out.println("Tema "+tema);
                
                    }
                }
                if(TemasDis.isEmpty()){
                    out.write("<option value=''>No hay temas disponibles</option>");
                }                  
            }
        } else if ("ListaPart".equals(tipoSeleccion)) {
            if(filtroSecundarioSeleccionado == null){
                out.write("<option value=''>Seleccionar Cliente</option>");
                for(String p :  ctrl.obtenerNombresClienteConParticular()){//Cambiar a funcion que te de clientes con playlist part publicas
                    if(p.equalsIgnoreCase(nickCliente)){
                        
                    }else{
                        out.write("<option value='" + p + "'>" + p + "</option>");
                    }
                }
            }else{
                if(listaCliente==null){
                    out.write("<option value=''>Seleccionar Lista</option>");
                    for (String lis : ctrl.obtenerPartPublicaDeDuenio(filtroSecundarioSeleccionado)) {
                        out.write("<option value='" + lis + "'>" + lis + "</option>");
                        System.out.println("Lista "+lis);
                    } 
                }else{
                    out.write("<option value=''>Seleccionar tema</option>");
                    for (String tema : ctrl.obtenerTemasDeParticular(listaCliente)){
                        if(ctrl.obtenerTemasDeParticular(listaAgregar).contains(tema)){
                        //El tema ya esta agregado a la lista
                    }else{
                        TemasDis.add(tema);
                        out.write("<option value='" + tema + "'>" + tema + "</option>");
                        System.out.println("Tema "+tema);
                
                    }
                }
                if(TemasDis.isEmpty()){
                    out.write("<option value=''>No hay temas disponibles</option>");
                }   
                }   
            }
            
        }
        out.flush(); // Asegúrate de que los datos se envíen
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
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String  nickCliente = (String) misesion.getAttribute("NickSesion");
        
        String lista = request.getParameter("listaAgregar");//Lista del cliente que inicio sesion
        String tipoDelObjeto = request.getParameter("tipoDelObjeto");//Filtro 1
        String filtro2 = request.getParameter("filtroPrincipal");//filtro 2
        String temas = request.getParameter("Temas");//si filtro1 es distino de ListaPart sera tema sino sera la la lista del cliente selecionada en el filtro 2
        String opcionesListaPart = request.getParameter("opcionesListaPart");//Si filtro1 es ListPart sera tema sino no existira
        //Controles
        boolean isSuS = false;
        for (DataSuscripcion sus : ctrl.getDataClienteAlt(nickCliente).getDataSuscripcion()) {
            if (sus.getEstado().name().equals("Vigente")) {
                isSuS = true;
            }
        }
        if(isSuS){
            if (lista == null || lista.isEmpty()) {//lista del sesion
                String error = "No se ha seleccionado ninguna lista.";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
            }else if(tipoDelObjeto == null || tipoDelObjeto.isEmpty()) {//album etc
                String error = "No se ha seleccionado un Filtro primario.";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
            }else if("ListaPart".equals(tipoDelObjeto)){
                if(filtro2 == null || filtro2.isEmpty()) {//Clientes con listas publicas
                    String error = "No se ha seleccionado un filtro secundario.";
                    misesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
                }
                if (temas == null || temas.isEmpty()) {//Tendra lista de clientes publica
                    String error = "No se ha seleccionado un Lista Publcia.";
                    misesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
                }else if (opcionesListaPart == null || opcionesListaPart.isEmpty()) {//Tema de esa lista publica
                    String error = "No se ha seleccionado un Tema.";
                    misesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP

                }else{
                    ctrl.AddTemaList("Particular", lista, opcionesListaPart, nickCliente);
                     // Redirigir al JSP con los datos
                    //request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
                }
            }else if(filtro2 == null || filtro2.isEmpty()) {//album o lista por defecto con temas
                String error = "No se ha seleccionado un filtro secundario.";
                misesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP

            }else if (temas == null || temas.isEmpty()) {//Tema
                    String error = "No se ha seleccionado un Tema.";
                    misesion.setAttribute("error", error);
                    request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
            }else{
                ctrl.AddTemaList("Particular", lista, temas, nickCliente);
                // Redirigir al JSP con los datos
               // request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP
            }
        }else{
            String error = "Necesitas una suscripcion para poder hacer eso.";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response); // Redirige al JSP             
        }
        request.setAttribute("lista", lista);
        request.setAttribute("tipoDelObjeto", tipoDelObjeto);
        request.setAttribute("filtro2", filtro2);
        request.setAttribute("temas", temas);
        request.setAttribute("opcionesListaPart", opcionesListaPart);

        // Redirigir al JSP con los datos
        request.getRequestDispatcher("JSP/AddTemaLista.jsp").forward(request, response);

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
