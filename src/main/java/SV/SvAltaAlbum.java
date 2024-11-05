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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;


/**
 *
 * @author Franco
 */
@MultipartConfig
@WebServlet(name = "SvAltaAlbum", urlPatterns = {"/SvAltaAlbum"})
public class SvAltaAlbum extends HttpServlet {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
        String nickArtista = (String) sesion.getAttribute("NickSesion");
        String error;
        String nombreAlbum = request.getParameter("nombreAlbum");
        String anio = request.getParameter("anio");
        String generosSeleccionados = request.getParameter("generosSeleccionados");

        String[] nombresTemas = request.getParameterValues("nombresTemas");
        String[] duraciones = request.getParameterValues("duraciones");
        String[] posiciones = request.getParameterValues("posiciones");
        String[] urls = request.getParameterValues("urls"); // contiene las URLs
        String[] archivos = request.getParameterValues("archivos"); // contiene las rutas de los archivos
        String[] generosArray = generosSeleccionados.split(",");
        
        
        //validacion de nombres
        Set<String> nombresSet = new HashSet<>();
        for (String nombreTema : nombresTemas) {
            if (!nombresSet.add(nombreTema)) {
                error = "El tema '" + nombreTema + "' ya existe en el álbum.";
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response);
                return;
            }
        }

        // ajueste de posiciones
        for (int i = 0; i < posiciones.length; i++) {
            for (int j = i + 1; j < posiciones.length; j++) {
                while (posiciones[j].equals(posiciones[i])) {
                    int nuevaPos = Integer.parseInt(posiciones[j]) + 1;
                    posiciones[j] = String.valueOf(nuevaPos);
                }
            }
        }
        
        //creacion de datatema
        List<DataTema> temas = new ArrayList<>();
        for (int i = 0; i < nombresTemas.length; i++) {
            String direccionFinal;

            // con esto verifico si es una url o un archivo,
            if (urls != null && urls.length > i && urls[i] != null && !urls[i].isEmpty()) {
                direccionFinal = urls[i]; // Asignar URL si existe
                System.out.println("POS: "+i+" URL: "+urls[i]);
            } else if (archivos != null && archivos.length > i && archivos[i] != null && !archivos[i].isEmpty()) {
                // si es un archivo lo mando a un servlet aparte para gestionarlo y conseguir la direccion
                direccionFinal = enviarArchivoAServlet(archivos[i], request); 
                System.out.println("POS: "+i+" ARCHIVO: "+urls[i]);
            } else {
                // error por las dudas
                error = "No se ha proporcionado ni URL ni archivo para el tema '" + nombresTemas[i] + "'.";
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response);
                System.out.println("POS: "+i+" URL: "+urls[i]);
                System.out.println("POS: "+i+" ARCHIVO: "+urls[i]);
                return;
            }

            DataTema tema = new DataTema(nombresTemas[i], nombreAlbum, duraciones[i], Integer.parseInt(posiciones[i]), direccionFinal, List.of(generosArray));
            temas.add(tema);
        }

        ctrl.ordenarTemasPorPosicion(temas);
        //ctrl.CrearAlbum(nombreAlbum, nickArtista, Integer.parseInt(anio), List.of(generosArray), temas);
        error = ("Se creo el album "+nombreAlbum+" con exito");
        sesion.setAttribute("error", error);
        request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response); 
    }

    private String enviarArchivoAServlet(String filePath, HttpServletRequest request) throws IOException {
        String servletURL = request.getContextPath() + "/SvGuardarArchivo";
        HttpURLConnection conn = (HttpURLConnection) new URL(servletURL).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        try (PrintWriter writer = new PrintWriter(conn.getOutputStream())) {
            writer.write("filePath=" + filePath);
            writer.flush();
        }
        
        // retorna la ruta del archivo guardado
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        return reader.readLine(); 
    }
}