<div class="drawer-side">
    <label for="my-drawer-2" aria-label="close sidebar" class="drawer-overlay"></label>
    <ul class="menu bg-base-200 text-base-content min-h-full w-64 gap-2 p-4">
        <li>
            <a href="<%= request.getContextPath() %>/dashboard" class="flex items-center p-3 rounded-lg transition-all
            <%= request.getRequestURI().endsWith("/dashboard") ? "text-primary" : "" %>
                focus:text-primary active:text-primary">
                <i class="fa-solid fa-house mr-3 text-xl"></i>
                Dashboard
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/customer" class="flex items-center p-3 rounded-lg transition-all
            <%= request.getRequestURI().endsWith("/customer") ? "text-primary" : "" %>
                focus:text-primary active:text-primary">
                <i class="fa-solid fa-users mr-3 text-xl"></i>
                Customers
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/item" class="flex items-center p-3 rounded-lg transition-all
            <%= request.getRequestURI().endsWith("/item") ? "text-primary" : "" %>
                focus:text-primary active:text-primary">
                <i class="fa-solid fa-boxes-stacked mr-3 text-xl"></i>
                Items
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/order" class="flex items-center p-3 rounded-lg transition-all
            <%= request.getRequestURI().endsWith("/order") ? "text-primary" : "" %>
                focus:text-primary active:text-primary">
                <i class="fa-solid fa-cart-shopping mr-3 text-xl"></i>
                Orders
            </a>
        </li>
    </ul>
</div>