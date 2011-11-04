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
  <h1>${unit.title ?: unit.name} of ${unit.space}</h1>

  <center><unit:pageImage for="${unit}"/></center>

  <mk:formActions>
      <unit:ifCanEdit unit="${unit}">
          <unit:link for="${unit}" action="setDraft" params="[draft:!unit.draft]"><button class="btn primary">ch draft
          (now:<g:formatBoolean
                  boolean="${unit.draft}"/>)</button></unit:link>
      </unit:ifCanEdit>
      <unit:ifCanDelete unit="${unit}">
          <unit:link for="${unit}" action="delete"><button class="btn danger">delete</button></unit:link>
      </unit:ifCanDelete>
  </mk:formActions>

  </body>
</html>