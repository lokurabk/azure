package dao.impl;

import dao.DaoVentas;
import dto.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import parainfo.sql.ConectaDb;

public class DaoVentasImpl implements DaoVentas{

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoVentasImpl() {
        this.db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> ventasXcliente() {
        List<Object[]> list = null;
        sql.append("SELECT ")
                .append("ventas.cliente,")
                .append("DATE_FORMAT(ventas.fecha, '%d/%m/%Y %H:%i:%s'),")
                .append("SUM(ventadetalles.cantidad * productos.precio) ")
                .append("FROM ventas ")
                .append("INNER JOIN ventadetalles ")
                .append("ON ventas.idventa = ventadetalles.idventa ")
                .append("INNER JOIN productos ")
                .append("ON ventadetalles.idproducto = productos.idproducto ")
                .append("GROUP BY ventas.cliente, ventas.fecha ")
                .append("ORDER BY ventas.cliente ASC, ventas.fecha DESC");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            list = new LinkedList<>();
            while (rs.next()) {
                Object[] reg = new Object[3];

                reg[0] = rs.getString(1);
                reg[1] = rs.getString(2);
                reg[2] = rs.getDouble(3);

                list.add(reg);
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return list;        
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}
