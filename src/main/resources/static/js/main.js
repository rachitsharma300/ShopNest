// Common JavaScript functions for entire application

// Initialize tooltips
document.addEventListener('DOMContentLoaded', function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Common utility functions
const Utils = {
    // Format currency
    formatCurrency: (amount) => {
        return '₹' + parseInt(amount).toLocaleString();
    },

    // Show notification
    showNotification: (message, type = 'info') => {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
        notification.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
        notification.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.body.appendChild(notification);

        // Auto remove after 5 seconds
        setTimeout(() => {
            if (notification.parentNode) {
                notification.remove();
            }
        }, 5000);
    },

    // Debounce function for search
    debounce: (func, wait) => {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }
};

// Cart management
const CartManager = {
    getCartCount: () => {
        return parseInt(localStorage.getItem('cartCount') || '0');
    },

    updateCartCount: (count) => {
        localStorage.setItem('cartCount', count.toString());
        this.updateCartUI();
    },

    updateCartUI: () => {
        const cartElements = document.querySelectorAll('.cart-count');
        const count = this.getCartCount();
        cartElements.forEach(element => {
            element.textContent = count;
        });
    }
};