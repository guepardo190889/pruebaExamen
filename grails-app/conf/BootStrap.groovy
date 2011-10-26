import tienda.*
import login.*

class BootStrap {

    def springSecurityService
    def sessionFactory

    def init = { servletContext ->

        log.info "Creando Roles"
        def rolAdministrador = login.Rol.findByAuthority('ROLE_ADMINISTRADOR')
        if (!rolAdministrador) {
            rolAdministrador = new login.Rol(authority: 'ROLE_ADMINISTRADOR').save(flush:true)
        }
        def rolUsuario = login.Rol.findByAuthority('ROLE_USUARIO')
        if (!rolUsuario) {
            rolUsuario = new login.Rol(authority: 'ROLE_USUARIO').save(flush:true)
        }

        log.info "Creando Usuarios"
        def administrador = login.Usuario.findByUsername("blackdeath")
        if(!administrador){
            administrador = new login.Usuario(
                nombre: 'Seth Karim',
                apellido: 'Luis Martinez',
                correoElectronico: 'cuentasvarias@hotmail.com',
                username:'blackdeath',
                password : 'aeiou123',
                enabled : true
            ).save(flush:true)
        }
        if (!administrador.authorities.contains(rolAdministrador)){
            UsuarioRol.create(administrador, rolAdministrador)
        }

        def usuario = login.Usuario.findByUsername("sklm")
        if(!usuario){
            usuario = new login.Usuario(
                nombre: 'Carlos',
                apellido: 'Trejo Montoya',
                correoElectronico: 'cuentasvarias@hotmail.com',
                username:'sklm',
                password : 'aeiou123',
                enabled : true
            ).save(flush:true)
        }
        if (!usuario.authorities.contains(rolUsuario)){
            UsuarioRol.create(usuario, rolUsuario)
        }
    
        log.info "Creando Grupo General"
        def grupo = Grupo.findByNombre("GENERAL")
        if(!grupo){
            grupo = new tienda.Grupo(
                nombre : "GENERAL"
            ).save(flush:true)
        }

        log.info "Creando Proveedor General"
        def proveedor = Proveedor.findByRfc("0123456789123")
        if(!proveedor){
            proveedor = new Proveedor(
                nombre : "TEST",
                apellido : "TEST",
                rfc : "0123456789123",
                curp : "012345678912345678",
                correoElectronico : "TEST",
                telefono: "TEST"
            ).save(flush:true)
        }

        log.info "Creando Productos de Prueba"
        def producto = Producto.findByCodigo("A")
        if(!producto){
            producto = new Producto(
                codigo : "A",
                nombre : "TEST",
                precio : new BigDecimal("10.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }
        producto = Producto.findByCodigo("AB")
        if(!producto){
            producto = new Producto(
                codigo : "AB",
                nombre : "TEST",
                precio : new BigDecimal("20.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }
        producto = Producto.findByCodigo("B")
        if(!producto){
            producto = new Producto(
                codigo : "B",
                nombre : "TEST",
                precio : new BigDecimal("30.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }
        producto = Producto.findByCodigo("BA")
        if(!producto){
            producto = new Producto(
                codigo : "BA",
                nombre : "TEST",
                precio : new BigDecimal("40.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }
        producto = Producto.findByCodigo("C")
        if(!producto){
            producto = new Producto(
                codigo : "C",
                nombre : "TEST",
                precio : new BigDecimal("50.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }
        producto = Producto.findByCodigo("SEKALUMA")
        if(!producto){
            producto = new Producto(
                codigo : "SEKALUMA",
                nombre : "TEST",
                precio : new BigDecimal("60.00"),
                proveedor : proveedor,
                grupo : grupo
            ).save(flush:true)
        }


    }
    def destroy = {
        
    }
}