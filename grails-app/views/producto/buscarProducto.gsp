<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta name="layout" content="main">
    <title>Producto - Buscar Producto</title>
    <r:require module="jquery-ui" />
  </head>
  <body>
    <h1>Busqueda de Productos</h1>

    <div class="body">

        <g:form action="show" >
          <g:hiddenField name="id" value="" />
            <div class="fieldcontain ${hasErrors(bean: producto, field: 'codigo', 'error')} required">
              <label for="codigo">
                <g:message code="producto.codigo.label" default="Codigo" />
              </label>
              <g:textField name="codigo" value=""/>
            </div>
          <fieldset class="buttons">
            <g:submitButton name="buscaProductoBtn"  value="mostrarProducto" />
          </fieldset>
        </g:form>
    </div>

        <r:script>
          $(document).ready(function() {
            $("input#codigo").autocomplete({
              source: '${createLink(action:'buscarProductosAutocomplete')}',
              select: function(event, ui) {
                $("input#id").val(ui.item.id);
              }
            });

            $("input#id").focus();
          });
        </r:script>
  </body>
</html>