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
import java.net.HttpURLConnection;
import java.net.URL;
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
@WebServlet(name = "SvConsultarAlbum", urlPatterns = {"/SvConsultarAlbum"})
public class SvConsultarAlbum extends HttpServlet {
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
            out.println("<title>Servlet SvConsultarAlbum</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvConsultarAlbum at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String filtroBusqueda = request.getParameter("filtroBusqueda");
        String artGen = request.getParameter("ArtGen");
        String nombreAlbum = request.getParameter("NombreAlbum");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(filtroBusqueda.equalsIgnoreCase("Artista")){  
            if(nombreAlbum==null){
                if(artGen==null){
                    for (String art : ctrl.obtenerNombresDeArtistaConAlbum()){
                        out.write("<option value='" + art + "'>" + art + "</option>");
                    }
                }else{
                    if(ctrl.obtenerAlbumesDeArtista(artGen)!=null){
                        for(String alb : ctrl.obtenerAlbumesDeArtista(artGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre del album:"+nombreAlbum);
                DataAlbum da = ctrl.obtenerDataAlbum(nombreAlbum);
                String pic = da.getPic();
                if (pic == null || pic.isBlank()) {
                    pic = "images/noImageSong.png"; // Imagen predeterminada si no hay imagen
                }
                String imagen = request.getContextPath() + "/" +pic;
                // crear una respuesta HTML
                
                StringBuilder albumData = new StringBuilder();
                albumData.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");

                // crear una respuesta HTML
                albumData.append("<h3>Información del Álbum</h3>")
                         .append("<p>Nombre: ").append(da.getNombre()).append("</p>")
                        .append("<p><img src=\"").append(imagen).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>") 
                        .append("<p>Año de creación: ").append(da.getCreacion()).append("</p>")
                         .append("<p>Géneros: ").append(String.join(" || ", da.getGeneros())).append("</p>")
                         .append("<h4>Temas:</h4>");

                for (DataTema tema : da.getDataTemas()) {
                    albumData.append("<p>")
                            .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                            .append("Nombre: ").append(tema.getNombre()).append(" - ")
                            .append("Duración: ").append(tema.getDuracion()).append(" mins")
                            .append(" - <a href='")
                            .append(request.getContextPath()) // Esto obtiene el contexto de la aplicación, e.g., /EspotifyWeb
                            .append("/").append(tema.getDireccion()) // Ruta completa desde el contexto raíz
                            .append("' download onclick=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Descargas')\">")
                            .append("<button>Descargar</button></a>")
                            .append("</p>");

                    String direccion = tema.getDireccion();
                    boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                    String videoId = " ";

                    if (direccion.startsWith("https://youtu.be")) {
                        // Si es un enlace corto de YouTube, extraemos el ID
                        videoId = direccion.split("youtu.be/")[1];
                    } else if (direccion.startsWith("https://www.youtube.com")) {
                        // Si es un enlace largo de YouTube, extraemos el ID
                        videoId = direccion.split("v=")[1].split("&")[0];
                    }

                    // Mostrar el iframe de YouTube con el ID extraído
                    if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {
                        albumData.append("<iframe width=\"220\" height=\"135\" src=\"https://www.youtube.com/embed/")
                                .append(videoId).append("\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen")
                                .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                    } else if (isMp3) {
                        // Reproductor de audio para archivos MP3 con controles completos
                        direccion =  request.getContextPath() + "/" + tema.getDireccion();
                        albumData.append("<audio id=\"audio-").append(tema.getIdTema()).append("\" controls ")
                                .append("onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\">")
                                .append("<source src=\"").append(direccion).append("\" type=\"audio/mpeg\">")
                                .append("Tu navegador no soporta el elemento de audio.")
                                .append("</audio>");
                    } else {
                        // Obtener la URL real redirigida
                        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                            direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                        }
                        String realUrl = null;
                        try {
                            // Crear la conexión HTTP con la URL de Bit.ly
                            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                            connection.connect();

                            // Obtener la cabecera "Location" que es la URL redirigida
                            realUrl = connection.getHeaderField("Location");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                        if (realUrl != null) {
                            try {
                                // Asegúrate de que la URL real tiene la estructura correcta
                                String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                // Reproducir la pista de SoundCloud en un iframe
                                albumData.append("<iframe width='100%' height='166' scrolling='no' frameborder='no'")
                                        .append(" src=\"https://w.soundcloud.com/player/?url=").append(encodedUrl)
                                        .append("&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false\"")
                                        .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Mensaje en caso de que no se pueda obtener la URL
                            albumData.append("<p>No se pudo redirigir el enlace de Bit.ly.</p>");
                        }
                    }
                }


                response.setContentType("text/html;charset=UTF-8");
                out.write(albumData.toString());
            }
        }
        
        if(filtroBusqueda.equalsIgnoreCase("Genero")){
            if(nombreAlbum==null){
                if(artGen==null){
                    for (String gen : ctrl.obtenerNombresDeGenerosConAlbum()){
                        if(gen.equalsIgnoreCase("Genero")){

                            }else{
                                out.write("<option value='" + gen + "'>" + gen + "</option>");
                            }
                    }
                }else{
                    if(ctrl.obtenerAlbumesDeGenero(artGen)!=null){
                        for(String alb : ctrl.obtenerAlbumesDeGenero(artGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                
                System.out.println("Nombre del album:"+nombreAlbum);
                DataAlbum da = ctrl.obtenerDataAlbum(nombreAlbum);
                String pic = da.getPic();
                if (pic == null || pic.isBlank()) {
                    pic = "images/noImageSong.png"; // Imagen predeterminada si no hay imagen
                }
                String imagen = request.getContextPath() + "/" +pic;
                // crear una respuesta HTML
                
                StringBuilder albumData = new StringBuilder();
                albumData.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");
                
                albumData.append("<h3>Información del Álbum</h3>")
                         .append("<p>Nombre: ").append(da.getNombre()).append("</p>")
                         .append("<p><img src=\"").append(imagen).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>")
                         .append("<p>Año de creación: ").append(da.getCreacion()).append("</p>")
                         .append("<p>Géneros: ").append(String.join(" || ", da.getGeneros())).append("</p>")
                         .append("<h4>Temas:</h4>");

                for (DataTema tema : da.getDataTemas()) {
                    albumData.append("<p>")
                            .append("Posición: ").append(tema.getOrdenAlbum()).append(" - ")
                            .append("Nombre: ").append(tema.getNombre()).append(" - ")
                            .append("Duración: ").append(tema.getDuracion()).append(" mins")
                            .append(" - <a href='")
                            .append(request.getContextPath()) // Esto obtiene el contexto de la aplicación, e.g., /EspotifyWeb
                            .append("/").append(tema.getDireccion()) // Ruta completa desde el contexto raíz
                            .append("' download onclick=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Descargas')\">")
                            .append("<button>Descargar</button></a>")
                            .append("</p>");

                    String direccion = tema.getDireccion();
                    boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                    String videoId = " ";

                    if (direccion.startsWith("https://youtu.be")) {
                        // Si es un enlace corto de YouTube, extraemos el ID
                        videoId = direccion.split("youtu.be/")[1];
                    } else if (direccion.startsWith("https://www.youtube.com")) {
                        // Si es un enlace largo de YouTube, extraemos el ID
                        videoId = direccion.split("v=")[1].split("&")[0];
                    }

                    // Mostrar el iframe de YouTube con el ID extraído
                    if (tema.getDireccion().startsWith("https://youtu.be") || tema.getDireccion().startsWith("https://www.youtube.com")) {
                        albumData.append("<iframe width=\"220\" height=\"135\" src=\"https://www.youtube.com/embed/")
                                .append(videoId).append("\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen")
                                .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                    } else if (isMp3) {
                        direccion = request.getContextPath() + "/" + tema.getDireccion();
                        // Reproductor de audio para archivos MP3 con controles completos
                        albumData.append("<audio id=\"audio-").append(tema.getIdTema()).append("\" controls ")
                                .append("onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\">")
                                .append("<source src=\"").append(direccion).append("\" type=\"audio/mpeg\">")
                                .append("Tu navegador no soporta el elemento de audio.")
                                .append("</audio>");
                    } else {
                        // Obtener la URL real redirigida
                        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                            direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                        }
                        String realUrl = null;
                        try {
                            // Crear la conexión HTTP con la URL de Bit.ly
                            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                            connection.connect();

                            // Obtener la cabecera "Location" que es la URL redirigida
                            realUrl = connection.getHeaderField("Location");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                        if (realUrl != null) {
                                // Asegúrate de que la URL real tiene la estructura correcta
                                String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                // Reproducir la pista de SoundCloud en un iframe
                                albumData.append("<iframe width='100%' height='166' scrolling='no' frameborder='no'")
                                        .append(" src=\"https://w.soundcloud.com/player/?url=").append(encodedUrl)
                                        .append("&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false\"")
                                        .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                            
                        } else {
                            // Mensaje en caso de que no se pueda obtener la URL
                            albumData.append("<p>No se pudo redirigir el enlace de Bit.ly.</p>");
                        }
                    }
                }


                response.setContentType("text/html;charset=UTF-8");
                out.write(albumData.toString());

            }
        }
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
        String nombreTema = request.getParameter("nombreTema");
        String nombreAlbum = request.getParameter("nombreAlbum");
        String tipoPuntaje = request.getParameter("tipoPuntaje");
        ctrl.aumentarPuntajeTema(nombreTema, nombreAlbum, tipoPuntaje);
        response.setStatus(HttpServletResponse.SC_OK);
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
