/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//image imports
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;


/**
 *
 * @author camin
 */
//@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
@WebServlet("/SvRegistro")
@MultipartConfig // Añade esta línea
public class SvRegistro extends HttpServlet {
    
    private static final String UPLOAD_DIR = "images/profiles";
    Factory fabric = Factory.getInstance();
    ICtrl ctrl = fabric.getICtrl();
    private ServletContext context;
    
    @Override
    public void init() throws ServletException {
        context = getServletContext(); // Inicializar el contexto al iniciar el servlet
    }
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
            out.println("<title>Servlet SvRegistro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvRegistro at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = null; // Inicializa el mensaje de error como null
        String nick = request.getParameter("nick");
        String nom = request.getParameter("nom");
        String ape = request.getParameter("ape");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        String pass2 = request.getParameter("pass2");
        String web = request.getParameter("web");
        String bio = request.getParameter("bio");
        String fecha = request.getParameter("fech");
        String artista = request.getParameter("esArtista");
        
        //imagenes
        String imagen = saveImage(request);
        String url = request.getParameter("urlInput");
        
        System.out.println("=======================================");
        System.out.println("Nickname: " + nick);
        System.out.println("File Part: " + imagen);
        System.out.println("URL: " + url);
        System.out.println("=======================================");
        
        LocalDate date = LocalDate.parse(fecha);
        int anio = date.getYear();
        int dia = date.getDayOfMonth();
        int month = date.getMonthValue();
        String mes = new String();
        HttpSession sesion = request.getSession();
        List<String> nicknames = new ArrayList<>();
        List<String> mails = new ArrayList<>();
        for (String Nickname : ctrl.obtenerNombresDeCliente()) {
            nicknames.add(Nickname);
        }
        for (String Nickname : ctrl.obtenerNombresDeArtista()) {
            nicknames.add(Nickname);
        }
        for (String Correo : ctrl.obtenerNombresDeCliente()) {
            mails.add(Correo);
        }
        for (String Correo : ctrl.obtenerNombresDeArtista()) {
            mails.add(Correo);
        }
        //Obtener mes
        if (month == 1) {
            mes = "Enero";
        } else if (month == 2) {
            mes = "Febrero";
        } else if (month == 3) {
            mes = "Marzo";
        } else if (month == 4) {
            mes = "Abril";
        } else if (month == 5) {
            mes = "Mayo";
        } else if (month == 6) {
            mes = "Junio";
        } else if (month == 7) {
            mes = "Julio";
        } else if (month == 8) {
            mes = "Agosto";
        } else if (month == 9) {
            mes = "Septiembre";
        } else if (month == 10) {
            mes = "Octubre";
        } else if (month == 11) {
            mes = "Noviembre";
        } else if (month == 12) {
            mes = "Diciembre";
        }
        // Verificación de errores
        if (nick == null || nick.isEmpty()) {
            error = "ERROR: campo nickname vacío";
        } else if (nom == null || nom.isEmpty()) {
            error = "ERROR: campo nombre vacío";
        } else if (ape == null || ape.isEmpty()) {
            error = "ERROR: campo apellido vacío";
        } else if (mail == null || mail.isEmpty()) {
            error = "ERROR: campo correo vacío";
        } else if (pass == null || pass.isEmpty()) {
            error = "ERROR: campo contraseña vacío";
        } else if (fecha == null || fecha.isEmpty()) {
            error = "ERROR: campo fecha de nacimiento vacío";
        } else if (anio >= 2024) {
            error = "ERROR: ingrese una fecha valida(31/12/2023 o anterior)";
        } else if (nicknames.contains(nick)) {
            error = "ERROR: ese nickname ya está en uso";
        } else if (mails.contains(mail)) {
            error = "ERROR: ese correo ya está en uso";
        } else if (!pass.equals(pass2)) {
            error = "ERROR: las contraseñas no coinciden";
        }
        // Verificacion de errores de artista
        if (artista != null) {
            if (web == null || web.isEmpty()) {
                error = "ERROR: campo página vacío";
            } else if (bio == null || bio.isEmpty()) {
                error = "ERROR: campo biografía vacío";
            }
        }
        // Control de nickname y mail
        if (nicknames.contains(nick)) {
            error = "ERROR: ese nickname ya esta en uso, elija otro";
        } else if (mails.contains(mail)) {
            error = "ERROR: ese correo ya esta en uso, elija otro";
        }
        
        if (error != null) {
            sesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/Registro.jsp").forward(request, response); // Redirige al JSP
        } else {
            if (artista != null) {
                //crear artista
                if (imagen != null && imagen != "") {
                    ctrl.crearArtista(nick, nom, ape, pass, mail, dia, mes, anio, bio, web, imagen);
                } else if (url != null && url != "") {
                    ctrl.crearArtista(nick, nom, ape, pass, mail, dia, mes, anio, bio, web, url);
                } else {
                    ctrl.crearArtista(nick, nom, ape, pass, mail, dia, mes, anio, bio, web, null);
                }

            } else {
                //crear cliente
                if(imagen != null && imagen != ""){
                    ctrl.crearCliente(nick, nom, ape, mail, pass, dia, mes, anio, imagen);
                }else if(url != null && url != ""){
                    ctrl.crearCliente(nick, nom, ape, mail, pass, dia, mes, anio, url);
                }else{
                    ctrl.crearCliente(nick, nom, ape, mail, pass, dia, mes, anio, null);
                }
            }
            request.getRequestDispatcher("index.jsp").forward(request, response); // Redirige al JSP
        }
    }
    
    private String saveImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("imageFile"); // Este es el nombre del campo del formulario

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();

            // Obtener la ruta del directorio donde se ejecuta el servidor
            String projectDir = new File(getServletContext().getRealPath("/")).getParentFile().getParent();
            System.out.println("DIRECCION: " + projectDir);
            String uploadPath = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images" + File.separator + "profiles";

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
            return "images/profiles/" + fileName; // Ruta relativa
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
