<%-- 
    Document   : ConsultaPerfil
    Created on : 21 oct. 2024, 8:19:25 p. m.
    Author     : cedre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Consultar Perfil</h2>
    <form action="${pageContext.request.contextPath}/SvConsultarPerfil" method="GET">
        <p><lable>Nick o Email:</lable><input type="text" name="NOE"</p>
        <button type="submit">Ingresar</button>
    </form>
    </body>
</html>
