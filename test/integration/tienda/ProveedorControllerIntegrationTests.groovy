package tienda

import static org.junit.Assert.*
import org.junit.*

class ProveedorControllerIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraDarAltaUnProveedor() {
        println "debieraDarAltaUnProveedor"

        def controller = new ProveedorController()
        assert controller

        def model = controller.create()
        assert model
        assert model.proveedorInstance

        controller.params.nombre = 'TEST'
        controller.params.apellido = 'TEST'
        controller.params.rfc = '012345678912'
        controller.params.curp = '012345678912345678'
        controller.params.correoElectronico = 'TEST'
        controller.params.telefono = 'TEST'
        controller.save()

        assert controller.response.redirectedUrl.startsWith('/proveedor/show')

        List<Proveedor> proveedores = Proveedor.findAll()
        println "proveedores: ${proveedores.size()}"
        assertTrue proveedores.size() > 0
        assertEquals "012345678912" , proveedores.get(0).rfc

    }

    @Test
    void debieraModificarUnProveedor() {
        println "debieraModificarUnProveedor"

    	def proveedor = new Proveedor(
            nombre: 'TEST',
            apellido : 'TEST',
            rfc : '012345678912',
            curp : '012345678912345678',
            correoElectronico : 'TEST',
            telefono : 'TEST'
	    ).save()
        assertNotNull proveedor

        def controller = new ProveedorController()
        controller.params.id = proveedor.id

        def model = controller.show()
        assert model.proveedorInstance
        assertEquals "TEST", model.proveedorInstance.nombre

        controller.params.id = proveedor.id
        model = controller.edit()
        assert model.proveedorInstance
        assertEquals "TEST", model.proveedorInstance.nombre

        controller.params.nombre = 'TEST1'
        controller.update()
        println "response: ${controller.response.redirectedUrl}"
        assert controller.response.redirectedUrl.startsWith('/proveedor/show')

        proveedor.refresh()
        assertEquals 'TEST1', proveedor.nombre
    }

    @Test
    void debieraEliminarUnProveedor() {
        println "eliminarProveedor"

    	def proveedor = new Proveedor(
            nombre: 'TEST',
            apellido : 'TEST',
            rfc : '012345678912',
            curp : '012345678912345678',
            correoElectronico : 'TEST',
            telefono : 'TEST'
	    ).save()
	assertNotNull proveedor

        def controller = new ProveedorController()
        controller.params.id = proveedor.id

        def model = controller.show()
        assert model.proveedorInstance
        assertEquals "TEST", model.proveedorInstance.nombre

        controller.params.id = proveedor.id
        controller.delete()

        assert controller.response.redirectedUrl.startsWith("/proveedor/list")

        model = Proveedor.get(proveedor.id)
        assert !model
    }
}
