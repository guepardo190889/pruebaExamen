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

        authenticateAdmin()

        def controller = new GrupoController()
        //assert controller

        def model = controller.create()
        assert model
        assert model.grupo

        controller.params.nombre = 'TEST'
        controller.save()

        //assert controller

        assert controller.response.redirectedUrl.startsWith('/grupo/show')

        def grupos = Grupo.findAll()
        assertTrue grupos.size() > 0
        println "grupos: ${grupos.size()}"
    }
}
