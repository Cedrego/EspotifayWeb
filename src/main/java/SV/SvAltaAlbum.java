package SV;

import Logica.Factory;
import Logica.ICtrl;
import Capa_Presentacion.DataTema;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.nio.file.Files;


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
        String error;
        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String nickArtista = (String) misesion.getAttribute("NickSesion");

        String nombreAlbum = request.getParameter("nombreAlbum");
        String anio = request.getParameter("anio");

        String generosSeleccionados = request.getParameter("generosSeleccionados");
        String[] generosArray = generosSeleccionados.split(",");

        String[] nombresTemas = request.getParameterValues("nombresTemas");
        String[] duraciones = request.getParameterValues("duraciones");
        String[] posiciones = request.getParameterValues("posiciones");
        String[] urls = request.getParameterValues("urls");
        
        for(int i=0; i<urls.length;i++){
            if(urls[i]==null){
                System.out.println("ESTA POSICION "+i+" ESTA NULL");
            }else{
                System.out.println("ESTA POSICION "+i+" TIENE ESTA ESTA URL:"+urls[i]);
            }   
        }
        // logica de validación de nombres de temas duplicados
        Set<String> nombresSet = new HashSet<>();
        for (String nombreTema : nombresTemas) {
            if (nombresSet.add(nombreTema)) {

            } else {
                // si no se puede añadir, es un duplicado
                error = ("ERROR: El tema '" + nombreTema + "' ya existe en el album.");
                sesion.setAttribute("error", error);
                request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response);
                return;
            }
        }
        String imagenPortadaUrl = guardarImagen(request);
        
        //logica para ajustar las posiciones
        for (int i = 0; i < posiciones.length; i++) {
            for (int j = i + 1; j < posiciones.length; j++) {
                // si encontramos dos valores iguales, incrementamos el segundo
                while (posiciones[j].equals(posiciones[i])) {
                    int nuevaPos = Integer.parseInt(posiciones[j]) + 1;  // sncrementamos la posición repetida
                    posiciones[j] = String.valueOf(nuevaPos); // actualizamos el valor en el array
                }
                System.out.println("VALOR POS: " + posiciones[j]);
            }
        }
        
        List<String> archivosGuardados = guardarArchivos(request);
        int archivoIndex = 0;  // Índice para rastrear archivos

        for (int i = 0; i < nombresTemas.length; i++) {
            // Solo asigna un archivo si urls[i] está vacío y hay archivos disponibles
            if (urls[i].isEmpty() && archivoIndex < archivosGuardados.size()) {
                urls[i] = archivosGuardados.get(archivoIndex);
                archivoIndex++;  // Incrementa solo si se asignó un archivo
            }
        }
        // si pasa las validaciones anteriores, crea la lista de temas
        List<DataTema> temas = new ArrayList();
        for (int i = 0; i < nombresTemas.length; i++) {
            DataTema tema = new DataTema(nombresTemas[i], nombreAlbum, duraciones[i], Integer.parseInt(posiciones[i]), urls[i], List.of(generosArray));
            temas.add(tema);
        }
        //ordeno las posiciones por las dudas
        ctrl.ordenarTemasPorPosicion(temas);
        // crear el álbum
        ctrl.CrearAlbum(imagenPortadaUrl,nombreAlbum, nickArtista, Integer.parseInt(anio), List.of(generosArray), temas);
        error = ("Se creo el album " + nombreAlbum + " con exito");
        sesion.setAttribute("error", error);
        request.getRequestDispatcher("JSP/AltaAlbum.jsp").forward(request, response);
    }


    private List<String> guardarArchivos(HttpServletRequest request) throws IOException, ServletException {
        List<String> archivosGuardados = new ArrayList<>();
        String projectDir = new File(getServletContext().getRealPath("/")).getParentFile().getParent();
        String uploadPath = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "musica";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio de almacenamiento de archivos.");
        }

        for (Part part : request.getParts()) {
            if (part.getName().equals("archivos") && part.getSize() > 0) {
                String fileName = part.getSubmittedFileName();
                File file = new File(uploadDir, fileName);
                String uniqueFileName = fileName;
                int fileSuffix = 1;

                // Evitar sobreescritura si el archivo ya existe
                while (file.exists()) {
                    uniqueFileName = fileName.replaceFirst("(\\.[^.]*)?$", "_" + fileSuffix + "$1"); // Agrega un sufijo antes de la extensión
                    file = new File(uploadDir, uniqueFileName);
                    fileSuffix++;
                }

                // Guardar el archivo
                try (InputStream inputStream = part.getInputStream()) {
                    Files.copy(inputStream, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                // Agregar la ruta relativa a la lista
                archivosGuardados.add("musica/" + uniqueFileName);
            }
        }

        return archivosGuardados;
    }
    
    private String guardarImagen(HttpServletRequest request) throws IOException, ServletException {
        String imagePath = null;
        String projectDir = new File(getServletContext().getRealPath("/")).getParentFile().getParent();
        String uploadPath = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images"+ File.separator + "portadas";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio de almacenamiento de imágenes.");
        }

        Part part = request.getPart("imagenPortada"); // Obtén el archivo de imagen
        if (part != null && part.getSize() > 0) {
            String fileName = part.getSubmittedFileName();
            File file = new File(uploadDir, fileName);

            try (InputStream inputStream = part.getInputStream()) {
                Files.copy(inputStream, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            // Agrega la ruta relativa de la imagen
            imagePath = "images/portadas/" + fileName;
        }
        return imagePath;
    }


}