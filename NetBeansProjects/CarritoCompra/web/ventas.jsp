<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>parainformaticos.com</title>
        <link href="jq/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
        <link href="parainfo/table.css" rel="stylesheet" type="text/css"/>
        <link href="parainfo/a.css" rel="stylesheet" type="text/css"/>

        <script src="jq/jquery-2.2.0.min.js" type="text/javascript"></script>
        <script src="jq/jquery-ui.min.js" type="text/javascript"></script>
        <script src="parainfo/table.js" type="text/javascript"></script>
    </head>
    <body>
        <fmt:setLocale value="en_US"/>
        
        <table class="parainfo" style="margin: auto;width: 400px">
            <thead>
                <tr>
                    <td>Cliente</td>
                    <td>Fecha y Hora</td>
                    <td>Pago S/.</td>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="v" items="${list}">
                    <tr>
                        <td>${v[0]}</td>
                        <td>${v[1]}</td>
                        <td style="text-align: right">
                            <fmt:formatNumber type="number" 
                                              pattern="#,###,###.00" 
                                              value="${v[2]}"/>
                        </td>
                        
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p style="text-align: center">
            <a href="index.jsp" class="parainfo">Home</a>
        </p>

        <%-- mensajes del servidor --%>
        <c:if test="${msg != null}">
        <dib class="msg_error 
             ui-state-highlight ui-corner-all">${msg}</div>
        </c:if>
</body>
</html>
