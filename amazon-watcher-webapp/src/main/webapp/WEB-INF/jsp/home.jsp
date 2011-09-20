<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml" 
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout pageTitle="Home">
   		<jsp:attribute name="content">   			
   			<h2>watched items</h2>
   			
   			<c:url value="/updateItems" var="manUpdateUrl" />
   			<c:url value="/items/edit" var="newItemUrl" />
   			<ul>
   				<li>
   					<c:choose>
   						<c:when test="${not empty lastUpdateDate}">
   							last updated on <fmt:formatDate value="${lastUpdateDate}" pattern="dd.MM.yyyy HH:mm" />
   					</c:when>
   					<c:otherwise>
   						not updated
   					</c:otherwise>
   					</c:choose>
   					<c:out value=" "/><a href="${manUpdateUrl}">update now</a>
   				</li>
   				<li><a href="${newItemUrl}">add new item</a></li>
   			</ul>
   			
   			<ul>
   			<c:forEach var="watchedItem" items="${items}">
   				<c:url value="/items/edit/${watchedItem.asin}" var="editUrl" />
   				<c:url value="/items/${watchedItem.asin}" var="itemUrl" />
   				<li><a href="${watchedItem.url}"><c:out value="${watchedItem.name}" /></a>  					
   					<ul class="history">
   						<c:forEach var="history" items="${watchedItem.priceHistories}">
   							<c:set var="price" value="${history.value}" />
   							<c:set var="date" value="${history.date}" />
   							<li data-date="${date}" data-price="${price}"><fmt:formatNumber value="${price}" maxFractionDigits="2" minFractionDigits="2" /><c:out value=" ${amazon.currency}" /> (<fmt:formatDate  value="${date}" pattern="dd.MM.yyyy HH:mm" />)</li>
   						</c:forEach>
   					</ul>
   					<ul class="watchedActions">
   						<li><a href="">show history</a></li>
   						<li><a href="${editUrl}">edit item</a></li>
   						<li>
   							<form action="${itemUrl}" method="POST">
   								<input type="submit" value="delete" />
   							</form>
   						</li>
   					</ul>
   				</li>
   			</c:forEach>
   			</ul>	
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>