package parainfo.carrito;

public class CarritoItem {

    private Integer idproducto;
    private String nombre;
    private Integer cantidad;
    private Double preciouni;

    public CarritoItem() {
    }

    public Double getPreciouni() {
        return preciouni;
    }

    public void setPreciouni(Double preciouni) {
        this.preciouni = preciouni;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }
}

