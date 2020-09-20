<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>test</title>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/js-cookie@rc/dist/js.cookie.min.js"></script>
    <style>
        <%@include file="css/main.css"%>
    </style>

</head>
<body>
<div id="body_div">
  <div id = "head_div" class="head">
    <span>Антонов Максим Александрович, P3214</span>
    <span>Вариант 2524</span>
  </div>
  <div id="canvas_div">
    <canvas id="canvas"  width="550px" height="550px" position="centre">
    </canvas>
  </div>

  <div id="form_div">

    <form id="testform" method="get" action="controllerServlet" >
      <label for="input_x">X</label>
      <select class="custom-class" id="input_x" name="X" >
        <option value="-3">-3</option>
        <option value="-2">-2</option>
        <option value="-1">-1</option>
        <option value="0" selected>0</option>
        <option value="1">1</option>
        <option value="2" >2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
      </select><br />

      <label for="input_y">Y</label>
      <input class="custom-class" serialize="true" id="input_y" type="text" name="Y" maxlength="15" oninput="validate()" onchange="validate()" ><br />

      <label for="first_input_r">R</label>

      <input id="first_input_r"type="checkbox" name="R" value="1" onchange="validate()">1
      <input type="checkbox" name="R" value="1.5" onchange="validate()">1.5
      <input type="checkbox" name="R" value="2" onchange="validate()">2
      <input type="checkbox" name="R" value="2.5" onchange="validate()">2.5
      <input type="checkbox" name="R" value="3" onchange="validate()">3<br />

      <input id="submit_button" type="button" disabled value="Отправить">


    </form>

  </div>
  <div id="hint_div">
    <span class="hint">Кнопка активируется, если<br/>выбраны все параметры <br/> и X∈[-3;5] и Y∈[-5;3]</span>
  </div>
  <div id="table_div" >
    <table class="result">
      <col width="50">
      <col width="50">
      <col width="50">
      <col width="75">
      <thead>
      <tr>
        <td>X</td>
        <td>Y</td>
        <td>R</td>
        <td>Result</td>
      </tr>
      </thead>
      <tbody id="table_body">


      <jsp:useBean id="results" scope="session" type="java.util.List" class="java.util.ArrayList"/>
      <c:forEach var="result"
                 items="${results}">
        <tr>
          <td> <div class="cell">${result.x} </div></td>
          <td> <div class="cell">${result.y} </div></td>
          <td> <div class="cell">${result.r} </div></td>
          <td> <div class="cell">${result.result} </div> </td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
  </div>
</div>
</body>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/canvas.js"></script>
</html>