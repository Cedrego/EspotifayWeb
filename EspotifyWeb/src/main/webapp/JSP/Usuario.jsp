<%-- 
    Document   : Usuario
    Created on : 17 oct. 2024, 2:45:14 p. m.
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espotify - Iniciar Sesion</title>
         <style>
            /* Reset and basic styling */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                background-color: #000;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                font-family: Arial, sans-serif;
            }

            /* Main container */
            .login-container {
                width: 300px;
                padding: 40px 20px;
                background-color: #333;
                border-radius: 10px;
                text-align: center;
                color: white;
            }

            /* Logo placeholder */
            .logo {
                width: 50px;
                height: 50px;
                margin: 0 auto 20px;
                background-image: url('images/cat.gif');
                background-size: cover;
                background-position: center;
                border-radius: 5px;
            }

            /* Title */
            .login-container h2 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            /* Input fields */
            .login-container input[type="text"],
            .login-container input[type="password"] {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 2px solid #666;
                border-radius: 20px;
                background-color: #444;
                color: white;
                font-size: 16px;
                text-align: center;
            }

            /* Buttons */
            .login-container button {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: none;
                border-radius: 20px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
            }

            /* Iniciar Sesión button */
            .login-container .login-button {
                background-color: #1db954;
                color: #FFF;
            }

            /* Crear Cuenta button */
            .login-container .register-button {
                background-color: #1db954;
                color: #FFF;
            }

            /* Error message */
            .error-message {
                color: red;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <!-- Logo Placeholder -->
            <div class="logo"></div>

            <!-- Title -->
            <h2>Espotify</h2>

            <!-- Login Form -->
            <form action="${pageContext.request.contextPath}/SvIngreso" method="POST">
                <input type="text" name="NOE" placeholder="Nombre de usuario o correo" required>
                <input type="password" name="pass" placeholder="Contraseña" required>
                <button type="submit" class="login-button">Iniciar Sesión</button>
            </form>

            <!-- Register Button -->
            <button onclick="window.location.href = '<%= request.getContextPath() %>/JSP/Registro.jsp'" class="register-button">Crear Cuenta</button>

            <!-- Error Message -->
            <%-- Mostrar mensaje de error si existe --%>
            <%
                String errorMessage = (String) request.getSession().getAttribute("error");
                if (errorMessage != null) {
            %>
            <p class="error-message"><%= errorMessage %></p>
            <%
                    request.getSession().removeAttribute("error"); // Limpiar el mensaje para que no persista
                }
            %>
        </div>
    </body>
</html>
