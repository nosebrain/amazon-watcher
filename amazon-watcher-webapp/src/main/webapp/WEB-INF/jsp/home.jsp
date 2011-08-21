<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml" 
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout pageTitle="Home">
   		<jsp:attribute name="content">
   			<h2>watched items</h2>
   			<c:url value="/items/edit" var="newItemUrl" />
   			<a href="${newItemUrl}">add</a>
   			<ul>
   			<c:forEach var="watchedItem" items="${items}">
   				<li><a href="${watchedItem.url}"><c:out value="${watchedItem.name}" /></a> (<c:out value="${watchedItem.lastPrice}" />, <c:out value="${watchedItem.changeDate}" />)</li>
   			</c:forEach>
   			</ul>	
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>