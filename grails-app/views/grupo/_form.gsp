<%@ page import="tienda.Grupo" %>



<div class="fieldcontain ${hasErrors(bean: grupoInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="grupo.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${grupoInstance?.nombre}"/>
</div>

