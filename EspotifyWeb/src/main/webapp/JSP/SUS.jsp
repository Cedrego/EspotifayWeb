<%-- 
    Document   : SUS
    Created on : 31 oct. 2024, 11:01:05
    Author     : tecnologo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
        function cargarMonto() {
                const tipoSUS = document.getElementById("TipoSus").value;
                const montoSUS = document.getElementById("montos");

                // Limpiar la tercera ComboBox
                montoSUS.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvSUS?TipoSUS=' + tipoSUS + '&_=' + new Date().getTime(), true);
                
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            montoSUS.innerHTML = xhr.responseText;
                        } else {
                            montoSUS.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        montoSUS.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();

            }
            </script>
    </head>
    <body>
        <h1>Bienvenido a Contratar Suscripción</h1>
        
        <form action="${pageContext.request.contextPath}/SvSUS" method="POST">
            <p><label for="filtroBusqueda">Ingrese las especificaciones de su Suscripción</label>
            <select id="TipoSus" name="TipoSus" onchange="cargarMonto();" required>
                <option value="">Seleccione la Duración</option>
                <option value="Semanal">Semanal</option>
                <option value="Mensual">Mensual</option>
                <option value="Anal">Anual</option>  
            </select>
            
            <p><label for="montos">Monto de su suscripción</label>
            <select id="montos" name="montos" required>
                <option value="">Monto de su suscripción</option>
            </select>
        </form>
    </body>
</html>
