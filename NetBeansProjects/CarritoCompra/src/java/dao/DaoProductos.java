package dao;

import dto.Productos;
import java.util.List;

public interface DaoProductos {

    public List<Productos> productosQry();
    
    public String getMessage();
}


