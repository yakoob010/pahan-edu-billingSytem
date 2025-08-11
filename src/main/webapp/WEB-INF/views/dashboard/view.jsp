<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="flex flex-col lg:flex-row gap-8">

    <!-- Left Side: Stats -->
    <div class="flex-1 space-y-6">
        <h1 class="text-4xl font-bold text-primary mb-4">🚀 Dashboard Overview</h1>

        <div class="grid grid-cols-1 sm:grid-cols-3 gap-6">
            <!-- Customers -->
            <div class="rounded-xl p-6 shadow-lg hover:scale-105 transform transition duration-300"
                 style="background: linear-gradient(135deg, #8B5CF6, #EC4899); color: white;">
                <div class="flex items-center gap-3">
                    <div class="bg-white/20 p-3 rounded-full text-2xl">
                        <i class="fa-solid fa-people-group"></i>
                    </div>
                    <span class="text-lg font-semibold">Total Customers</span>
                </div>
                <h2 class="text-4xl font-bold mt-4"><%= request.getAttribute("customerCount") %></h2>
            </div>

            <!-- Items -->
            <div class="rounded-xl p-6 shadow-lg hover:scale-105 transform transition duration-300"
                 style="background: linear-gradient(135deg, #06B6D4, #3B82F6); color: white;">
                <div class="flex items-center gap-3">
                    <div class="bg-white/20 p-3 rounded-full text-2xl">
                        <i class="fa-solid fa-box"></i>
                    </div>
                    <span class="text-lg font-semibold">Total Items</span>
                </div>
                <h2 class="text-4xl font-bold mt-4"><%= request.getAttribute("itemCount") %></h2>
            </div>

            <!-- Bills -->
            <div class="rounded-xl p-6 shadow-lg hover:scale-105 transform transition duration-300"
                 style="background: linear-gradient(135deg, #F59E0B, #EF4444); color: white;">
                <div class="flex items-center gap-3">
                    <div class="bg-white/20 p-3 rounded-full text-2xl">
                        <i class="fa-solid fa-file"></i>
                    </div>
                    <span class="text-lg font-semibold">Total Bills</span>
                </div>
                <h2 class="text-4xl font-bold mt-4"><%= request.getAttribute("orderCount") %></h2>
            </div>
        </div>
    </div>

    <!-- Right Side: Quick Actions -->
    <div class="lg:w-1/3 rounded-xl p-6 shadow-xl backdrop-blur-lg bg-white/10 border border-white/20">
        <h2 class="text-xl font-bold mb-6 text-white">⚡ Quick Actions</h2>
        <div class="space-y-4">
            <a class="block bg-white/20 hover:bg-white/30 transition-all duration-300 rounded-lg p-4 flex items-center gap-4 text-white shadow-sm hover:shadow-lg"
               href="<%= request.getContextPath() %>/customer">
                <i class="fa-solid fa-people-group text-2xl"></i>
                <span class="text-lg font-semibold">Add New Customer</span>
            </a>
            <a class="block bg-white/20 hover:bg-white/30 transition-all duration-300 rounded-lg p-4 flex items-center gap-4 text-white shadow-sm hover:shadow-lg"
               href="<%= request.getContextPath() %>/item">
                <i class="fa-solid fa-box text-2xl"></i>
                <span class="text-lg font-semibold">Add New Item</span>
            </a>
            <a class="block bg-white/20 hover:bg-white/30 transition-all duration-300 rounded-lg p-4 flex items-center gap-4 text-white shadow-sm hover:shadow-lg"
               href="<%= request.getContextPath() %>/order">
                <i class="fa-solid fa-money-bills text-2xl"></i>
                <span class="text-lg font-semibold">Create Bill</span>
            </a>
        </div>
    </div>

</div>
