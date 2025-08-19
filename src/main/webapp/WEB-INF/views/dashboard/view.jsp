<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Pahana Edu Billing System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
        .sidebar {
            min-height: 100vh;
            background: #2c3e50;
            color: white;
        }
        .sidebar .nav-link {
            color: rgba(255, 255, 255, 0.8);
            margin: 5px 0;
            border-radius: 5px;
            padding: 10px 15px;
        }
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background: rgba(255, 255, 255, 0.1);
            color: white;
        }
        .sidebar .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }
        .main-content {
            padding: 20px;
        }
        .welcome-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px;
            padding: 30px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-bottom: 20px;
            transition: transform 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-card i {
            font-size: 2.5rem;
            margin-bottom: 15px;
            color: #6c757d;
        }
        .stat-card h3 {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 5px;
        }
        .stat-card p {
            color: #6c757d;
            margin-bottom: 0;
        }
        .navbar {
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="d-flex">
    <!-- Sidebar -->
    <div class="sidebar" style="width: 250px;">
        <div class="p-3 text-center">
            <h4>Pahana Edu</h4>
            <p class="text-muted">Billing System</p>
        </div>
        <hr>
        <ul class="nav flex-column p-3">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-link active">
                    <i class="bi bi-speedometer2"></i> Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/customer" class="nav-link">
                    <i class="bi bi-people"></i> Customers
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/item" class="nav-link">
                    <i class="bi bi-box-seam"></i> Items
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/order" class="nav-link">
                    <i class="bi bi-receipt"></i> Invoices
                </a>
            </li>
            <li class="nav-item mt-4">
                <a href="${pageContext.request.contextPath}/logout" class="nav-link text-danger">
                    <i class="bi bi-box-arrow-right"></i> Logout
                </a>
            </li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="flex-grow-1">
        <!-- Top Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container-fluid">
                <button class="btn btn-link text-dark d-md-none" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu">
                    <i class="bi bi-list"></i>
                </button>
                <div class="ms-auto d-flex align-items-center">
                    <span class="me-3">Welcome, ${sessionScope.username}</span>
                    <div class="dropdown">
                        <a href="#" class="d-block text-dark text-decoration-none dropdown-toggle" id="dropdownUser" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle" style="font-size: 1.5rem;"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUser">
                            <li><a class="dropdown-item" href="#">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>

        <!-- Page Content -->
        <div class="main-content">
            <div class="welcome-card">
                <h2>Welcome back, ${sessionScope.username}!</h2>
                <p class="mb-0">Here's what's happening with your store today.</p>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="stat-card text-center">
                        <i class="bi bi-people"></i>
                        <h3>${not empty customerCount ? customerCount : '0'}</h3>
                        <p>Total Customers</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-card text-center">
                        <i class="bi bi-box-seam"></i>
                        <h3>${not empty itemCount ? itemCount : '0'}</h3>
                        <p>Total Items</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="stat-card text-center">
                        <i class="bi bi-receipt"></i>
                        <h3>${not empty orderCount ? orderCount : '0'}</h3>
                        <p>Total Orders</p>
                    </div>
                </div>
            </div>

            <!-- Recent Activity Section -->
            <div class="card mt-4">
                <div class="card-header bg-white">
                    <h5 class="mb-0">Recent Activity</h5>
                </div>
                <div class="card-body">
                    <p class="text-muted">No recent activity to show</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
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
