<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <meta name="layout" content="mono"/>
      <title>Unit ${unit.title}</title>
  </head>
  <body>
  <h1>${unit.title ?: unit.name}; ${unit.space}</h1>
    <pre>
  ${unit.properties}
      </pre>
  </body>
</html>