$(function () {
    if($("#carritoItems tr").length > 0) {
        $("#carritoFin").show();
    } else {
        $("#carritoFin").hide();
    }
});

function carritoDlg(idproducto, producto, precio) {
    $("#idproducto").val(idproducto);
    $("#producto").val(producto);
    $("#precio").val(precio);
    $("#cantidad").val("1");

    $("#dcarrito").dialog({
        modal: true,
        width: 320,
        buttons: {
            "Acepta Producto": function () {
                $("#carritoIns").submit();
            },
            "Cancelar": function () {
                $(this).dialog("close");
            }
        }
    });
}

function carritoDel() {
    var ids = [];

    $("input[name='carritoDel']:checked").each(function () {
        ids.push($(this).val());
    });

    if (ids.length === 0) {
        message("Advertencia", "Seleccione fila(s) a Retirar");

    } else {
        $("#message").html("¿Retirar registro(s)?");

        $("#dlg_message").dialog({
            modal: true,
            title: "Advertencia",
            width: 340,
            buttons: {
                "No": function () {
                    $(this).dialog("close");
                },
                "Si": function () {
                    $(this).dialog("close");
                    window.location = "Carrito?accion=CARDEL&ids="
                            + ids.toString();
                }
            }
        });
    }
}

function cancela() {
    $("#message").html("¿Anular Compras de Cliente?");
    $("#dlg_message").dialog({
        modal: true,
        title: "Anular Compras",
        width: 340,
        buttons: {
            "No": function () {
                $(this).dialog("close");
            },
            "Si": function () {
                $(this).dialog("close");
                window.location = "Carrito?accion=CARCANCEL";
            }
        }
    });
}

function acepta() {
    $("#cliente").val("Cliente No Habitual");
    
    $("#dacepta").dialog({
        modal: true,
        width: 320,
        buttons: {
            "Acepta Producto": function () {
                $("#carritoOk").submit();
            },
            "Cancelar": function () {
                $(this).dialog("close");
            }
        }
    });
}

