<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="fragment/header.jsp" %>
<div class="container table-container">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/contact-form">Add</a>
    <table>
        <tr>
            <th>ID</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Address</th>
            <th>Contact</th>
            <th>Action</th>
        </tr>

        <c:forEach var="contact" items="${contacts}">
            <tr>
                <td><c:out value="${contact.id}"/></td>
                <td><c:out value="${contact.firstName}"/></td>
                <td><c:out value="${contact.lastName}"/></td>
                <td><c:out value="${contact.address}"/></td>
                <td><c:out value="${contact.phoneNumber}"/></td>
                <td>
                    <a class="btn btn-edit"
                       href="${pageContext.request.contextPath}/edit/<c:out value="${contact.id}"/>">Edit</a>
                    <a class="btn btn-danger"
                       href="${pageContext.request.contextPath}/delete/<c:out value="${contact.id}"/>">Delete</a>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
<%@include file="fragment/footer.jsp" %>
