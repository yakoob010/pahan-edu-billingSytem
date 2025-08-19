<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("pageTitle") != null ? request.getAttribute("pageTitle") : "Login - Pahana Edu Billing System" %></title>

    <!-- Tailwind CSS + DaisyUI -->
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.10/dist/full.min.css" rel="stylesheet" type="text/css">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .login-card {
            backdrop-filter: blur(10px);
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
    </style>
</head>
<body class="flex items-center justify-center min-h-screen p-4">

<div class="login-card rounded-2xl shadow-2xl p-8 w-full max-w-md">
    <!-- Logo/Header -->
    <div class="text-center mb-8">
        <div class="mb-4">
            <i class="fas fa-graduation-cap text-5xl text-primary"></i>
        </div>
        <h1 class="text-3xl font-bold text-white mb-2">Pahana Edu</h1>
        <p class="text-gray-300">Billing System</p>
    </div>

    <!-- Login Form -->
    <form method="post" action="<%= request.getContextPath() %>/login" class="space-y-6">

        <!-- Username Field -->
        <div class="form-control">
            <label class="label">
                    <span class="label-text text-white font-semibold">
                        <i class="fas fa-user mr-2"></i>Username
                    </span>
            </label>
            <input type="text"
                   name="username"
                   value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                   placeholder="Enter your username"
                   class="input input-bordered bg-white/20 text-white placeholder-gray-300 border-white/30 focus:border-primary focus:bg-white/30"
                   required
                   autocomplete="username">
        </div>

        <!-- Password Field -->
        <div class="form-control">
            <label class="label">
                    <span class="label-text text-white font-semibold">
                        <i class="fas fa-lock mr-2"></i>Password
                    </span>
            </label>
            <div class="relative">
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your password"
                       class="input input-bordered w-full bg-white/20 text-white placeholder-gray-300 border-white/30 focus:border-primary focus:bg-white/30 pr-12"
                       required
                       autocomplete="current-password">
                <button type="button"
                        id="togglePassword"
                        class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-300 hover:text-white transition-colors">
                    <i class="fas fa-eye" id="eyeIcon"></i>
                </button>
            </div>
        </div>

        <!-- Error Message -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error bg-red-600/20 border-red-500/50">
            <i class="fas fa-exclamation-circle"></i>
            <span><%= request.getAttribute("error") %></span>
        </div>
        <% } %>

        <!-- Login Button -->
        <button type="submit" class="btn btn-primary w-full text-lg font-semibold">
            <i class="fas fa-sign-in-alt mr-2"></i>
            Sign In
        </button>
    </form>

    <!-- Demo Credentials Info -->
    <div class="mt-8 p-4 bg-info/20 rounded-lg border border-info/30">
        <div class="text-center text-info-content">
            <i class="fas fa-info-circle mr-2"></i>
            <span class="font-semibold">Demo Credentials</span>
        </div>
        <div class="mt-2 text-sm text-gray-300 text-center">
            <div><strong>Username:</strong> admin</div>
            <div><strong>Password:</strong> admin123</div>
        </div>
    </div>

    <!-- Footer -->
    <div class="text-center mt-8 text-gray-400 text-sm">
        <p>&copy; 2025 Pahana Edu. All rights reserved.</p>
    </div>
</div>

<!-- JavaScript -->
<script>
    // Password visibility toggle
    document.getElementById('togglePassword').addEventListener('click', function() {
        const passwordField = document.getElementById('password');
        const eyeIcon = document.getElementById('eyeIcon');

        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordField.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    });

    // Show flash messages if any
    <%
    String flashSuccess = (String) session.getAttribute("flash_success");
    String flashError = (String) session.getAttribute("flash_error");
    if (flashSuccess != null) {
        session.removeAttribute("flash_success");
    %>
    Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: '<%= flashSuccess %>',
        theme: 'dark',
        timer: 4000,
        timerProgressBar: true
    });
    <% } else if (flashError != null) {
        session.removeAttribute("flash_error");
    %>
    Swal.fire({
        icon: 'error',
        title: 'Error!',
        text: '<%= flashError %>',
        theme: 'dark'
    });
    <% } %>

    // Auto-focus username field on page load
    window.addEventListener('load', function() {
        const usernameField = document.querySelector('input[name="username"]');
        if (usernameField && !usernameField.value) {
            usernameField.focus();
        }
    });

    // Form submission loading state
    document.querySelector('form').addEventListener('submit', function() {
        const submitBtn = document.querySelector('button[type="submit"]');
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin mr-2"></i>Signing In...';
    });
</script>

</body>
</html>

