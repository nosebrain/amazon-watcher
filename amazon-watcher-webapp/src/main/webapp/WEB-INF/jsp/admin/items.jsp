<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:aw="urn:jsptld:/WEB-INF/taglibs/amazon-watcher-taglib.tld">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message var="pageTitle" key="administration.items.header" />
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<div class="page-header">
   				<h1><c:out value="${pageTitle}" /></h1>
			</div>
			<div class="row">
				<div class="span8">
					<table class="table table-striped table-bordered">
						<tr>
							<th><!-- keep me --></th>
							<th>Last Updated</th>
							<th>Price</th>
							<th>Last Error</th>
							<th>Subscriptions</th>
							<th>→</th>
						</tr>		
	   				<c:forEach var="item" items="${items}">
	   					<tr>
	   						<td><img src="${aw:getImageUrl(item)}" /></td>
	   						<td>--</td>
	   						<td>--</td>
	   						<td>--</td>
	   						<td>--</td>
	   						<td><a href="${aw:getUrl(item) }">→</a></td>
	   					</tr>
	   				</c:forEach>
	   				</table>
	   			</div>
   			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>