<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:sec="http://www.springframework.org/security/tags">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout>
   		<jsp:attribute name="content">
   			
   			<sec:authorize access="isAuthenticated()">
   				<c:url value="/observations/edit" var="newItemUrl" />
   				<h2><fmt:message key="home.header" />(<a href="${newItemUrl}">+</a>)</h2>
   			
	   			<c:choose>
					<c:when test="${not empty lastUpdateDate}">
						<fmt:formatDate value="${lastUpdateDate}" pattern="dd.MM.yyyy HH:mm" var="formatedDate" />
						<fmt:message key="updater.lastupdated">
							<fmt:param value="${formatedDate}" />
						</fmt:message>
					</c:when>
	 				<c:otherwise><fmt:message key="updater.notupdated" /></c:otherwise>
	 				<!-- TODO: next update -->
				</c:choose>
	   			<div id="items">
	   				<ul class="sortMenu">
	   					<li>Name (<a href="?sortBy=name">asc</a> | <a href="?sortBy=name">desc</a>)</li>
	   					<li>Last update (<a href="?sortBy=lastupdate">asc</a> | <a href="?sortBy=lastupdate">desc</a>)</li>
	   				</ul>
		   			<c:forEach var="watchedItem" items="${observations}">
		   				<observations:details observation="${watchedItem}" />
		   			</c:forEach>
		   			
	   			</div>
   			</sec:authorize>
   			
   			<sec:authorize access="isAnonymous()">
   				<h2><fmt:message key="home.overview" /></h2>
   				
   				<div id="overview">
   					<fmt:message key="system.about">
   						<fmt:param value="${properties['general.name']}"/>
   					</fmt:message>
   				</div>
   			</sec:authorize>
   			
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>