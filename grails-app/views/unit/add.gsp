<%--
 * @author alari
 * @since 10/27/11 10:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <meta name="layout" content="mono"/>
      <title>Adding the Unit</title>

  </head>
  <body>

  <style>
      #unit-title{
          width: 100%;
          border: 0 solid transparent;
          font-size: 30px;
          font-family: Tahoma;
          height: 34px;
      }
      #unit-add{
          height: 350px;
          border: 1px solid gray;
          border-radius: 3px;
      }
      #unit-add-drop{
          background: #ffdab9;
          height: 100%;
      }
  </style>

  <h1><input id="unit-title" type="text" placeholder="Заголовок" name="title"/></h1>

  <div id="unit-content"></div>


  <div id="unit-add">
      <div class="span6" id="unit-add-drop">
           <form method="post" enctype="multipart/form-data">
          Left Zone (droppable)
          <input type="file" name="avatar"/>
          <input type="submit"/>

      </form>
      </div>
      <div class="span6">
        Main Zone There
      </div>
  </div>
  <br/>
  <mk:formActions>
      <input type="submit" class="btn primary"/>
  </mk:formActions>

  <r:require module="mirariUnitUpload"/>


  </body>
</html>