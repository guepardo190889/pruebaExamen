package tienda

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMINISTRADOR'])
class ProductoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [productoInstanceList: Producto.list(params), productoInstanceTotal: Producto.count()]
    }

    def create() {
        [productoInstance: new Producto(params)]
    }

    def save() {
        def productoInstance = new Producto(params)
        if (!productoInstance.save(flush: true)) {
            render(view: "create", model: [productoInstance: productoInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'producto.label', default: 'Producto'), productoInstance.id])
        redirect(action: "show", id: productoInstance.id)
    }

    def show() {
        def productoInstance = Producto.get(params.id)
        if (!productoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
            return
        }

        [productoInstance: productoInstance]
    }

    def edit() {
        def productoInstance = Producto.get(params.id)
        if (!productoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
            return
        }

        [productoInstance: productoInstance]
    }

    def update() {
        def productoInstance = Producto.get(params.id)
        if (!productoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (productoInstance.version > version) {
                productoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'producto.label', default: 'Producto')] as Object[],
                          "Another user has updated this Producto while you were editing")
                render(view: "edit", model: [productoInstance: productoInstance])
                return
            }
        }

        productoInstance.properties = params

        if (!productoInstance.save(flush: true)) {
            render(view: "edit", model: [productoInstance: productoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'producto.label', default: 'Producto'), productoInstance.id])
        redirect(action: "show", id: productoInstance.id)
    }

    def delete() {
        def productoInstance = Producto.get(params.id)
        if (!productoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
            return
        }

        try {
            productoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def buscarProducto(){

    }

    def mostrarProducto(){
        log.debug "Params: ${params}"
        def productoInstance = Producto.get(params.productoId)

        log.debug "productoInstance: ${productoInstance}}"
        println "Producto Instance: ${productoInstance}"

        if (!productoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'producto.label', default: 'Producto'), params.id])
            redirect(action: "list")
            return
        }

        [productoInstance: productoInstance]
    }

    def buscarProductosAutocomplete() {
        log.debug("Params: $params")
        def filtro = "%${params.term}%"
        def productos = Producto.executeQuery("""
            select producto
            from Producto producto
            where producto.codigo
            like :filtro
        """, [filtro: filtro])
        def lista = []
        for(producto in productos) {
            lista << [id:producto.id, value:producto.codigo]
        }
        log.debug("Lista: $lista")
        log.debug("ListaJSON: ${lista as JSON}")
        render lista as JSON
    }
}
