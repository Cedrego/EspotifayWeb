<%-- 
    Document   : Invitado
    Created on : 17 oct. 2024, 2:44:47 p. m.
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invitado</title>
    </head>
        </script>
        <h1>AAAAA   AAAAA AAA AAAAA   AAAAA</h1>
        <p><button onclick="window.location.href = '${pageContext.request.contextPath}/SvConsultarPerfil'">Consultar Perfiles</button></p>
        <%HttpSession misesion = request.getSession();
        misesion.setAttribute("Perfil","algo");%>
        <div id="video-container" style="display: none;">
        <iframe width="560" height="315" src="https://www.youtube.com/embed/Cn8-52oONT0" frameborder="0" allowfullscreen></iframe>
        </div>

        <p><button onclick="showVideo()">[REDACTED]</button></p>

        <script>
            function showVideo() {
                document.getElementById("video-container").style.display = "block";
            }
        </script>
        
    </body>
</html>
