<%-- 
    Document   : ActualizarSUS
    Created on : 31 oct. 2024, 17:30:43
    Author     : tecnologo
--%>

<%@page import="Capa_Presentacion.DataSuscripcion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
<!DOCTYPE html>
<html>
<head>
    <title>Suscripciones</title>
</head>
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
