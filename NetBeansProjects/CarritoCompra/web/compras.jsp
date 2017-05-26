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
        <link href="parainfo/form.css" rel="stylesheet" type="text/css"/>
        <link href="parainfo/a.css" rel="stylesheet" type="text/css"/>
        <link href="parainfo/message.css" rel="stylesheet" type="text/css"/>

        <script src="jq/jquery-2.2.0.min.js" type="text/javascript"></script>
        <script src="jq/jquery-ui.min.js" type="text/javascript"></script>
        <script src="parainfo/table.js" type="text/javascript"></script>
        <script src="parainfo/form.js" type="text/javascript"></script>
        <script src="parainfo/message.js" type="text/javascript"></script>

        <script src="js/compras.js" type="text/javascript"></script>
    </head>
    <body>
        <fmt:setLocale value="en_US"/>

        <%-- visor de productos --%>
        <div id="main">
            <c:forEach var="p" items="${list}">
                <div class="vproducto">
                    <h4 style="margin-bottom: 2px">${p.producto}</h4>

                    <p style="margin: 0px;">
                        <a href="#" 
                           onclick="carritoDlg('${p.idproducto}', '${p.producto}', '${p.precio}')">
                            <img src="${p.fotopath}" alt=""/></a>
                    </p>

                    <p style="margin: 0px;">
                        Precio: S/. 
                        <span style="font-weight: bold">
                            <fmt:formatNumber type="number" 
                                              pattern="#,###,###.00" 
                                              value="${p.precio}"/>
                        </span>
                    </p>

                    <p style="text-align: right;margin: 2px;">
                        <a href="#" class="parainfo" 
                           onclick="carritoDlg('${p.idproducto}', '${p.producto}', '${p.precio}')">
                            comprar</a> 
                        <a href="#" 
                           onclick="carritoDlg('${p.idproducto}', '${p.producto}', '${p.precio}')">
                            <img src="images/carrito.jpg" alt="" 
                                 style="vertical-align: middle"/></a>
                    </p>
                </div>
            </c:forEach>
            <div style="clear: left"></div>
        </div>

        <%-- grilla de productos seleccionados --%>
        <form action="Carrito" method="post" class="parainfo">
            <input type="hidden" name="accion" value="OK"/>
            <table class="parainfo" style="margin: auto;width: 502px">
                <thead>
                    <tr>
                        <td>Producto</td>
                        <td>Precio</td>
                        <td>Cantidad</td>
                        <td>Total</td>
                        <th class="crud">
                            <a class="del" href="#" onclick="carritoDel();"
                               title="Retirar"><span></span></a>
                        </th>
                    </tr>
                </thead>

                <tfoot>
                    <tr>
                        <td colspan="3" style="text-align: right">
                            Total a pagar S/. 
                        </td>
                        <td style="text-align: right">
                            <fmt:formatNumber type="number" 
                                              pattern="#,###,###.00" 
                                              value="${carrito.pagoTotal}"/>
                        </td>
                        <th>&nbsp;</th>
                    </tr>
                </tfoot>

                <tbody id="carritoItems">
                    <c:forEach var="c" items="${carrito.items}">
                        <tr>
                            <td>${c.nombre}</td>
                            <td style="text-align: right">
                                <fmt:formatNumber type="number" 
                                                  pattern="#,###,###.00" 
                                                  value="${c.preciouni}"/>
                            </td>
                            <td style="text-align: right">${c.cantidad}</td>
                            <td style="text-align: right">
                                <fmt:formatNumber 
                                    type="number" 
                                    pattern="#,###,###.00" 
                                    value="${c.preciouni * c.cantidad}"/>
                            </td>
                            <th>
                                <input type="checkbox" name="carritoDel" 
                                       value="${c.idproducto}"/>
                            </th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <p id="carritoFin" style="text-align: center;margin: 20px">
                <input type="button" onclick="acepta();" 
                       style="margin-right: 20px"
                       value="Acepta Compra"/>

                <input type="button" onclick="cancela();"
                       value="Cancela Compra"/>
            </p>
        </form>

        <%-- dialogo de compra --%>
        <div style="display:none">
            <div id="dcarrito" title="Carrito de Compras">
                <form action="Carrito" method="post" class="parainfo" 
                      id="carritoIns">
                    <input type="hidden" name="accion" value="CARINS"/>
                    <input type="hidden" name="idproducto" id="idproducto"/>

                    <table class="tabla">
                        <tr>
                            <td>Producto</td>
                            <td>
                                <input type="text" style="width: 200px" 
                                       name="producto" id="producto"
                                       readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Precio</td>
                            <td>
                                <input type="text" style="width: 100px" 
                                       id="precio" name="precio"
                                       readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Cantidad</td>
                            <td>
                                <select name="cantidad" id="cantidad" 
                                        style="width: 110px">
                                    <c:forEach var="x" begin="1" end="10">
                                        <option value="${x}">${x}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <%-- dialogo final de acepta compra --%>
        <div style="display:none">
            <div id="dacepta" title="Acepta Compras">
                <form action="Carrito" method="post" class="parainfo" 
                      id="carritoOk">
                    <input type="hidden" name="accion" value="OK"/>

                    <table class="tabla">
                        <tr>
                            <td>Cliente</td>
                            <td>
                                <input type="text" style="width: 200px" 
                                       name="cliente" id="cliente"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <p style="text-align: center">
            <a href="Ventas?accion=QRY" 
               class="parainfo">Ver Lista de Ventas por Cliente</a><br/>
            <a href="Productos?accion=QRY&target=stocks" 
               class="parainfo">Ver Precios y Stock de Productos</a>
        </p>

        <%-- mensajes del servidor --%>
        <c:if test="${msg != null}">
            <div class="msg_error ui-state-highlight ui-corner-all">
                ${msg}</div>
        </c:if>

        <%-- mensajes lado del cliente --%>
        <div style="display: none">
            <div id="dlg_message"><p id="message"></p></div>
        </div>
    </body>
</html>

