<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" data-theme="sunset">
<head>
    <meta charset="UTF-8">
    <title>Login - Pahana Education</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/theme.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body class="min-h-screen bg-gradient-to-r from-purple-500 via-pink-500 to-red-500 flex items-center justify-center p-4">
    <div class="bg-white p-8 rounded-lg shadow-xl w-96">
        <div class="text-center mb-8">
            <h1 class="text-2xl font-bold text-gray-800">Welcome Back!</h1>
            <p class="text-gray-600">Login to access your dashboard</p>
        </div>

        <form method="post" action="<%= request.getContextPath() %>/admin-login" class="space-y-6">
            <% if (request.getAttribute("error") != null) { %>
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                    <span class="block sm:inline"><%= request.getAttribute("error") %></span>
                </div>
            <% } %>

            <div>
                <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
                    Username
                </label>
                <div class="relative">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                        <i class="fas fa-user text-gray-400"></i>
                    </span>
                    <input type="text" id="username" name="username" required
                           class="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                           placeholder="Enter your username">
                </div>
            </div>

            <div>
                <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
                    Password
                </label>
                <div class="relative">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                        <i class="fas fa-lock text-gray-400"></i>
                    </span>
                    <input type="password" id="password" name="password" required
                           class="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                           placeholder="Enter your password">
                </div>
            </div>

            <button type="submit" class="w-full bg-gradient-to-r from-purple-500 via-pink-500 to-red-500 text-white py-2 px-4 rounded-md hover:opacity-90 transition duration-300">
                Login
            </button>
        </form>
    </div>
</body>
</html>

