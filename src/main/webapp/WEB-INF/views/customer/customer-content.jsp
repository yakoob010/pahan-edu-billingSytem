<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-header">
    <h1>Customer Management</h1>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCustomerModal">
        <i class="bi bi-plus-circle"></i> Add New Customer
    </button>
</div>

<div class="card">
    <div class="card-header">
        <h5 class="mb-0">All Customers</h5>
    </div>
    <div class="card-body">
        <c:if test="${not empty sessionScope.flash_success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.flash_success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="flash_success" scope="session" />
        </c:if>

        <c:if test="${not empty sessionScope.flash_error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.flash_error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="flash_error" scope="session" />
        </c:if>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                <tr>
                    <th>Customer ID</th>
                    <th>Name</th>
                    <th>Mobile</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Registration Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty customers}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">
                                <i class="bi bi-inbox" style="font-size: 2rem;"></i>
                                <br>No customers found.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="customer" items="${customers}">
                            <tr>
                                <td>${customer.customerId}</td>
                                <td>${customer.name}</td>
                                <td>${customer.mobileNumber}</td>
                                <td>${customer.email}</td>
                                <td>${customer.address}</td>
                                <td>${customer.registrationDate}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <button type="button" class="btn btn-sm btn-outline-primary edit-customer-btn"
                                                data-bs-toggle="modal" data-bs-target="#editCustomerModal"
                                                data-id="${customer.customerId}"
                                                data-name="${customer.name}"
                                                data-email="${customer.email}"
                                                data-address="${customer.address}"
                                                data-mobile="${customer.mobileNumber}">
                                            <i class="bi bi-pencil"></i> Edit
                                        </button>
                                        <form method="post" class="d-inline"
                                              action="${pageContext.request.contextPath}/customer/delete"
                                              onsubmit="return confirm('Are you sure you want to delete this customer?');">
                                            <input type="hidden" name="id" value="${customer.customerId}">
                                            <button type="submit" class="btn btn-sm btn-outline-danger">
                                                <i class="bi bi-trash"></i> Delete
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Edit Customer Modal -->
<div class="modal fade" id="editCustomerModal" tabindex="-1" aria-labelledby="editCustomerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editCustomerModalLabel">Edit Customer</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="${pageContext.request.contextPath}/customer/edit" method="post" class="needs-validation" novalidate>
                <div class="modal-body">
                    <input type="hidden" name="id" id="editCustomerId">

                    <div class="mb-3">
                        <label for="editName" class="form-label">Name</label>
                        <input type="text" class="form-control" id="editName" name="name" required>
                        <div class="invalid-feedback">
                            Please provide a name.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="editEmail" name="email" required>
                        <div class="invalid-feedback">
                            Please provide a valid email.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editAddress" class="form-label">Address</label>
                        <textarea class="form-control" id="editAddress" name="address" rows="2" required></textarea>
                        <div class="invalid-feedback">
                            Please provide an address.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editMobile" class="form-label">Mobile Number</label>
                        <input type="tel" class="form-control" id="editMobile" name="mobileNumber" required>
                        <div class="invalid-feedback">
                            Please provide a mobile number.
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Handle edit button click
    document.querySelectorAll('.edit-customer-btn').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name');
            const email = this.getAttribute('data-email');
            const address = this.getAttribute('data-address');
            const mobile = this.getAttribute('data-mobile');

            // Set form values
            document.getElementById('editCustomerId').value = id;
            document.getElementById('editName').value = name || '';
            document.getElementById('editEmail').value = email || '';
            document.getElementById('editAddress').value = address || '';
            document.getElementById('editMobile').value = mobile || '';
        });
    });

    // Form validation
    (function () {
        'use strict';
        var forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();
</script>

<%@ include file="add-customer-modal.jsp" %>
