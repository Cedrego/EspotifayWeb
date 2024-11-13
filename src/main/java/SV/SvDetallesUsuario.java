/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataAlbum;
import Capa_Presentacion.DataArtistaAlt;
import Capa_Presentacion.DataClienteAlt;
import Capa_Presentacion.DataParticular;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvDetallesUsuario", urlPatterns = {"/SvDetallesUsuario"})
public class SvDetallesUsuario extends HttpServlet {
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
            out.println("<title>Servlet SvDetallesUsuario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvDetallesUsuario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipoUsuario = request.getParameter("tipoUsuario");
        String nickUsuario = request.getParameter("nickUsuario");
        
        System.out.println("==================================================");
        System.out.println("Tipo del usuario = " +tipoUsuario);
        System.out.println("Nick del Usuario = "+nickUsuario);
        System.out.println("==================================================");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        StringBuilder datosUsuario = new StringBuilder();
        if (tipoUsuario.equalsIgnoreCase("cliente")) {
            DataClienteAlt DTA = ctrl.getDataClienteAlt(nickUsuario);
            String pic = DTA.getPicture();
            if (pic == null || pic.isBlank()) {
                pic = "images/profiles/blank.png"; // Imagen predeterminada si no hay imagen
            }

            String imagen = request.getContextPath() + "/" + pic;
            datosUsuario.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");
            
            
            datosUsuario.append("<h3>Información del Cliente</h3>")
                    .append("<p><img src=\"").append(imagen).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>")
                    .append("<p>Nombre: ").append(DTA.getNombre()).append("</p>")
                    .append("<p>Correo: ").append(DTA.getCorreo()).append("</p>")
                    .append("<p>Año de Nacimiento: ").append(DTA.getFecha().getDia()).append("/")
                    .append(DTA.getFecha().getMes()).append("/").append(DTA.getFecha().getAnio()).append("</p>");
            
            int usuariosSeguidos = 0;
            int seguidores = 0;
            for(String artistasSeguidos : DTA.getDataArtSeguido()){
                usuariosSeguidos = usuariosSeguidos +1;
            }
            for(String clientesSeguidos: DTA.getDataCliSeguido()){
                usuariosSeguidos = usuariosSeguidos +1;
            }
            for(String clientesSeguidores : DTA.getDataCliSeguidor()){
                seguidores = seguidores +1;
            }
            datosUsuario.append("<p> Cantidad Seguidos: ").append(usuariosSeguidos).append("   Cantidad Seguidores: ").append(seguidores).append("</p>");
            
            datosUsuario.append("<p> Listas creadas por ").append(DTA.getNickname()).append(":");
            datosUsuario.append("<ul>");

            for (DataParticular part : DTA.getDataPart()) {
                // Agregar un botón que representa el nombre de la lista y llama a la función AJAX al hacer clic
                datosUsuario.append("<li>")
                            .append("<button onclick=\"consultarLista('Cliente', '").append(DTA.getNickname()).append("', '").append(part.getNombre()).append("')\">")
                            .append(part.getNombre())
                            .append("</button>")
                            .append("</li>");
            }

            datosUsuario.append("</ul>");
            datosUsuario.append("</p>");

            // Contenedor para mostrar la respuesta del AJAX
            datosUsuario.append("<div id='responseDiv'></div>");

            response.setContentType("text/html;charset=UTF-8");
            out.write(datosUsuario.toString());
        }
        
        if(tipoUsuario.equalsIgnoreCase("artista")){
            DataArtistaAlt DTA = ctrl.getDataArtistaAlt(nickUsuario);
            
             String pic = DTA.getPicture();
            if (pic == null || pic.isBlank()) {
                pic = "images/profiles/blank.png"; // Imagen predeterminada si no hay imagen
            }

            String imagen = request.getContextPath() + "/" + pic;
            datosUsuario.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");
            
            
            datosUsuario.append("<h3>Información del Cliente</h3>")
                    .append("<p><img src=\"").append(imagen).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>")
                    .append("<p>Nombre: ").append(DTA.getNombre()).append("</p>")
                    .append("<p>Correo: ").append(DTA.getCorreo()).append("</p>")
                    .append("<p>Biografía: ").append(DTA.getBiografia()).append("</p>")
                    .append("<p>Sitio Web: ").append(DTA.getSitioWeb()).append("</p>")
                    .append("<p>Año de Nacimiento: ").append(DTA.getFecha().getDia()).append("/")
                    .append(DTA.getFecha().getMes()).append("/").append(DTA.getFecha().getAnio()).append("</p>");
            
            int seguidores = 0 ;
            for(String seguid : DTA.getDataseguidoPorA()){
                seguidores = seguidores +1;
            }
            datosUsuario.append("<p>Cantidad de Seguidores: ").append(seguidores);
            datosUsuario.append("<p> Albumes de ").append(DTA.getNickname()).append(":");
            datosUsuario.append("<ul>");

            for (DataAlbum alb : DTA.getDataalbumes()) {
                // Agregar un botón que representa el nombre de la lista y llama a la función AJAX al hacer clic
                datosUsuario.append("<li>")
                            .append("<button onclick=\"consultarAlbum('Artista', '").append(DTA.getNickname()).append("', '").append(alb.getNombre()).append("')\">")
                            .append(alb.getNombre())
                            .append("</button>")
                            .append("</li>");
            }

            datosUsuario.append("</ul>");
            datosUsuario.append("</p>");

            // Contenedor para mostrar la respuesta del AJAX
            datosUsuario.append("<div id='responseDiv'></div>");

            response.setContentType("text/html;charset=UTF-8");
            out.write(datosUsuario.toString());
            
            DTA.getPicture();
            DTA.getNombre();
            DTA.getCorreo();
            DTA.getBiografia();
            DTA.getCorreo();
            DTA.getFecha();
            DTA.getDataseguidoPorA();
            DTA.getDataalbumes();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
