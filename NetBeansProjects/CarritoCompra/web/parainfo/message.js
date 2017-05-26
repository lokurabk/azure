function message(titulo, msg) {
    $("#message").html(msg);

    $("#dlg_message").dialog({
        title: titulo,
        modal: true,
        width: 400,
        buttons: {
            "Cerrar": function () {
                $(this).dialog("close");
            }
        }
    });
}
