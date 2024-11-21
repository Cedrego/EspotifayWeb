/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataAlbum;
import Capa_Presentacion.DataTema;
import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author tecnologo
 */
@WebServlet(name = "SvBuscador", urlPatterns = {"/SvBuscador"})
public class SvBuscador extends HttpServlet {
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
            out.println("<title>Servlet SvBuscador</title>");
            out.println("</head>");
            out.println("<style>body { color: white; background-color: #000; }</style>");
            out.println("<body>");
            out.println("<h1>Servlet SvBuscador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getParameter("query");
    response.setContentType("text/html;charset=UTF-8");

    if (query != null && !query.trim().isEmpty()) {
        List<String> albums = ctrl.buscadorAlbum(query);
        List<DataTema> temas = ctrl.buscadorTema(query);
        List<String> part = ctrl.buscadorPart(query);
        List<String> pd = ctrl.buscadorPD(query);
        List<String> clientes = ctrl.buscadorCliente(query);
        List<String> artistas = ctrl.buscadorArtista(query);
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Resultados de Búsqueda</title>");
            out.println("<style>body { color: white; background-color: #000; }</style>");
            out.println("<script type='text/javascript'>");

            // Función JavaScript para reproducir un tema
            out.println("function reproducirTema(url, nombre, imagen) {");
            out.println("    document.getElementById('audio-player').src = url;");
            out.println("    document.getElementById('song-name').textContent = nombre;");
            out.println("    document.getElementById('album-image').src = imagen;");
            out.println("    document.getElementById('audio-player').play();");
            out.println("    const playPauseButton = document.getElementById('play-pause');");
            out.println("    playPauseButton.innerHTML = \"&#10074;&#10074;\"; // Cambiar a ícono de pausa");
            out.println("}");


            // Script para mostrar detalles del cliente
            out.println("function mostrarDetallesCliente(nickUsuario) {");
            out.println("   var xhr = new XMLHttpRequest();");
            out.println("   var contextPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));");
            out.println("   xhr.open('GET', contextPath + '/SvDetallesUsuario?tipoUsuario=cliente&nickUsuario=' + nickUsuario + '&dondeViene=buscador&_=' + new Date().getTime(), true);");
            out.println("   xhr.onreadystatechange = function() {");
            out.println("       if (xhr.readyState == 4 && xhr.status == 200) {");
            out.println("           var detailsContainer = document.getElementById('detallesCliente' + nickUsuario);");
            out.println("           detailsContainer.innerHTML = xhr.responseText;");
            out.println("           detailsContainer.style.display = 'block';");
            out.println("       }");
            out.println("   };");
            out.println("   xhr.send();");
            out.println("}");

            // Script para mostrar detalles del artista
            out.println("function mostrarDetallesArtista(nickUsuario) {");
            out.println("   var xhr = new XMLHttpRequest();");
            out.println("   var contextPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));");
            out.println("   xhr.open('GET', contextPath + '/SvDetallesUsuario?tipoUsuario=artista&nickUsuario=' + nickUsuario + '&dondeViene=buscador&_=' + new Date().getTime(), true);");
            out.println("   xhr.onreadystatechange = function() {");
            out.println("       if (xhr.readyState == 4 && xhr.status == 200) {");
            out.println("           var detailsContainer = document.getElementById('detallesArtista' + nickUsuario);");
            out.println("           detailsContainer.innerHTML = xhr.responseText;");
            out.println("           detailsContainer.style.display = 'block';");
            out.println("       }");
            out.println("   };");
            out.println("   xhr.send();");
            out.println("}");
            
            out.println("function consultarLista(filtroBusqueda, CliGen, NombreLista) {");
            out.println("    var xhr = new XMLHttpRequest();");
            out.println("    var contextPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));");
            out.println("    xhr.open('GET', contextPath + '/SvConsultarLista?filtroBusqueda=' + filtroBusqueda + '&CliGen=' + CliGen + '&NombreLista=' + NombreLista + '&_=' + new Date().getTime(), true);");
            out.println("    xhr.onreadystatechange = function () {");
            out.println("        if (xhr.readyState == 4 && xhr.status == 200) {");
            out.println("            var responseDiv = document.getElementById('detallesLista' + NombreLista);");
            out.println("            responseDiv.innerHTML = xhr.responseText;");
            out.println("            responseDiv.style.display = 'block';");
            out.println("        }");
            out.println("    };");
            out.println("    xhr.send();");
            out.println("}");

            out.println("function consultarAlbum(filtroBusqueda, ArtGen, NombreAlbum) {");
            out.println("    var xhr = new XMLHttpRequest();");
            out.println("    var contextPath = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));");
            out.println("    xhr.open('GET', contextPath + '/SvConsultarAlbum?filtroBusqueda=' + filtroBusqueda + '&ArtGen=' + ArtGen + '&NombreAlbum=' + NombreAlbum + '&_=' + new Date().getTime(), true);");
            out.println("    xhr.onreadystatechange = function () {");
            out.println("        if (xhr.readyState == 4 && xhr.status == 200) {");
            out.println("            var responseDiv = document.getElementById('detallesAlbum' + NombreAlbum);");
            out.println("            responseDiv.innerHTML = xhr.responseText;");
            out.println("            responseDiv.style.display = 'block';");
            out.println("        }");
            out.println("    };");
            out.println("    xhr.send();");
            out.println("}");

            
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");

            // Si no se encuentran resultados, mostrar mensaje
            if (artistas.isEmpty() && clientes.isEmpty() && albums.isEmpty() && temas.isEmpty() && part.isEmpty() && pd.isEmpty()) {
                out.println("<h3>No se encontraron resultados para la búsqueda.</h3>");
            } else {
                
                // Mostrar resultados
                if (!artistas.isEmpty()) {
                    out.println("<h3>Artistas</h3>");
                    for (String art : artistas) {
                        out.println("<p id='artista_" + art + "' onclick='mostrarDetallesArtista(\"" + art + "\")' style='cursor: pointer; color: lightblue;'>" + art + "</p>");
                        out.println("<div id='detallesArtista" + art + "' style='display: none; padding-left: 20px;'></div>");
                    }
                }

                if (!clientes.isEmpty()) {
                    out.println("<h3>Clientes</h3>");
                    for (String cli : clientes) {
                        out.println("<p id='cliente_" + cli + "' onclick='mostrarDetallesCliente(\"" + cli + "\")' style='cursor: pointer; color: lightblue;'>" + cli + "</p>");
                        out.println("<div id='detallesCliente" + cli + "' style='display: none; padding-left: 20px;'></div>");
                    }
                }
               
                if (!albums.isEmpty()) {
                    out.println("<h3>Álbumes</h3>");
                    for (String album : albums) {
                        String[] alb = album.split("\\|");
                        out.println("<p id='album_" + alb[0] + "' onclick=\"consultarAlbum('Artista', '" + alb[1] + "', '" + alb[0] + "')\" style='cursor: pointer; color: lightblue;'>" + alb[0] + "</p>");
                        out.println("<div id='detallesAlbum" + alb[0] + "' style='display: none; padding-left: 20px;'></div>");
                    }
                }

                if (!temas.isEmpty()) {
                    out.println("<h3>Temas</h3>");
                    for (DataTema tema : temas) {
                        DataAlbum da = ctrl.obtenerDataAlbum(tema.getAlbum());
                        String pic = da.getPic();
                        if (pic == null || pic.isBlank()) {
                            pic = "images/noImageSong.png"; // Imagen predeterminada si no hay imagen
                        }
                        String imagen = request.getContextPath() + "/" +pic;
                        String direccion =  request.getContextPath() + "/" + tema.getDireccion();
                        out.println("<p onclick=\"reproducirTema('" + direccion + "', '" + tema.getNombre() + "', '" + imagen +"')\" style='cursor: pointer; color: lightblue;'>" + tema.getNombre() + "</p>");
                        
                    }
                }

                if (!part.isEmpty() || !pd.isEmpty()) {
                    out.println("<h3>Listas de Reproducción</h3>");
                    for (String parti : part) {
                        String[] par = parti.split("\\|");
                        out.println("<p id='lista_" + par[0] + "' onclick=\"consultarLista('Cliente', '" + par[1] + "', '" + par[0] + "')\" style='cursor: pointer; color: lightblue;'>" + par[0] + "</p>");
                        out.println("<div id='detallesLista" + par[0] + "' style='display: none; padding-left: 20px;'></div>");
                    }
                    for (String pordef : pd) {
                        String[] prdf = pordef.split("\\|");
                        out.println("<p id='lista_" + prdf[0] + "' onclick=\"consultarLista('Genero', '" + prdf[1] + "', '" + prdf[0] + "')\" style='cursor: pointer; color: lightblue;'>" + prdf[0] + "</p>");
                        out.println("<div id='detallesLista" + prdf[0] + "' style='display: none; padding-left: 20px;'></div>");
                    }
                }
            }

            out.println("</body>");
            out.println("</html>");
        }
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
