<%-- 
    Document   : RankingUsuarios
    Created on : 17 nov. 2024, 8:19:27 p. m.
    Author     : Franco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            /* Fondo y estilos de texto */
            body {
                background-color: #000;
                color: #FFF;
                font-family: 'Poppins', sans-serif;
            }

            h1 {
                color: #FFF;
                text-align: center;
                margin-top: 20px;
            }

            /* Estilo de la tabla */
            table {
                width: 80%;
                margin: 20px auto;
                border-collapse: collapse;
                background-color: #1a1a1a;
                border-radius: 10px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
            }

            th, td {
                padding: 12px;
                text-align: center;
                border: 1px solid #444;
            }

            th {
                background-color: #1db954;
                color: black;
                font-size: 18px;
            }

            td {
                color: #FFF;
                font-size: 16px;
            }

            /* Estilo para filas de la tabla */
            tr:nth-child(even) {
                background-color: #2c2c2c;
            }

            tr:hover {
                background-color: #444;
                cursor: pointer; /* Hace que las filas sean "clickeables" */
                box-shadow: 0 0 10px #1db954;
            }

            /* Estilo de la fila clickeable */
            tr:active {
                background-color: #1db954;
                color: #000;
            }
        </style>
        
        <script>
            function cargarRankingUsuarios() {
                const ranking = document.getElementById("ranking").getElementsByTagName("tbody")[0]; // Obtener el tbody

                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvRankingUsuarios?_=' + new Date().getTime(), true);

                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Limpiar filas anteriores
                        ranking.innerHTML = '';

                        // Verificar si la respuesta contiene datos
                        if (xhr.responseText.trim() !== "") {
                            ranking.innerHTML = xhr.responseText;
                        } else {
                            ranking.innerHTML = '<tr><td colspan="3">No se encontraron resultados</td></tr>';
                        }
                    } else {
                        ranking.innerHTML = '<tr><td colspan="3">Error al cargar los datos</td></tr>';
                    }
                };
                xhr.send();
            };
            
            function mostrarDetallesUsuario(nickUsuario) {
                const detailsRow = document.getElementById('detallesUsuario' + nickUsuario);

                // Mostrar la fila de detalles si está oculta, o ocultarla si está visible
                if (detailsRow.style.display === 'none' || detailsRow.style.display === '') {
                    detailsRow.style.display = 'table-row'; // Mostrar la fila
                } else {
                    detailsRow.style.display = 'none'; // Ocultar la fila
                }
            }
            
            window.onload = function() {
                cargarRankingUsuarios();
            };
        </script>
    </head>
    <body>
        <h1>Ranking de Usuarios</h1>
        
        <div id="ranking">
            <table>
                <thead>
                    <tr>
                        <th>Posición</th>
                        <th>Nombre</th>
                        <th>Seguidores</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- aquí se llenan las filas -->
                </tbody>
            </table>
        </div>
        
    </body>
</html>
