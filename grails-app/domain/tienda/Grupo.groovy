package tienda

class Grupo {

    def nombre

    static constraints = {
        nombre blank:false
    }

    String toString() {
        return nombre
    }
}
