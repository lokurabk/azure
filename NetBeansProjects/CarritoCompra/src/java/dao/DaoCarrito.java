package dao;

import parainfo.carrito.CarritoCompras;

public interface DaoCarrito {

    public String carritoAcepta(String cliente, CarritoCompras carrito);
    
    public String getMessage();
}

