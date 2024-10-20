/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SV;

import Logica.Factory;
import Logica.ICtrl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        if(ctrl.obtenerNombresDeCliente().contains(NOE) || ctrl.obtenerMailDeCliente().contains(NOE) ){//Verifico si ingreso bien el nick(DESPUES VER PARA EMAIL)
            if(ctrl.existePassC(NOE,Contra)){//Verifico si ingreso bien el pass
                response.sendRedirect("Clietne.jsp");
            }
        }
        if(ctrl.existeArtista(NOE)){
            if(ctrl.existePassA(NOE,Contra)){//Verifico si ingreso bien el pass
                response.sendRedirect("Artista.jsp");
            }
        }
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}