<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO" %>
<%@ page import="dev.yakoob.pahanaedu.business.item.dto.ItemDTO" %>

<%
    List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
    List<ItemDTO> items = (List<ItemDTO>) request.getAttribute("items");
%>

<div class="flex justify-between items-center mb-6">
    <h1 class="text-3xl font-bold">Place Order</h1>
    <div></div>
</div>

<form id="orderForm" action="<%= request.getContextPath() %>/order" method="post"
      class="grid grid-cols-1 lg:grid-cols-3 gap-6">

    <!-- Create New Bill Form -->
    <div class="col-span-2 bg-base-100 p-6 rounded-box shadow-lg">
        <h2 class="text-xl font-bold mb-4 flex items-center gap-2">Create New Bill</h2>

        <!-- Select Customer -->
        <div class="form-control w-full mb-4">
            <label class="label font-semibold">Select Customer *</label>
            <select name="customerId" id="customerId" class="select select-bordered w-full" required>
                <option disabled selected>Choose a customer</option>
                <% for (CustomerDTO c : customers) { %>
                    <option value="<%= c.getCustomerId() %>"><%= c.getName() %></option>
                <% } %>
            </select>
        </div>

        <!-- Add Items -->
        <h3 class="font-semibold mt-6 mb-2">Add Items</h3>
        <div class="flex gap-4 items-end">
            <div class="flex-1">
                <label class="label font-semibold">Select Item</label>
                <select id="itemSelect" class="select select-bordered w-full">
                    <option disabled selected value="">Choose an item</option>
                    <% for (ItemDTO i : items) { %>
                        <option value="<%= i.getItemCode() %>" data-name="<%= i.getItemName() %>" data-price="<%= i.getUnitPrice() %>">
                            <%= i.getItemName() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="form-control">
                <label class="label font-semibold">Quantity</label>
                <input type="number" id="itemQuantity" value="1" min="1" class="input input-bordered w-24" />
            </div>

            <button type="button" class="btn btn-secondary btn-outline" onclick="addToBill()">
                <i class="fa-solid fa-plus"></i>
            </button>
        </div>

        <!-- Bill Items Table -->
        <div class="mt-8">
            <h3 class="font-semibold mb-2">Bill Items</h3>
            <div class="overflow-x-auto">
                <table class="table table-zebra w-full text-sm">
                    <thead>
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
                            <td colspan="5" class="text-center">No items added to bill</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Bill Summary + Recent Bills -->
    <div class="flex flex-col gap-6">
        <!-- Summary -->
        <div class="bg-base-100 p-6 rounded-box shadow-lg">
            <h3 class="text-lg font-semibold mb-4">Bill Summary</h3>
            <div class="space-y-2 text-sm">
                <div class="flex justify-between">
                    <span>Subtotal:</span>
                    <span>Rs. <span id="subtotal">0.00</span></span>
                </div>
                <div class="flex justify-between font-bold text-lg mt-4">
                    <span>Total:</span>
                    <span>Rs. <span id="total">0.00</span></span>
                </div>
            </div>
            <button type="button" class="btn btn-primary mt-6 w-full" onclick="submitOrder()">
                <i class="fa-solid fa-cart-shopping"></i> Create Bill
            </button>
        </div>

        <!-- Recent Bills -->
        <div class="bg-base-100 p-6 rounded-box shadow-lg">
            <h3 class="text-lg font-semibold mb-2">Recent Bills</h3>
            <p class="text-sm text-gray-400">No bills created yet</p>
        </div>
    </div>

    <!-- Hidden fields for bill items and total -->
    <div id="hiddenInputsContainer"></div>
    <input type="hidden" name="total" id="totalHidden">

</form>

<script>
    const billItems = [];

    function addToBill() {
        const itemSelect = document.getElementById("itemSelect");
        const qtyInput = document.getElementById("itemQuantity");

        const itemCode = itemSelect.value;
        const itemName = itemSelect.selectedOptions[0]?.dataset.name;
        const unitPrice = parseFloat(itemSelect.selectedOptions[0]?.dataset.price);
        const quantity = parseInt(qtyInput.value);

        if (!itemCode || isNaN(quantity) || quantity <= 0) {
            Swal.fire({
                icon: "warning",
                text: "Please select an item and enter a valid quantity.",
                theme: 'dark'
            });
            return;
        }

        const existing = billItems.find(item => item.code === itemCode);
        if (existing) {
            existing.quantity += quantity;
        } else {
            billItems.push({ code: itemCode, name: itemName, price: unitPrice, quantity });
        }

        renderBillTable();
        calculateTotal();
    }

    function removeItem(code) {
        const index = billItems.findIndex(item => item.code === code);
        if (index !== -1) {
            billItems.splice(index, 1);
        }
        renderBillTable();
        calculateTotal();
    }

    function renderBillTable() {
        const tbody = document.getElementById("bill-items");
        tbody.innerHTML = "";

        if (billItems.length === 0) {
            tbody.innerHTML = '<tr id="no-items-row"><td colspan="5" class="text-center">No items added to bill</td></tr>';
            return;
        }

        billItems.forEach(item => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${item.name}</td>
                <td>${item.quantity}</td>
                <td>Rs. ${item.price.toFixed(2)}</td>
                <td>Rs. ${(item.price * item.quantity).toFixed(2)}</td>
                <td><button class="btn btn-sm text-error" onclick="removeItem('${item.code}')">
                    <i class="fa-solid fa-trash"></i>
                </button></td>
            `;
            tbody.appendChild(row);
        });
    }

    function calculateTotal() {
        let subtotal = 0;
        billItems.forEach(item => {
            subtotal += item.price * item.quantity;
        });

        const total = subtotal;

        document.getElementById("subtotal").innerText = subtotal.toFixed(2);
        document.getElementById("total").innerText = total.toFixed(2);
    }

    function submitOrder() {
        const customerId = document.getElementById("customerId").value;

        console.log(customerId)

        if (!customerId || customerId === "Choose a customer") {
            Swal.fire({
                icon: "error",
                text: "Please select a customer.",
                theme: 'dark'
            });
            return;
        }

        if (billItems.length === 0) {
            Swal.fire({
                icon: "error",
                text: "Please add at least one item to the bill.",
                theme: 'dark'
            });
            return;
        }

        // Clear previous hidden inputs
        const container = document.getElementById("hiddenInputsContainer");
        container.innerHTML = "";

        // Add hidden inputs for each bill item
        billItems.forEach((item, index) => {
            container.innerHTML += `
                <input type="hidden" name="items[${index}].code" value="${item.code}">
                <input type="hidden" name="items[${index}].quantity" value="${item.quantity}">
                <input type="hidden" name="items[${index}].price" value="${item.price}">
            `;
        });

        // Set total
        document.getElementById("totalHidden").value = document.getElementById("total").innerText;

        // Submit the form
        document.getElementById("orderForm").submit();
    }
</script>
