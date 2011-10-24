package tienda

import static org.junit.Assert.*
import org.junit.*
import general.*

class ProductoControllerIntegrationTests extends BaseIntegrationTest {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraDarAltaUnProducto() {
        println "debieraDarAltaUnProducto"

        authenticateAdmin()

        Grupo grupo = new Grupo(
            nombre: "TEST"
        ).save(flash:true)
        assertNotNull grupo

        Proveedor proveedor = new Proveedor(
            nombre : "TEST",
            apellido : "TEST",
            rfc : "0123456789123",
            curp : "012345678912345678",
            correoElectronico : "TEST",
            telefono : "TEST"
        ).save(flash:true)
        assertNotNull proveedor

        def controller = new ProductoController()
        assert controller

        def model = controller.create()
        assert model
        assert model.productoInstance

        controller.params.codigo = 'TEST'
        controller.params.nombre = 'TEST'
        controller.params.precio = new BigDecimal('0.00')
        controller.params.grupo = grupo
        controller.params.proveedor = proveedor
        controller.save()

        assert controller.response.redirectedUrl.startsWith('/producto/show')

        List<Producto> productos = Producto.findAll()
        println "productos: ${productos.size()}"
        assertTrue productos.size() > 0
        assertEquals "TEST" , productos.get(0).codigo

    }

    @Test
    void debieraModificarUnProducto() {
        println "debieraModificarUnProducto"

    	def producto = new Producto(
            nombre: 'TEST',
            apellido : 'TEST',
            rfc : '012345678912',
            curp : '012345678912345678',
            correoElectronico : 'TEST',
            telefono : 'TEST'
	    ).save()
        assertNotNull producto

        def controller = new ProductoController()
        controller.params.id = producto.id

        def model = controller.show()
        assert model.productoInstance
        assertEquals "TEST", model.productoInstance.nombre

        controller.params.id = producto.id
        model = controller.edit()
        assert model.productoInstance
        assertEquals "TEST", model.productoInstance.nombre

        controller.params.nombre = 'TEST1'
        controller.update()
        println "response: ${controller.response.redirectedUrl}"
        assert controller.response.redirectedUrl.startsWith('/producto/show')

        producto.refresh()
        assertEquals 'TEST1', producto.nombre
    }

    @Test
    void debieraEliminarUnProducto() {
        println "eliminarProducto"

    	def producto = new Producto(
            nombre: 'TEST',
            apellido : 'TEST',
            rfc : '012345678912',
            curp : '012345678912345678',
            correoElectronico : 'TEST',
            telefono : 'TEST'
	    ).save()
	assertNotNull producto

        def controller = new ProductoController()
        controller.params.id = producto.id

        def model = controller.show()
        assert model.productoInstance
        assertEquals "TEST", model.productoInstance.nombre

        controller.params.id = producto.id
        controller.delete()

        assert controller.response.redirectedUrl.startsWith("/producto/list")

        model = Producto.get(producto.id)
        assert !model
    }
}
