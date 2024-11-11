package SV;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@WebServlet(name = "SvGuardarArchivo", urlPatterns = {"/SvGuardarArchivo"})
public class SvGuardarArchivo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sourcePath = request.getParameter("filePath");
        String targetDir = request.getServletContext().getRealPath("/uploads/");

        String fileLocation = guardarArchivo(sourcePath, targetDir);

        response.setContentType("text/plain");
        response.getWriter().write(fileLocation); // devuelve la ruta de archivo guardada
    }

    private String guardarArchivo(String sourcePath, String targetDir) throws IOException {
        File sourceFile = new File(sourcePath);
        if (sourceFile.exists()) {
            Path targetPath = Paths.get(targetDir, sourceFile.getName());
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        }
        return sourcePath; // Devuelve la ruta original si no se encontró el archivo
    }
}
