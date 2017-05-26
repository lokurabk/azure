package web.servlet;

import dao.DaoCarrito;
import dao.impl.DaoCarritoImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.carrito.CarritoCompras;
import parainfo.carrito.CarritoItem;
import parainfo.convert.DeString;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/Carrito"})
public class CarritoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        accion = (accion == null) ? "" : accion;
        String result = null;
        String target = "Productos?accion=QRY";

        switch (accion) {
            case "CARINS":
                Integer idproducto = DeString.aInteger(
                        request.getParameter("idproducto"));
                String producto = request.getParameter("producto");
                Double precio = DeString.aDouble(
                        request.getParameter("precio"));
                Integer cantidad = DeString.aInteger(
                        request.getParameter("cantidad"));

                // existe carrito
                CarritoCompras carrito = (CarritoCompras) 
                        request.getSession().getAttribute("carrito");

                if (carrito == null) {
                    carrito = new CarritoCompras();
                    request.getSession().setAttribute("carrito", carrito);
                }

                // adicionar a carrito
                CarritoItem item = carrito.getItem(idproducto);
                if (item == null) {
                    item = new CarritoItem();
                    item.setIdproducto(idproducto);
                    item.setNombre(producto);
                    item.setCantidad(cantidad);
                    item.setPreciouni(precio);

                    carrito.insertItem(item);
                } else {
                    result = "Producto \"" + producto
                            + "\" ya fue seleccionado";
                }
                break;

            case "CARDEL":
                List<Integer> ids
                        = DeString.ids(request.getParameter("ids"));

                if (ids != null) {
                    for (Integer x : ids) {
                        carrito = (CarritoCompras) 
                                request.getSession().getAttribute("carrito");

                        item = carrito.getItem(x);
                        carrito.deleteItem(item);
                    }
                } else {
                    result = "Lista de IDs Incorrecta";
                }
                break;

            case "CARCANCEL":
                request.getSession().removeAttribute("carrito");
                break;

            case "OK":
                String cliente = request.getParameter("cliente");

                if ((cliente == null) || (cliente.trim().length() == 0)) {
                    result = "Ingrese nombre de Cliente";
                    
                } else {
                    carrito = (CarritoCompras) 
                            request.getSession().getAttribute("carrito");

                    if ((carrito != null) && (carrito.getItemCount() > 0)) {
                        DaoCarrito daoCarrito = new DaoCarritoImpl();

                        result = daoCarrito.carritoAcepta(cliente, carrito);

                        if (result == null) {
                            request.getSession().removeAttribute("carrito");
                            result = "Compras de " + cliente + " Registradas";
                        }

                    } else {
                        result = "Carrito sin Productos";
                    }
                }

                break;

            case "":
                result = "Solicitud requerida";
                break;

            default:
                result = "Solicitud no reconocida";
        }

        if (result != null) {
            request.setAttribute("msg", result);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="doGet y doPost">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

