package login

class Usuario {

	transient springSecurityService

        String nombre
        String apellido
        String correoElectronico
        Date fechaNacimiento
        String telefono

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
            nombre blank:false
            apellido blank:false
            correoElectronico blank:false
            fechaNacimiento blank:false
            correoElectronico blank:false
            telefono blank:false
            username blank: false, unique: true
            password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this).collect { it.rol } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
