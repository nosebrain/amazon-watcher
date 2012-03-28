<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:items="urn:jsptagdir:/WEB-INF/tags/item"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="system.errors.${status}.title" var="pageTitle" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			
   			<div class="hero-unit">
   				<h1><c:out value="${pageTitle}" /></h1>
   				<p><fmt:message key="system.errors.${status}.description"></fmt:message></p>
   			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>