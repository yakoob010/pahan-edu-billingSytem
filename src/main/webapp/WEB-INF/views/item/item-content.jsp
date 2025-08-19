<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-header">
    <h1>Item Management</h1>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addItemModal">
        <i class="bi bi-plus-circle"></i> Add New Item
    </button>
</div>

<div class="card">
    <div class="card-header">
        <h5 class="mb-0">All Items</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                <tr>
                    <th>Item Code</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Category</th>
                    <th>Author</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty items}">
                    <tr>
                        <td colspan="8" class="text-center text-muted py-4">
                            <i class="bi bi-inbox" style="font-size: 2rem;"></i>
                            <br>No items found.
                        </td>
                    </tr>
                </c:if>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td>${item.itemCode}</td>
                        <td>${item.itemName}</td>
                        <td>$${item.unitPrice}</td>
                        <td>${item.stockQuantity}</td>
                        <td>${item.category}</td>
                        <td>${item.author}</td>
                        <td>
                            <div class="text-truncate" style="max-width: 200px;" title="${item.description}">
                                    ${item.description}
                            </div>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-sm btn-outline-primary edit-item-btn"
                                        data-item-code="${item.itemCode}"
                                        data-item-name="${item.itemName}"
                                        data-unit-price="${item.unitPrice}"
                                        data-stock-quantity="${item.stockQuantity}"
                                        data-category="${item.category}"
                                        data-author="${item.author}"
                                        data-description="${item.description}">
                                    <i class="bi bi-pencil"></i> Edit
                                </button>
                                <form method="post" class="d-inline"
                                      action="${pageContext.request.contextPath}/item/delete"
                                      onsubmit="return confirm('Are you sure you want to delete this item?');">
                                    <input type="hidden" name="code" value="${item.itemCode}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-trash"></i> Delete
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Edit Item Modal -->
<div class="modal fade" id="editItemModal" tabindex="-1" aria-labelledby="editItemModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editItemModalLabel">Edit Item</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="editItemForm" action="${pageContext.request.contextPath}/item/edit" method="post" class="needs-validation" novalidate>
                <input type="hidden" id="editItemCode" name="code">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="editItemName" class="form-label">Item Name</label>
                        <input type="text" class="form-control" id="editItemName" name="itemName" required>
                        <div class="invalid-feedback">
                            Please provide an item name.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editUnitPrice" class="form-label">Unit Price</label>
                        <input type="number" step="0.01" class="form-control" id="editUnitPrice" name="unitPrice" required>
                        <div class="invalid-feedback">
                            Please provide a valid price.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editStockQuantity" class="form-label">Stock Quantity</label>
                        <input type="number" class="form-control" id="editStockQuantity" name="stockQuantity" required>
                        <div class="invalid-feedback">
                            Please provide a valid quantity.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="editCategory" class="form-label">Category</label>
                        <input type="text" class="form-control" id="editCategory" name="category">
                    </div>

                    <div class="mb-3">
                        <label for="editAuthor" class="form-label">Author</label>
                        <input type="text" class="form-control" id="editAuthor" name="author">
                    </div>

                    <div class="mb-3">
                        <label for="editDescription" class="form-label">Description</label>
                        <textarea class="form-control" id="editDescription" name="description" rows="3"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <i class="bi bi-x-circle"></i> Cancel
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="add-item-modal.jsp" %>

<script>
    // Initialize Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Handle edit button click
    document.querySelectorAll('.edit-item-btn').forEach(button => {
        button.addEventListener('click', function() {
            const itemCode = this.getAttribute('data-item-code');
            const itemName = this.getAttribute('data-item-name');
            const unitPrice = this.getAttribute('data-unit-price');
            const stockQuantity = this.getAttribute('data-stock-quantity');
            const category = this.getAttribute('data-category') || '';
            const author = this.getAttribute('data-author') || '';
            const description = this.getAttribute('data-description') || '';

            // Set form values
            document.getElementById('editItemCode').value = itemCode;
            document.getElementById('editItemName').value = itemName;
            document.getElementById('editUnitPrice').value = unitPrice;
            document.getElementById('editStockQuantity').value = stockQuantity;
            document.getElementById('editCategory').value = category;
            document.getElementById('editAuthor').value = author;
            document.getElementById('editDescription').value = description;

            // Show the modal
            const modal = new bootstrap.Modal(document.getElementById('editItemModal'));
            modal.show();
        });
    });

    // Form validation and AJAX submission
    document.getElementById('editItemForm')?.addEventListener('submit', function(e) {
        e.preventDefault();

        if (!this.checkValidity()) {
            e.stopPropagation();
            this.classList.add('was-validated');
            return false;
        }

        // Get form data
        const formData = new FormData(this);
        const formObject = {};
        formData.forEach((value, key) => {
            formObject[key] = value;
        });

        // Send AJAX request
        fetch('${pageContext.request.contextPath}/item/edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(formData).toString()
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while updating the item.');
            });
    });

    // Initialize form validation
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
