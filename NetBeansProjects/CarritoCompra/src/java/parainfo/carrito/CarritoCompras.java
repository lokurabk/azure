package parainfo.carrito;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CarritoCompras {

    private List<CarritoItem> _items;

    public List getItems() {
        return _items;
    }

    public CarritoCompras() {
        _items = new LinkedList<>();
    }

    public void insertItem(CarritoItem p) {
        _items.add(p);
    }

    public void deleteItem(CarritoItem p) {
        _items.remove(p);
    }

    public void updateItem(CarritoItem p) {
        CarritoItem bc = getItem(p.getIdproducto());
        int index = _items.indexOf(bc);
        _items.set(index, p);
    }

    public CarritoItem getItem(Integer id) {
        CarritoItem item = null;

        for (CarritoItem p : _items) {
            if (p.getIdproducto().intValue() == id.intValue()) {
                item = p;
                break;
            }
        }

        return item;
    }

    public int getItemCount() {
        return _items.size();
    }

    public void empty() {
        _items = new LinkedList();
    }

    public boolean isEmpty() {
        return (_items.isEmpty());
    }

    public double getPagoTotal() {
        Iterator i = _items.iterator();
        double total = 0D;

        while (i.hasNext()) {
            CarritoItem p = (CarritoItem) i.next();
            total += (p.getCantidad() * p.getPreciouni());
        }

        return total;
    }
}

