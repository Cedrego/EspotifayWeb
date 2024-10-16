<%-- 
    Document   : index
    Created on : 15 oct. 2024, 7:50:40 p. m.
    Author     : cedre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Background Image Example</title>
        <style>
            body {
                margin: 0; /* Remove default margins */
                height: 100vh; /* Make body height 100% of the viewport */
                background-image: url('images/cat.gif'); /* Image is in the images folder */
                background-size: cover; /* Cover the entire page */
                background-repeat: no-repeat; /* Prevent image from repeating */
                background-position: center; /* Center the image */
            }
        </style>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%String hola = "Hello World Desde Java!! =)";%>
        <%=hola%>
        <br><img src="images\MikuDance.gif" alt="A Wild Miku Appears!"/>
    </body>
</html>
