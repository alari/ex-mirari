<%--
  By alari
  Since 12/15/11 6:54 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="editAudio">
        <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к звуку (placeholder)"/>
        <audio data-bind="audio: '<g:resource dir="/js/mediaelement/"/>'" src="{{= params.mpeg}}"></audio>
</mk:tmpl>

<r:require module="mediaelement"/>