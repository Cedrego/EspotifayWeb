/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;


import Capa_Presentacion.DataParticular;
import Capa_Presentacion.DataPorDefecto;
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

/**
 *
 * @author Franco
 */
@WebServlet(name = "SvConsultarLista", urlPatterns = {"/SvConsultarLista"})
public class SvConsultarLista extends HttpServlet {
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
            out.println("<title>Servlet SvConsultarLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvConsultarLista at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtroBusqueda = request.getParameter("filtroBusqueda");
        String cliGen = request.getParameter("CliGen");
        String nombreLista = request.getParameter("NombreLista");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(filtroBusqueda.equalsIgnoreCase("Cliente")){  
            if(nombreLista==null){
                if(cliGen==null){
                    for (String cli : ctrl.obtenerNombresClienteConParticular()){
                        out.write("<option value='" + cli + "'>" + cli + "</option>");
                    }
                }else{
                    if(ctrl.obtenerPartPublicaDeDuenio(cliGen)!=null){
                        for(String lis : ctrl.obtenerPartPublicaDeDuenio(cliGen)){
                            out.write("<option value='" + lis + "'>" + lis + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre de la lista:"+nombreLista);
                System.out.println("Cliente duenio:"+cliGen);
                DataParticular dp = ctrl.obtenerDataParticular(nombreLista,cliGen);
                if (dp == null) {
                    System.out.println("Error: dp es null.");
                } else if (dp.getDataTemas() == null) {
                    System.out.println("Error: dp.getDataTemas() es null.");
                } else {
                    System.out.println("DataParticular obtenida correctamente.");
                }
                // crear una respuesta HTML
                StringBuilder listaData = new StringBuilder();
                listaData.append("<style>")
                    .append(".imagen-cliente {")
                    .append("    max-width: 100px;") // Tamaño máximo de ancho
                    .append("    max-height: 100px;") // Tamaño máximo de alto
                    .append("    width: auto;") // Mantiene la proporción
                    .append("    height: auto;") // Mantiene la proporción
                    .append("    object-fit: cover;") // Asegura que la imagen cubra el área sin distorsionarse
                    .append("}")
                    .append("</style>");
                
                listaData.append("<h3>Información de la Lista</h3>");
                        String imagen = dp.getImagen();
                        if (imagen == null || imagen.isBlank()) {
                            imagen = "images/noImageSong.png"; // Imagen predeterminada si no hay imagen
                        }
                        String imagenURL = request.getContextPath() + "/" + imagen;

                        listaData.append("")
                        .append("<p><img src=\"").append(imagenURL).append("\" class=\"imagen-cliente\" style=\"margin-right: 12px;\"></p>")
                        .append("<p>Nombre: ").append(dp.getNombre()).append("</p>")
                        .append("<p>Cliente Dueño: ").append(dp.getCliente()).append("</p>")
                        .append("<h4>Temas:</h4>");

                for (DataTema tema : dp.getDataTemas()) {
                    listaData.append("<p>")
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
                        listaData.append("<iframe width=\"220\" height=\"135\" src=\"https://www.youtube.com/embed/")
                                .append(videoId).append("\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen")
                                .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                    } else if (isMp3) {
                        // Reproductor de audio para archivos MP3 con controles completos
                        direccion =  request.getContextPath() + "/" + tema.getDireccion();
                        listaData.append("<audio id=\"audio-").append(tema.getIdTema()).append("\" controls ")
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
                                listaData.append("<iframe width='100%' height='166' scrolling='no' frameborder='no'")
                                        .append(" src=\"https://w.soundcloud.com/player/?url=").append(encodedUrl)
                                        .append("&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false\"")
                                        .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Mensaje en caso de que no se pueda obtener la URL
                            listaData.append("<p>No se pudo redirigir el enlace de Bit.ly.</p>");
                        }
                    }
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(listaData.toString());
                }
        }
            
        
        
        if(filtroBusqueda.equalsIgnoreCase("Genero")){
            if(nombreLista==null){
                if(cliGen==null){
                    for (String gen : ctrl.obtenerNombresDeGenerosConPorDefecto()){
                        if(gen.equalsIgnoreCase("Genero")){

                            }else{
                                out.write("<option value='" + gen + "'>" + gen + "</option>");
                            }
                    }
                }else{
                    if(ctrl.obtenerListasDeGenero(cliGen)!=null){
                        for(String alb : ctrl.obtenerListasDeGenero(cliGen)){
                            out.write("<option value='" + alb + "'>" + alb + "</option>");
                        }
                    }
                }
            }else{
                System.out.println("Nombre de la lista:"+nombreLista);
                DataPorDefecto dpd = ctrl.obtenerDataPorDefecto(nombreLista);

                // crear una respuesta HTML
                StringBuilder listaData = new StringBuilder();
                listaData.append("<h3>Información de la Lista</h3>")
                        .append("<p>Nombre: ").append(dpd.getNombre()).append("</p>")
                        .append("<p>Genero: ").append(dpd.getGenero()).append("</p>")
                        .append("<h4>Temas:</h4>");

                for (DataTema tema : dpd.getDataTemas()) {
                    listaData.append("<p>")
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
                        listaData.append("<iframe width=\"220\" height=\"135\" src=\"https://www.youtube.com/embed/")
                                .append(videoId).append("\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen")
                                .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                    } else if (isMp3) {
                        // Reproductor de audio para archivos MP3 con controles completos
                        direccion =  request.getContextPath() + "/" + tema.getDireccion();
                        listaData.append("<audio id=\"audio-").append(tema.getIdTema()).append("\" controls ")
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
                                listaData.append("<iframe width='100%' height='166' scrolling='no' frameborder='no'")
                                        .append(" src=\"https://w.soundcloud.com/player/?url=").append(encodedUrl)
                                        .append("&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false\"")
                                        .append(" onplay=\"incrementarPuntajeTema('").append(tema.getNombre()).append("','").append(tema.getAlbum()).append("','Reproducciones')\"></iframe>");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Mensaje en caso de que no se pueda obtener la URL
                            listaData.append("<p>No se pudo redirigir el enlace de Bit.ly.</p>");
                        }
                    }
                }

                response.setContentType("text/html;charset=UTF-8");
                out.write(listaData.toString());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreTema = request.getParameter("nombreTema");
        String nombreAlbum = request.getParameter("nombreAlbum");
        String tipoPuntaje = request.getParameter("tipoPuntaje");
        ctrl.aumentarPuntajeTema(nombreTema, nombreAlbum, tipoPuntaje);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
