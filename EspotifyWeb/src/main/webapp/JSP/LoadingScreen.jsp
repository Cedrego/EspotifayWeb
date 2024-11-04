<%-- 
    Document   : LoadingScreen
    Created on : 4 nov. 2024, 1:29:44 a. m.
    Author     : cedre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cargando...</title>
        <style>
            /* Estilo para centrar el mensaje */
            .loading-message {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 30vh;
                font-size: 1.2em;
                background-color: #000;
                color: #FFF;
            }
        </style>
        <script>
            // Redirige al servlet después de un breve retraso
            window.onload = function() {
                setTimeout(function() {
                    window.location.href = '<%= request.getContextPath() %>/SvConsultarPerfil';
                }, 500); // Ajusta el retraso si es necesario
            };
        </script>
    </head>
    <body>
        <div class="loading-message">
            Cargando datos, por favor espere . . .
        </div>
    </body>
</html>