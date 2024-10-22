<%-- 
    Document   : Usuario
    Created on : 17 oct. 2024, 2:45:14 p. m.
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesion</title>
    </head>
    <body>
        <h1>Inicie Sesion</h1>
    <form action="${pageContext.request.contextPath}/SvIngreso" method="POST">
        <p><lable>Nick o Email:</lable><input type="text" name="NOE"</p>
        <p><lable>Contraseña:</lable><input type="password" name="pass"</p>
        <button type="submit">Consultar</button>
    </form>
        <button onclick="window.location.href = '<%= request.getContextPath() %>/JSP/Registro.jsp'">Registrar</button>
    </body>
</html>
