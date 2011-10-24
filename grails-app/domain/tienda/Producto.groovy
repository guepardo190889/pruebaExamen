package tienda

class Producto {

    String codigo
    String nombre
    BigDecimal precio = new BigDecimal("00.00")

    static belongsTo = [proveedor:Proveedor, grupo:Grupo]

    static constraints = {
    }
}
