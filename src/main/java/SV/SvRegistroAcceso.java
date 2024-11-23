package SV;


import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*") // Aplica el filtro a todas las URLs del sitio
public class SvRegistroAcceso implements Filter {
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro (si es necesario)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String path = httpRequest.getRequestURI();
            
            // Excluir carpetas específicas
            if (path.endsWith(".mp3") || path.endsWith(".jpg") || path.endsWith(".png")) {
                chain.doFilter(request, response); // Saltar el filtro
                return;
            }
            
            // Capturar los datos de acceso
            String ip = request.getRemoteAddr();
            String url = httpRequest.getRequestURL().toString();
            String userAgent = httpRequest.getHeader("User-Agent");
            String navegador = obtenerNavegador(userAgent);
            String sistemaOperativo = obtenerSistemaOperativo(userAgent);

            // Opcional: Capturar usuario si está autenticado (puede variar según tu sistema)
            String usuario = (httpRequest.getSession(false) != null)
                    ? (String) httpRequest.getSession().getAttribute("NickSesion")
                    : "Invitado";

            // Guardar los datos en la base de datos
            registrarAcceso(usuario, ip, url, navegador, sistemaOperativo,determinarMetodoAcceso(userAgent), java.time.LocalDateTime.now().toString());
        }

        // Pasar la solicitud al siguiente filtro o servlet
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Liberar recursos si es necesario
    }

    // Método para registrar el acceso en la base de datos
    private void registrarAcceso(String usuario, String ip, String url, String browser, String sistemaOperativo, String metodoAcceso, String fechaHora) {
        //registrarAcceso(String ip, String nickUsuario, String url, String browser, String sistemaOperativo, String metodoAcceso, String fechaHora);
        if(usuario == null){
            usuario = "Invitado";
        }
        ctrl.registrarAcceso(ip, usuario, url, browser, sistemaOperativo, metodoAcceso, fechaHora);
    }

    // Método para extraer el navegador del User-Agent
    private String obtenerNavegador(String userAgent) {
        if (userAgent.contains("Brave")) {
            return "Brave";  // Detecta Brave
        } else if (userAgent.contains("Edg")) {
            return "Microsoft Edge";  // Detecta Edge
        } else if (userAgent.contains("Opera") || userAgent.contains("OPR")) {
            return "Opera";  // Detecta Opera
        } else if (userAgent.contains("Chrome")) {
            return "Google Chrome";  // Si no es ninguno de los anteriores, es Chrome
        } else if (userAgent.contains("Firefox")) {
            return "Mozilla Firefox";  // Detecta Firefox
        } else if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "Internet Explorer";  // Detecta Internet Explorer
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";  // Detecta Safari
        } else {
            return "Desconocido";  // Si no se detecta ningún navegador conocido
        }
    }

    // Método para extraer el sistema operativo del User-Agent
    private String obtenerSistemaOperativo(String userAgent) {
    if (userAgent.contains("Android")) {
        return "Android";
    } else if (userAgent.contains("iPhone") || userAgent.contains("iOS")) {
            return "iOS";
    }else if (userAgent.contains("Windows")) {
        return "Windows";
    } else if (userAgent.contains("Mac")) {
        return "Mac OS";
    } else if (userAgent.contains("X11") || userAgent.contains("Linux")) {
        return "Linux";
    }  else {
        return "Desconocido";
    }
}
    
    // Método para determinar si el acceso es desde móvil o web
    private String determinarMetodoAcceso(String userAgent) {
        if (userAgent == null) {
            return "Desconocido";
        }
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "Mobile";
        } else {
            return "Web";
        }
    }
}
