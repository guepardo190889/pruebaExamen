package general

import login.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.authentication.TestingAuthenticationToken

class BaseIntegrationTest extends GroovyTestCase {

    def authenticateAdmin() {
        def credentials = 'aeiou123   '
        def user = new Usuario(
                username:'blackdeath'
                ,password:credentials
            )
        def authorities = [new GrantedAuthorityImpl('ROLE_ADMINISTRADOR')]
        def principal = new GrailsUser(user.username,credentials,true,true,true,true,authorities,111)
        authenticate(principal,credentials,authorities)
    }
    
    
    def authenticateUser() {
        def credentials = 'aeiou123'
        def user = new Usuario(
                username:'sklm'
                ,password:credentials
            )
        def authorities = [new GrantedAuthorityImpl('ROLE_USUARIO')]
        def principal = new GrailsUser(user.username,credentials,true,true,true,true,authorities,13)
        authenticate(principal,credentials,authorities)
    }
    
    def authenticate(principal, credentials, authorities) {
        def authentication = new TestingAuthenticationToken(principal, credentials, authorities as GrantedAuthority[])
        authentication.authenticated = true
        SCH.context.authentication = authentication
        return authentication
    }

    def logout() {
        SCH.context.authentication = null
    }
}
