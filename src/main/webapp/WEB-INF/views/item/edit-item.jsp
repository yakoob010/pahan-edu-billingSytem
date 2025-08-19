<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0">Edit Item</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/item/edit" method="post" class="needs-validation" novalidate>
                        <input type="hidden" name="code" value="${item.itemCode}">

                        <div class="mb-3">
                            <label for="itemName" class="form-label">Item Name</label>
                            <input type="text" class="form-control" id="itemName" name="itemName"
                                   value="${item.itemName}" required>
                            <div class="invalid-feedback">
                                Please provide an item name.
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="unitPrice" class="form-label">Unit Price</label>
                            <input type="number" step="0.01" class="form-control" id="unitPrice" name="unitPrice"
                                   value="${item.unitPrice}" required>
                            <div class="invalid-feedback">
                                Please provide a valid price.
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="stockQuantity" class="form-label">Stock Quantity</label>
                            <input type="number" class="form-control" id="stockQuantity" name="stockQuantity"
                                   value="${item.stockQuantity}" required>
                            <div class="invalid-feedback">
                                Please provide a valid quantity.
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="category" class="form-label">Category</label>
                            <input type="text" class="form-control" id="category" name="category"
                                   value="${item.category}">
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description"
                                      rows="3">${item.description}</textarea>
                        </div>

                        <div class="mb-3">
                            <label for="publisher" class="form-label">Publisher</label>
                            <input type="text" class="form-control" id="publisher" name="publisher"
                                   value="${item.publisher}" required>
                            <div class="invalid-feedback">
                                Please provide a publisher.
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="author" class="form-label">Author</label>
                            <input type="text" class="form-control" id="author" name="author"
                                   value="${item.author}" required>
                            <div class="invalid-feedback">
                                Please provide an author.
                            </div>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/item" class="btn btn-secondary me-md-2">
                                <i class="bi bi-x-circle"></i> Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-save"></i> Save Changes
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Form validation
    (function () {
        'use strict'

        var forms = document.querySelectorAll('.needs-validation')

        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
