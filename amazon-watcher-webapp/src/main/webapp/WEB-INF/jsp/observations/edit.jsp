<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml" 
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:bootstrap="urn:jsptagdir:/WEB-INF/tags/utils/bootstrap"
        	xmlns:enum="urn:jsptagdir:/WEB-INF/tags/utils/enum"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:aw="urn:jsptld:/WEB-INF/taglibs/amazon-watcher-taglib.tld">
	<!-- TODO: global errors -->
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<c:set var="item" value="${observation.item}" />
   	<c:set var="newItem" value="${empty item}" />
   	<c:choose>
		<c:when test="${newItem}">
			<c:url value="/observations" var="actionUrl"/>
			<fmt:message key="observation.create.action" var="actionValue" />
			<fmt:message key="observation.create.header" var="pageTitle" />
		</c:when>
		
		<c:otherwise>
			<fmt:message key="observation.edit.action" var="actionValue" />
			<c:set var="pageTitle" value="${observation.name}" />  						
			<c:url value="/observations/${item.site}_${item.asin}" var="actionUrl" />
		</c:otherwise>
	</c:choose>
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">   			
   			<c:set var="new_asin" value="${asin}" />
   			<c:if test="${newItem and not empty item.url}">
   				<c:set var="new_asin" value="${aw:getASIN(item.url)}" />
   			</c:if>
   			
   			<c:if test="${not empty new_asin}">
   				<img src="${aw:getImageUrl(new_asin)}" />
   			</c:if>
   			<div class="page-header">
   				<h1>
   			<c:choose>
   				<c:when test="${newItem}">
   					<fmt:message key="observation.create.header" />
   				</c:when>
   				<c:otherwise>
   					<fmt:message key="observation.edit.header">
   						<fmt:param value="${observation.name}" />
   					</fmt:message>
   				</c:otherwise>
   			</c:choose>
   				</h1>
   			</div>
   			
   			<div class="row">
   			
   			<div id="img" class="span2">
   				<!-- fill me -->
   				<c:if test="${not newItem}">
   					<a href="${aw:getUrl(item)}"><img src="${aw:getImageUrl(item)}" /></a>
   				</c:if>
   			</div>
   			
   			<div class="span6">
   			<form:form method="POST" commandName="observation" action="${actionUrl}" cssClass="form-horizontal">
   				<c:if test="${not newItem}">
   					<input type="hidden" name="_method" value="PUT" />
   				</c:if>
   				
   				<bootstrap:input labelMessageKey="observation.name" path="name" required="${true}" />
   				
   				<c:if test="${not newItem}">
   					<c:set var="itemCssClass" value="hidden" />
   				</c:if>
   				
   				<bootstrap:input labelMessageKey="observation.item" path="item" required="${true}" extraCssClass="${itemCssClass}" />
   				
   				<bootstrap:enumSelect labelMessageKey="observation.mode" path="mode" required="${true}"/>   				
   				
				<bootstrap:input path="limit" labelMessageKey="observation.limit" required="${true}" />
				
   				<div class="form-actions">
   					<!-- TODO: referer -->
   					<c:url var="homeurl" value="/" />
   					<button href="${homeurl}" class="btn"><fmt:message key="cancel" /></button>
   					
   					<input type="submit" value="${actionValue}" class="btn btn-primary" />
   				</div>
   			</form:form>
   			</div>
   			
   				<div class="span4">
   					<div class="alert alert-warning">
   						<fmt:message key="tips.bookmarklet" />
   					</div>
   				</div>
   			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>