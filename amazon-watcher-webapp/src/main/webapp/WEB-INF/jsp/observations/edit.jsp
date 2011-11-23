<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml" 
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:enum="urn:jsptagdir:/WEB-INF/tags/utils/enum"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:aw="urn:jsptld:/WEB-INF/taglibs/amazon-watcher-taglib.tld">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<c:set var="newItem" value="${empty observation.item}" />
   	<c:choose>
		<c:when test="${newItem}">
			<c:url value="/observations" var="actionUrl"/>
			<fmt:message key="item.create.action" var="actionValue" />
			<fmt:message key="item.create.header" var="pageTitle" />
		</c:when>
		
		<c:otherwise>
			<fmt:message key="item.edit.action" var="actionValue" />
			<fmt:message key="item.edit.header" var="pageTitle" >
				<fmt:param>
					${item.name}
				</fmt:param>
			</fmt:message>   						
			<c:url value="/observations/${asin}" var="actionUrl" />
		</c:otherwise>
	</c:choose>
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			
   		
   			
   			<h2>
   				<c:out value="${pageTitle}" />
   			</h2>
   			
   			<c:set var="new_asin" value="${asin}" />
   			<c:if test="${newItem and not empty item.url}">
   				<c:set var="new_asin" value="${aw:getASIN(item.url)}" />
   			</c:if>
   			
   			<c:if test="${not empty new_asin}">
   				<img src="${aw:getImageUrl(new_asin)}" />
   			</c:if>
   			
   			<form:form method="POST" commandName="observation" action="${actionUrl}">
   				<div>
   					<p>
   						<form:label path="name"><fmt:message key="observation.name" /> *:</form:label>
   						<form:input path="name" />
   						<form:errors path="name" cssClass="errors" />
   					</p>
   					<c:if test="${not newItem}">
   						<c:set var="urlDisplay" value="display:none;" />
   					</c:if> 					
   					<p style="${urlDisplay}">
   						<form:label path="item"><fmt:message key="observation.item" /> *:</form:label>
   						<form:input path="item" />
   						<form:errors path="item" cssClass="errors" />
   					</p>
   				</div>
   				<div>
   					<p>
   						<form:label path="mode"><fmt:message key="observation.mode" /> *:</form:label>
   						<enum:select path="mode" />			
   					</p>
   					<p>
   						<form:label path="limit"><fmt:message key="observation.limit" />:</form:label>
   						<form:input path="limit" />
   						<form:errors path="limit" cssClass="errors" />
   					</p>
   				</div>
   				<div>
   					<input type="submit" value="${actionValue}" />
   				</div>
   			</form:form>
   			
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>