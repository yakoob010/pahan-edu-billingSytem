<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="flex justify-between items-center mb-6">
    <h1 class="text-3xl font-bold">Dashboard</h1>
</div>

<!-- Stat Cards -->
<div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mb-6">
    <div class="bg-neutral rounded-box py-4 px-6">
        <div class="flex justify-between items-center">
            <span>Total Customers</span>
            <span class="text-purple-400 text-xl"><i class="fa-solid fa-people-group"></i></span>
        </div>
        <h2 class="text-2xl font-semibold mt-2"><%= request.getAttribute("customerCount") %>
        </h2>
    </div>
    <div class="bg-neutral rounded-box py-4 px-6">
        <div class="flex justify-between items-center">
            <span>Total Items</span>
            <span class="text-secondary text-xl"><i class="fa-solid fa-box"></i></span>
        </div>
        <h2 class="text-2xl font-semibold mt-2"><%= request.getAttribute("itemCount") %>
        </h2>
    </div>
    <div class="bg-neutral rounded-box py-4 px-6">
        <div class="flex justify-between items-center">
            <span>Total Bills</span>
            <span class="text-primary text-xl"><i class="fa-solid fa-file"></i></span>
        </div>
        <h2 class="text-2xl font-semibold mt-2"><%= request.getAttribute("orderCount") %>
        </h2>
    </div>
</div>

<div class="bg-neutral rounded-box p-4">
    <h2 class="text-lg font-semibold mb-4">Quick Actions</h2>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <a class="bg-base-200 flex flex-col justify-center items-center rounded-box py-8" href="<%= request.getContextPath() %>/customer">
            <i class="fa-solid fa-people-group text-3xl mb-2"></i>
            Add New Customer
        </a>
        <a class="bg-base-200 flex flex-col justify-center items-center rounded-box py-8" href="<%= request.getContextPath() %>/item">
            <i class="fa-solid fa-box text-3xl mb-2"></i>
            Add New Item
        </a>
        <a class="bg-base-200 flex flex-col justify-center items-center rounded-box py-8" href="<%= request.getContextPath() %>/order">
            <i class="fa-solid fa-money-bills text-3xl mb-2"></i>
            Create Bill
        </a>
    </div>
</div>