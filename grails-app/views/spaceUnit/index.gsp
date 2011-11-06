<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <meta name="layout" content="mono"/>
      <title>${unit}</title>
  </head>
  <body>
  <h1>${unit}</h1>

  <center><unit:pageImage for="${unit}"/></center>

  <mk:formActions>
      <unit:ifCanEdit unit="${unit}">
          <unit:link for="${unit}" action="setDraft" params="[draft:!unit.draft]">

              <button class="btn primary"><g:message code="unit.edit.setDraftTo.${unit.draft ? 'false' : 'true'}"/></button></unit:link>
      </unit:ifCanEdit>
      <unit:ifCanDelete unit="${unit}">
          <unit:link for="${unit}" action="delete"><button class="btn danger">
              <g:message code="unit.delete.button"/>
          </button></unit:link>
      </unit:ifCanDelete>
  </mk:formActions>

  </body>
</html>