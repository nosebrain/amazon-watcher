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
   	
   	<fmt:message key="register.title" var="pageTitle" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<h2><c:out value="${pageTitle}" /></h2>
   			<c:url var="registerUrl" value="/register" />
   			<form:form commandName="user" action="${registerUrl}" method="post">
   				<div>
   					<p>
   						<form:label path="name"><fmt:message key="user.name" /></form:label>
   						<form:input path="name" />
   						<form:errors path="name" />
   					</p>
   					<p>
   						<form:label path="mail"><fmt:message key="user.mail" /></form:label>
   						<form:input path="mail" />
   						<form:errors path="mail" />
   					</p>
   				</div>
   				
   				<div>
   					<fmt:message key="user.register.action" var="actionValue" />
   					<input type="submit" value="${actionValue}" />
   				</div>
   			</form:form>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>