package tienda

import org.springframework.dao.DataIntegrityViolationException

class GrupoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [grupoInstanceList: Grupo.list(params), grupoInstanceTotal: Grupo.count()]
    }

    def create() {
        log.debug "params: ${params}"
        [grupoInstance: new Grupo(params)]
    }

    def save() {
        def grupoInstance = new Grupo(params)
        if (!grupoInstance.save(flush: true)) {
            render(view: "create", model: [grupoInstance: grupoInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'grupo.label', default: 'Grupo'), grupoInstance.id])
        redirect(action: "show", id: grupoInstance.id)
    }

    def show() {
        def grupoInstance = Grupo.get(params.id)
        if (!grupoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "list")
            return
        }

        [grupoInstance: grupoInstance]
    }

    def edit() {
        def grupoInstance = Grupo.get(params.id)
        if (!grupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "list")
            return
        }

        [grupoInstance: grupoInstance]
    }

    def update() {
        def grupoInstance = Grupo.get(params.id)
        if (!grupoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (grupoInstance.version > version) {
                grupoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'grupo.label', default: 'Grupo')] as Object[],
                          "Another user has updated this Grupo while you were editing")
                render(view: "edit", model: [grupoInstance: grupoInstance])
                return
            }
        }

        grupoInstance.properties = params

        if (!grupoInstance.save(flush: true)) {
            render(view: "edit", model: [grupoInstance: grupoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'grupo.label', default: 'Grupo'), grupoInstance.id])
        redirect(action: "show", id: grupoInstance.id)
    }

    def delete() {
        def grupoInstance = Grupo.get(params.id)
        if (!grupoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "list")
            return
        }

        try {
            grupoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'grupo.label', default: 'Grupo'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
