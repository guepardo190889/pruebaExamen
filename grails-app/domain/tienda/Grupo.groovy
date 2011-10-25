package tienda

class Grupo {

    String nombre

    static constraints = {
        nombre blank:false
    }

    String toString() {
        return nombre
    }
}
