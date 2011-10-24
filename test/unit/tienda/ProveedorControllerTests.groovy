package tienda



import org.junit.*
import grails.test.mixin.*
import javax.servlet.http.HttpServletResponse

@TestFor(ProveedorController)
@Mock(Proveedor)
class ProveedorControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/proveedor/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.proveedorInstanceList.size() == 0
        assert model.proveedorInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.proveedorInstance != null
    }

    void testSave() {
        controller.save()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.save()

        assert model.proveedorInstance != null
        assert view == '/proveedor/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/proveedor/show/1'
        assert controller.flash.message != null
        assert Proveedor.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/proveedor/list'


        populateValidParams(params)
        def proveedor = new Proveedor(params)

        assert proveedor.save() != null

        params.id = proveedor.id

        def model = controller.show()

        assert model.proveedorInstance == proveedor
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/proveedor/list'


        populateValidParams(params)
        def proveedor = new Proveedor(params)

        assert proveedor.save() != null

        params.id = proveedor.id

        def model = controller.edit()

        assert model.proveedorInstance == proveedor
    }

    void testUpdate() {

        controller.update()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/proveedor/list'

        response.reset()


        populateValidParams(params)
        def proveedor = new Proveedor(params)

        assert proveedor.save() != null

        // test invalid parameters in update
        params.id = proveedor.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/proveedor/edit"
        assert model.proveedorInstance != null

        proveedor.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/proveedor/show/$proveedor.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        proveedor.clearErrors()

        populateValidParams(params)
        params.id = proveedor.id
        params.version = -1
        controller.update()

        assert view == "/proveedor/edit"
        assert model.proveedorInstance != null
        assert model.proveedorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/proveedor/list'

        response.reset()

        populateValidParams(params)
        def proveedor = new Proveedor(params)

        assert proveedor.save() != null
        assert Proveedor.count() == 1

        params.id = proveedor.id

        controller.delete()

        assert Proveedor.count() == 0
        assert Proveedor.get(proveedor.id) == null
        assert response.redirectedUrl == '/proveedor/list'
    }
}
