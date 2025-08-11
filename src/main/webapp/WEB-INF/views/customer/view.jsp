<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, dev.yakoob.pahanaedu.business.customer.dto.CustomerDTO" %>

<div class="flex justify-between items-center mb-6">
    <h1 class="text-3xl font-extrabold bg-gradient-to-r from-purple-500 to-pink-500 text-transparent bg-clip-text">
        Customer Management
    </h1>
    <label for="add-customer-modal"
           class="btn btn-primary mb-4 shadow-lg hover:scale-105 transform transition duration-300">
        + Add Customer
    </label>
</div>

<div class="card bg-base-200 shadow-xl rounded-xl overflow-hidden">
    <div class="card-body">
        <div class="flex justify-between items-center mb-4">
            <h2 class="card-title text-lg font-bold text-primary">📋 Customers</h2>
            <input type="text"
                   placeholder="🔍 Search customers..."
                   class="input input-bordered w-64 focus:outline-none focus:ring-2 focus:ring-primary transition duration-300"/>
        </div>

        <div class="overflow-x-auto">
            <table class="table table-zebra table-md">
                <thead class="bg-gradient-to-r from-primary to-secondary text-white">
                <tr>
                    <th class="py-3">Name</th>
                    <th>Mobile</th>
                    <th>Units Consumed</th>
                    <th>Email</th>
                    <th>Registered</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customerList");
                    if (customers == null || customers.isEmpty()) {
                %>
                <tr class="hover:bg-base-300 transition duration-300">
                    <td colspan="9" class="text-center text-gray-400 italic py-6">No customers found</td>
                </tr>
                <%
                } else {
                    for (CustomerDTO customer : customers) {
                %>
                <tr class="hover:bg-primary/10 transition duration-300">
                    <td class="font-semibold"><%= customer.getName() %></td>
                    <td><span class="badge badge-outline badge-primary"><%= customer.getMobileNumber() %></span></td>
                    <td class="text-center font-bold text-secondary"><%= customer.getUnitsConsumed() %></td>
                    <td class="truncate max-w-xs"><%= customer.getEmail() %></td>
                    <td><%= customer.getRegistrationDate() %></td>
                    <td>
                        <div class="flex space-x-2 justify-center">
                            <a href="<%= request.getContextPath() %>/customer/edit?id=<%= customer.getCustomerId() %>"
                               class="btn btn-sm btn-primary btn-outline hover:scale-105 transition duration-300">
                                Edit
                            </a>
                            <form method="post"
                                  action="<%= request.getContextPath() %>/customer/delete?id=<%= customer.getCustomerId() %>"
                                  onsubmit="return confirm('Are you sure you want to delete this customer?');">
                                <button type="submit"
                                        class="btn btn-sm btn-secondary btn-outline hover:scale-105 transition duration-300">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../customer/add-customer-modal.jsp" %>
