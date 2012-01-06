<%--
  By alari
  Since 1/4/12 11:36 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_youTube">
    <center><iframe width="420" height="315" src="http://www.youtube.com/embed/{{= youtubeId}}" frameborder="0" allowfullscreen></iframe></center>
</mk:tmpl>

<mk:tmpl id="edit_russiaRu">
    <embed src="http://www.russia.ru/player/main.swf?103" flashvars="name={{= videoId}}&from=blog&blog=true" width="448" height="252" bgcolor="#000000" allowScriptAccess="always" allowFullScreen="true"></embed>
</mk:tmpl>