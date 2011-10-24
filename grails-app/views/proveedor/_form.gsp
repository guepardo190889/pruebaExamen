<%@ page import="tienda.Proveedor" %>



<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="proveedor.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${proveedorInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'apellido', 'error')} required">
	<label for="apellido">
		<g:message code="proveedor.apellido.label" default="Apellido" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apellido" required="" value="${proveedorInstance?.apellido}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'rfc', 'error')} required">
	<label for="rfc">
		<g:message code="proveedor.rfc.label" default="Rfc" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="rfc" maxlength="13" required="" value="${proveedorInstance?.rfc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'curp', 'error')} required">
	<label for="curp">
		<g:message code="proveedor.curp.label" default="Curp" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="curp" maxlength="18" required="" value="${proveedorInstance?.curp}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'correoElectronico', 'error')} required">
	<label for="correoElectronico">
		<g:message code="proveedor.correoElectronico.label" default="Correo Electronico" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="correoElectronico" required="" value="${proveedorInstance?.correoElectronico}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: proveedorInstance, field: 'telefono', 'error')} required">
	<label for="telefono">
		<g:message code="proveedor.telefono.label" default="Telefono" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="telefono" required="" value="${proveedorInstance?.telefono}"/>
</div>

