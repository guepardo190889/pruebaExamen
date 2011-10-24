package tienda



import org.junit.*
import grails.test.mixin.*
import javax.servlet.http.HttpServletResponse

@TestFor(ProductoController)
@Mock(Producto)
class ProductoControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/producto/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.productoInstanceList.size() == 0
        assert model.productoInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.productoInstance != null
    }

    void testSave() {
        controller.save()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.save()

        assert model.productoInstance != null
        assert view == '/producto/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/producto/show/1'
        assert controller.flash.message != null
        assert Producto.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/producto/list'


        populateValidParams(params)
        def producto = new Producto(params)

        assert producto.save() != null

        params.id = producto.id

        def model = controller.show()

        assert model.productoInstance == producto
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/producto/list'


        populateValidParams(params)
        def producto = new Producto(params)

        assert producto.save() != null

        params.id = producto.id

        def model = controller.edit()

        assert model.productoInstance == producto
    }

    void testUpdate() {

        controller.update()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/producto/list'

        response.reset()


        populateValidParams(params)
        def producto = new Producto(params)

        assert producto.save() != null

        // test invalid parameters in update
        params.id = producto.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/producto/edit"
        assert model.productoInstance != null

        producto.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/producto/show/$producto.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        producto.clearErrors()

        populateValidParams(params)
        params.id = producto.id
        params.version = -1
        controller.update()

        assert view == "/producto/edit"
        assert model.productoInstance != null
        assert model.productoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/producto/list'

        response.reset()

        populateValidParams(params)
        def producto = new Producto(params)

        assert producto.save() != null
        assert Producto.count() == 1

        params.id = producto.id

        controller.delete()

        assert Producto.count() == 0
        assert Producto.get(producto.id) == null
        assert response.redirectedUrl == '/producto/list'
    }
}
