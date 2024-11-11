/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Capa_Presentacion.DataSuscripcion;
import Logica.Factory;
import Logica.ICtrl;
import Logica.Suscripcion;
import antlr.collections.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;

/**
 *
 * @author User
 */
@WebServlet(name = "SvCrearLista", urlPatterns = {"/SvCrearLista"})
@MultipartConfig // Añade esta línea
public class SvCrearLista extends HttpServlet {
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
            out.println("<title>Servlet SvCrearLista</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvCrearLista at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String NombreLista = request.getParameter("NomLista");
        String url = request.getParameter("url");

        // Procesar la imagen (archivo) si fue enviado
        String imagen = saveImage(request); // Este método maneja la carga de la imagen

        HttpSession misesion = request.getSession(false); // No crear una nueva si no existe
        String nickSesion = (String) misesion.getAttribute("NickSesion");
        boolean isSuS = false;
        for (DataSuscripcion sus : ctrl.getDataClienteAlt(nickSesion).getDataSuscripcion()) {
            if (sus.getEstado().name().equals("Vigente")) {
                isSuS = true;
            }
        }

        if (NombreLista.isEmpty()) {
            String error = "ERROR: Escriba un nombre para la lista";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        }

        if (ctrl.ExisListPartEnCliente(NombreLista, nickSesion)) { // Verifica si la lista ya existe
            String error = "ERROR: Ya existe una lista con ese nombre";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        }

        boolean Exist = false;
        for (DataSuscripcion sus : ctrl.ObtenerSubscClietne(nickSesion)) {
            if (sus.getEstado().toString().equals("Vigente")) {
                Exist = true;
                break;
            }
        }

        if (Exist == false) {
            String error = "ERROR: Suscripcion no vigente";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
        } else {
            // Si se subió una imagen (archivo)
            if (imagen != null) {
                System.out.println("ARCHIVO: " + imagen);

                // Obtener solo la fecha actual sin la hora
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada = fechaActual.format(formato);

                ctrl.CreateLista(NombreLista, "Particular", nickSesion, fechaFormateada, imagen);
                request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
            } else if (url != null && !url.isEmpty()) { // Si se ha enviado una URL
                System.out.println("URL: " + url);

                // Obtener solo la fecha actual sin la hora
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada = fechaActual.format(formato);
                String exito = "Lista Creada con Exito";
                misesion.setAttribute("exito", exito);
                ctrl.CreateLista(NombreLista, "Particular", nickSesion, fechaFormateada, url);
                request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
            } else {
                System.out.println("Sin archivo ni URL");

                // Obtener solo la fecha actual sin la hora
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada = fechaActual.format(formato);
                String exito = "Lista Creada con Exito";
                misesion.setAttribute("exito", exito);
                ctrl.CreateLista(NombreLista, "Particular", nickSesion, fechaFormateada, null);
                request.getRequestDispatcher("JSP/CrearLista.jsp").forward(request, response); // Redirige al JSP
            }
        }
    }
    
    private String saveImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file"); // Este es el nombre del campo del formulario

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();

            // Obtener la ruta del directorio donde se ejecuta el servidor
            String projectDir = new File(getServletContext().getRealPath("/")).getParentFile().getParent();
            System.out.println("DIRECCION: " + projectDir);
            String uploadPath = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images" + File.separator + "playlist";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean dirCreated = uploadDir.mkdirs(); // Crea la carpeta si no existe
                if (dirCreated) {
                    System.out.println("Directorio creado: " + uploadDir.getAbsolutePath());
                } else {
                    System.out.println("No se pudo crear el directorio: " + uploadDir.getAbsolutePath());
                }
            }

            // Guardar el archivo en el servidor
            File file = new File(uploadDir, fileName);
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING); // Copia el archivo
                System.out.println("Archivo guardado en: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Error al guardar el archivo: " + e.getMessage());
            }

            // Devuelve la ruta relativa que se usará para acceder a la imagen en la aplicación
            return "images/playlist/" + fileName; // Ruta relativa
        }
        return null; // Retorna null si no se sube ninguna imagen
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
