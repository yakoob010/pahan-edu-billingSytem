<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page-header d-flex justify-content-between align-items-center">
    <h1>Create Invoice</h1>
</div>

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

<form id="orderForm" action="${pageContext.request.contextPath}/order" method="post">
    <div class="row">
        <!-- Create New Bill Form -->
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Create New Bill</h5>
                </div>
                <div class="card-body">
                    <!-- Select Customer -->
                    <div class="mb-4">
                        <label for="customerId" class="form-label fw-semibold">Select Customer *</label>
                        <select name="customerId" id="customerId" class="form-select" required>
                            <option disabled selected>Choose a customer</option>
                            <c:forEach var="c" items="${customers}">
                                <option value="${c.customerId}">${c.email}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Add Items -->
                    <h6 class="fw-semibold mt-4 mb-3">Add Items</h6>
                    <div class="row g-3 align-items-end">
                        <div class="col-md-6">
                            <label for="itemSelect" class="form-label fw-semibold">Select Item</label>
                            <select id="itemSelect" class="form-select">
                                <option disabled selected value="">Choose an item</option>
                                <c:forEach var="i" items="${items}">
                                    <option value="${i.itemCode}"
                                            data-name="${i.itemName}"
                                            data-price="${i.unitPrice}">
                                            ${i.itemName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-3">
                            <label for="itemQuantity" class="form-label fw-semibold">Quantity</label>
                            <input type="number" id="itemQuantity" value="1" min="1" class="form-control" />
                        </div>

                        <div class="col-md-3">
                            <button type="button" class="btn btn-outline-secondary w-100" onclick="addToBill()">
                                <i class="bi bi-plus"></i> Add
                            </button>
                        </div>
                    </div>

                    <!-- Bill Items Table -->
                    <div class="mt-4">
                        <h6 class="fw-semibold mb-3">Bill Items</h6>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead class="table-dark">
                                <tr>
                                    <th>Item</th>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Total</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody id="bill-items">
                                <tr id="no-items-row">
                                    <td colspan="5" class="text-center text-muted">No items added to bill</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bill Summary -->
        <div class="col-lg-4">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Bill Summary</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-2">
                        <span>Subtotal:</span>
                        <span>$<span id="subtotal">0.00</span></span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between fw-bold fs-5">
                        <span>Total:</span>
                        <span>$<span id="total">0.00</span></span>
                    </div>
                    <button type="button" class="btn btn-primary w-100 mt-4" onclick="event.preventDefault(); submitOrder(); return false;">
                        <i class="bi bi-cart-check"></i> Create Bill
                    </button>
                </div>
            </div>

            <!-- Recent Orders -->
            <div class="card mt-4">
                <div class="card-header">
                    <h6 class="mb-0">Recent Orders</h6>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty recentOrders}">
                            <div class="list-group list-group-flush">
                                <c:forEach var="order" items="${recentOrders}" varStatus="status">
                                    <c:if test="${status.index < 5}">
                                        <div class="list-group-item px-0 py-2">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div>
                                                    <small class="text-muted">Order #${order.orderId}</small><br>
                                                    <small class="text-muted">Customer: ${order.customerId}</small>
                                                </div>
                                                <div class="text-end">
                                                    <strong>$${order.totalAmount}</strong><br>
                                                    <small class="text-muted">${order.date}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted mb-0">No recent orders</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Hidden fields for bill items and total -->
    <div id="hiddenInputsContainer"></div>
    <input type="hidden" name="total" id="totalHidden">
</form>

<script>
    // Basic JavaScript test - this should appear in console immediately when page loads
    console.log("=== JavaScript is loading ===");
    console.log("Page loaded, script is running");

    const billItems = [];
    console.log("billItems array initialized:", billItems);

    function addToBill() {
        console.log("=== addToBill() called ===");

        const itemSelect = document.getElementById("itemSelect");
        const qtyInput = document.getElementById("itemQuantity");

        console.log("itemSelect element:", itemSelect);
        console.log("qtyInput element:", qtyInput);

        const itemCode = itemSelect.value;
        const itemName = itemSelect.selectedOptions[0]?.dataset.name;
        const unitPrice = parseFloat(itemSelect.selectedOptions[0]?.dataset.price);
        const quantity = parseInt(qtyInput.value);

        console.log("Form values:");
        console.log("- itemCode:", itemCode);
        console.log("- itemName:", itemName);
        console.log("- unitPrice:", unitPrice);
        console.log("- quantity:", quantity);

        if (!itemCode || isNaN(quantity) || quantity <= 0) {
            console.log("ERROR: Validation failed!");
            alert("Please select an item and enter a valid quantity.");
            return;
        }

        console.log("Before adding - billItems.length:", billItems.length);
        console.log("Before adding - billItems:", billItems);

        const existing = billItems.find(item => item.code === itemCode);
        if (existing) {
            console.log("Found existing item, updating quantity from", existing.quantity, "to", existing.quantity + quantity);
            existing.quantity += quantity;
        } else {
            const newItem = { code: itemCode, name: itemName, price: unitPrice, quantity };
            console.log("Adding new item:", newItem);
            billItems.push(newItem);
        }

        console.log("After adding - billItems.length:", billItems.length);
        console.log("After adding - billItems:", billItems);

        renderBillTable();
        calculateTotal();

        // Reset form
        itemSelect.value = "";
        qtyInput.value = 1;

        console.log("=== addToBill() completed ===");
    }

    function removeItem(code) {
        const index = billItems.findIndex(item => item.code === code);
        if (index !== -1) billItems.splice(index, 1);
        renderBillTable();
        calculateTotal();
    }

    function renderBillTable() {
        const tbody = document.getElementById("bill-items");
        tbody.innerHTML = "";

        if (billItems.length === 0) {
            tbody.innerHTML = '<tr id="no-items-row"><td colspan="5" class="text-center text-muted">No items added to bill</td></tr>';
            return;
        }

        billItems.forEach(item => {
            const row = document.createElement("tr");
            row.innerHTML =
                "<td>" + item.name + "</td>" +
                "<td>" + item.quantity + "</td>" +
                "<td>$" + item.price.toFixed(2) + "</td>" +
                "<td>$" + (item.price * item.quantity).toFixed(2) + "</td>" +
                "<td>" +
                "<button type=\"button\" class=\"btn btn-sm btn-outline-danger\" onclick=\"removeItem('" + item.code + "')\">" +
                "<i class=\"bi bi-trash\"></i>" +
                "</button>" +
                "</td>";
            tbody.appendChild(row);
        });
    }

    function calculateTotal() {
        let subtotal = 0;
        billItems.forEach(item => { subtotal += item.price * item.quantity; });
        const total = subtotal;
        document.getElementById("subtotal").innerText = subtotal.toFixed(2);
        document.getElementById("total").innerText = total.toFixed(2);
    }

    function submitOrder() {
        try {
            console.log("=== submitOrder() called ===");
            console.log("billItems array:", billItems);
            console.log("billItems.length:", billItems.length);

            const customerId = document.getElementById("customerId").value;
            console.log("customerId:", customerId);

            if (!customerId || customerId === "Choose a customer") {
                alert("Please select a customer.");
                return;
            }
            if (billItems.length === 0) {
                console.log("ERROR: billItems array is empty!");
                alert("Please add at least one item to the bill.");
                return;
            }

            const container = document.getElementById("hiddenInputsContainer");
            console.log("hiddenInputsContainer found:", container);
            container.innerHTML = "";

            console.log("Creating hidden fields for", billItems.length, "items:");
            billItems.forEach((item, index) => {
                console.log("Item " + index + ":", item);
                const hiddenHTML =
                    '<input type="hidden" name="items[' + index + '].code" value="' + item.code + '">' +
                    '<input type="hidden" name="items[' + index + '].quantity" value="' + item.quantity + '">' +
                    '<input type="hidden" name="items[' + index + '].price" value="' + item.price + '">';
                console.log("Hidden HTML for item " + index + ":", hiddenHTML);
                container.innerHTML += hiddenHTML;
            });

            const totalValue = document.getElementById("total").innerText;
            console.log("Total value:", totalValue);
            document.getElementById("totalHidden").value = totalValue;

            console.log("Final container HTML:", container.innerHTML);
            console.log("=== About to submit form ===");

            // Prevent default form submission and submit manually
            const form = document.getElementById("orderForm");
            console.log("Form element:", form);
            form.submit();

        } catch (error) {
            console.error("ERROR in submitOrder():", error);
            alert("JavaScript error: " + error.message);
        }
    }
</script>
