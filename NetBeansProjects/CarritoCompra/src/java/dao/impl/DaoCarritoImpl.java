package dao.impl;

import dao.DaoCarrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import parainfo.carrito.CarritoCompras;
import parainfo.carrito.CarritoItem;
import parainfo.sql.ConectaDb;

public class DaoCarritoImpl implements DaoCarrito {

    private final ConectaDb db;
    private String message;

    public DaoCarritoImpl() {
        this.db = new ConectaDb();
    }

    @Override
    public String carritoAcepta(String cliente, CarritoCompras carrito) {
        String sql01 = "INSERT INTO ventas(cliente, fecha) VALUES(?, ?)";

        String sql02 = "INSERT INTO ventadetalles"
                + "(idventa, idproducto, cantidad) VALUES(?, ?, ?)";

        String sql03 = "UPDATE productos SET stock = stock - ? "
                + "WHERE idproducto = ?";

        try (Connection cn = db.getConnection();
                PreparedStatement ps01 = cn.prepareStatement(sql01,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement ps02 = cn.prepareStatement(sql02);
                PreparedStatement ps03 = cn.prepareStatement(sql03)) {

            cn.setAutoCommit(false); // desactiva autoCommit
            boolean ok = true;

            ps01.setString(1, cliente);
            ps01.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            // registra nueva venta
            ps01.executeUpdate();

            // obteniendo idventa generado
            Integer idventa = null;
            try (ResultSet rs = ps01.getGeneratedKeys()) {

                if (rs.next()) {
                    idventa = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo registrar Venta");
                }

            } catch (SQLException e) {
                message = e.getMessage();
                ok = false;
            }

            // si todo OK
            if (ok) {
                List<CarritoItem> list = carrito.getItems();

                for (CarritoItem item : list) {
                    ps02.setInt(1, idventa);
                    ps02.setInt(2, item.getIdproducto());
                    ps02.setInt(3, item.getCantidad());

                    // registra detalles de venta
                    int ctos = ps02.executeUpdate();

                    if (ctos > 0) {
                        ps03.setInt(1, item.getCantidad());
                        ps03.setInt(2, item.getIdproducto());

                        // actualiza stock (NO considera stock negativo, 
                        // por no ser preponderante ya que solo se desea mostrar
                        // funcionalidad de carrito de compra)
                        ctos = ps03.executeUpdate();

                        if (ctos == 0) {
                            message = "No se pudo actualizar Stock";
                            ok = false;
                            break;
                        }

                    } else {
                        message = "No se pudo registrar Detalle";
                        ok = false;
                        break;
                    }
                }
            }

            if (ok) {
                cn.commit();
            } else {
                cn.rollback();
            }

            cn.setAutoCommit(true); // activa autoCommit

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

