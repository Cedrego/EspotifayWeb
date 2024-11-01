<%-- 
    Document   : ActualizarSUS
    Created on : 31 oct. 2024, 17:30:43
    Author     : tecnologo
--%>

<%@page import="Capa_Presentacion.DataSuscripcion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Suscripciones</title>
</head>
<style>
        /* Estilo de fondo y texto */
        body {
            background-color: #000;
            color: #FFF;
            font-family: 'Poppins', sans-serif;
        }

        h1, label {
            color: #FFF;
        }

        /* Estilo para select */
        select {
            background-color: #1a1a1a;
            color: #FFF;
            padding: 5px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            transition: box-shadow 0.3s ease;
        }

        select:hover, select:focus {
            box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
        }

        /* Estilo para botones */
        button {
            background-color: #1db954;
            color: black;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }

        button:hover {
            box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
        }
</style>
<body>
    <h1>Suscripciones de Cliente</h1>
    <form action="${pageContext.request.contextPath}/SvActualizarSUS" method="post">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Estado</th>
                <th>Tipo</th>
                <th>Última Modificación</th>
                <th>Nuevo Estado</th>
            </tr>
            <% 
                // Obtener la lista de suscripciones desde el atributo de solicitud
                List<DataSuscripcion> suscripciones = (List<DataSuscripcion>) request.getAttribute("suscripciones");
                request.removeAttribute("suscripciones");
                // Verificar si la lista no es nula
                if (suscripciones != null) {
                    for (DataSuscripcion suscripcion : suscripciones) {
            %>
                        <tr>
                            <td><%= suscripcion.getId() %></td>
                            <td><%= suscripcion.getEstado() %></td>
                            <td><%= suscripcion.getTipo() %></td>
                            <td><%= suscripcion.getUltimaModificacion().getDia() + "/" + suscripcion.getUltimaModificacion().getMes() + "/" + suscripcion.getUltimaModificacion().getAnio() %></td>
                            <td>
                                <% String estado = suscripcion.getEstado().toString(); %>
                                <% if ("Pendiente".equals(estado)) { %>
                                    <select name="nuevoEstado_<%= suscripcion.getId() %>">
                                        <option value="">No Cambiar</option>
                                        <option value="Cancelada">Cancelada</option>
                                    </select>
                                <% } else if ("Vencida".equals(estado)) { %>
                                    <select name="nuevoEstado_<%= suscripcion.getId() %>">
                                        <option value="">No Cambiar</option>
                                        <option value="Vigente">Vigente</option>
                                        <option value="Cancelada">Cancelada</option>
                                    </select>
                                <% } else { %>
                                    <%= estado %> <!-- Si el estado es Vigente o Cancelada, mostrar sin opciones -->
                                <% } %>
                            </td>
                        </tr>
            <% 
                    }
                } else { 
            %>
                    <tr><td colspan="5">No hay suscripciones disponibles.</td></tr>
            <% 
                } 
            %>
        </table>
        <button type="submit">Actualizar Estados</button>
    </form>
</body>
</html>
