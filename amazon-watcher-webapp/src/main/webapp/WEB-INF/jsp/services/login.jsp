<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="login.title" var="pageTitle" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<h2><c:out value="${pageTitle}" /></h2>
   			
   			<ul id="loginServices">
   				<li><a href="#normal" class="login selected">Amazon Watcher</a></li>
   				
   				<c:forEach var="service" items="${authServices}">
   					<c:set var="serviceId" value="${fn:toLowerCase(service.name)}" />
   					<c:url var="iconUrl" value="/icons/authorities/${serviceId}.png" />
   					<li><a href="#" data-serviceId="${serviceId}" data-url="${service.url}" title="${service.name}"><img src="${iconUrl}" /></a></li>
   				</c:forEach>
   			</ul>
   			
   			<ul id="loginExtras">
   				<li id="normal">
   					<div>
   					<c:url var="loginUrl" value="/login_internal" />
   					<form action="${loginUrl}" method="post">
   						<p><label>username *:</label><input type="text" name="name"/><br />
   						<label>password *:</label><input type="password" name="secret" /></p>
   						<p><input type="submit" value="login" /></p>
   					</form>
   					</div>
				</li>
   			</ul>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>