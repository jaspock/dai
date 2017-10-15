<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Nuevo producto</title>
  </head>
<body>
    <form method="POST" action='ProductoController' name="frmAddProduct">
        Identificador : <input type="text" readonly="readonly" name="productoid"
            value="<c:out value="${producto.productoid}" />" /> <br /> 
        Nombre : <input
            type="text" name="name"
            value="<c:out value="${producto.name}" />" /> <br /> 
        Cantidad : <input
            type="text" name="quantity"
            value="<c:out value="${producto.quantity}" />" /> <br /> 
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
