package tienda

import static org.junit.Assert.*
import org.junit.*

class GrupoControllerIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void debieraDarAltaUnGrupo() {
        println "debieraDarAltaUnGrupo"

        def controller = new GrupoController()
        assert controller

        def model = controller.create()
        assert model
        assert model.grupoInstance

        controller.params.nombre = 'TEST'
        controller.save()

        assert controller.response.redirectedUrl.startsWith('/grupo/show')

        List<Grupo> grupos = Grupo.findAll()
        println "grupos: ${grupos.size()}"
        assertTrue grupos.size() > 0
        
        assertEquals "TEST" , grupos.get(1).nombre
        
    }

     @Test
    void debieraModificarUnGrupo() {
        println "debieraModificarUnGrupo"

    	def grupo = new Grupo(
            nombre: 'TEST'
	    ).save()
        assertNotNull grupo

        def controller = new GrupoController()
        controller.params.id = grupo.id

        def model = controller.show()
        assert model.grupoInstance
        assertEquals "TEST", model.grupoInstance.nombre

        controller.params.id = grupo.id
        model = controller.edit()
        assert model.grupoInstance
        assertEquals "TEST", model.grupoInstance.nombre

        controller.params.nombre = 'TEST1'
        controller.update()
        println "response: ${controller.response.redirectedUrl}"
        assert controller.response.redirectedUrl.startsWith('/grupo/show')

        grupo.refresh()
        assertEquals 'TEST1', grupo.nombre
    }

    @Test
    void debieraEliminarUnGrupo() {
        println "eliminarGrupo"

        def grupo = new Grupo(
            nombre: 'TEST'
	    ).save()
	assertNotNull grupo

        def controller = new GrupoController()
        controller.params.id = grupo.id

        def model = controller.show()
        assert model.grupoInstance
        assertEquals "TEST", model.grupoInstance.nombre

        controller.params.id = grupo.id
        controller.delete()
        
        assert controller.response.redirectedUrl.startsWith("/grupo/list")

        model = Grupo.get(grupo.id)
        assert !model
    }
}
