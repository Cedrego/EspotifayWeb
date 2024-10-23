/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;


import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 *
 * @author User
 */
@WebServlet("/SvIngreso")
public class SvIngreso extends HttpServlet {
    Factory fabric =Factory.getInstance();
    ICtrl ctrl = fabric.getICtrl();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String NOE = request.getParameter("NOE");
        String Contra = request.getParameter("pass");
        HttpSession misesion = request.getSession();
        if(ctrl.obtenerNombresDeCliente().contains(NOE) || ctrl.obtenerMailDeCliente().contains(NOE) ){//Verifico si ingreso bien el nick(DESPUES VER PARA EMAIL)
            if(ctrl.existePassC(NOE,Contra)){//Verifico si ingreso bien el pass
                List<String>sesion = ctrl.ContraXCliente(NOE,Contra);
                misesion.setAttribute("NickSesion",sesion.getFirst());
                response.sendRedirect("JSP/Cliente.jsp");
            }
        }else if(ctrl.obtenerNombresDeArtista().contains(NOE) || ctrl.obtenerMailDeArtista().contains(NOE)){
            if(ctrl.existePassA(NOE,Contra)){//Verifico si ingreso bien el pass
                List<String>sesion = ctrl.ContraXArtista(NOE,Contra);
                misesion.setAttribute("NickSesion",sesion.getFirst());
                response.sendRedirect("JSP/Artista.jsp");
            }
        }else{
            String error = "ERROR: El nick, email o contraseña no son validos";
            misesion.setAttribute("error", error);
            request.getRequestDispatcher("JSP/Usuario.jsp").forward(request, response); // Redirige al JSP
        }
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
