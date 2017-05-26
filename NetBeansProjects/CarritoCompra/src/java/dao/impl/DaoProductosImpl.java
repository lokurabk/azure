package dao.impl;

import dao.DaoProductos;
import dto.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import parainfo.sql.ConectaDb;

public class DaoProductosImpl implements DaoProductos {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoProductosImpl() {
        this.db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Productos> productosQry() {
        List<Productos> list = null;
        sql.append("SELECT ")
                .append("idproducto,")
                .append("producto,")
                .append("precio,")
                .append("stock,")
                .append("fotopath ")
                .append("FROM productos");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            list = new LinkedList<>();
            while (rs.next()) {
                Productos p = new Productos();

                p.setIdproducto(rs.getInt(1));
                p.setProducto(rs.getString(2));
                p.setPrecio(rs.getDouble(3));
                p.setStock(rs.getInt(4));
                p.setFotopath(rs.getString(5));

                list.add(p);
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

