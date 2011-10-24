package tienda

class Proveedor {

    String nombre
    String apellido
    String rfc
    String curp
    String correoElectronico
    String telefono

    static constraints = {
        nombre blank:false
        apellido blank:false
        rfc blank:false, size:12..13
        curp blank:false, size:18..18
        correoElectronico blank:false
        telefono blank:false
    }

    String toString() {
        return "${nombre}, ${rfc}, ${curp}"
    }
}
