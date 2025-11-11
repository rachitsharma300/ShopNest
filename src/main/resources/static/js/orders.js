// Order management functionality
class OrderManager {
    constructor() {
        this.currentStep = 1;
        this.shippingAddress = {};
        this.paymentMethod = 'razorpay';
    }

    initCheckout() {
        this.setupAddressForm();
        this.setupPaymentMethods();
    }

    setupAddressForm() {
        const form = document.getElementById('checkoutForm');
        if (form) {
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                this.placeOrder();
            });
        }
    }

    setupPaymentMethods() {
        const paymentMethods = document.querySelectorAll('.payment-method');
        paymentMethods.forEach(method => {
            method.addEventListener('click', (e) => {
                this.selectPaymentMethod(e.currentTarget);
            });
        });
    }

    selectPaymentMethod(methodElement) {
        document.querySelectorAll('.payment-method').forEach(m => {
            m.classList.remove('selected');
        });
        methodElement.classList.add('selected');

        const method = methodElement.querySelector('input').value;
        this.paymentMethod = method;
    }

    placeOrder() {
        // Validate form
        if (!this.validateForm()) {
            Utils.showNotification('Please fill all required fields', 'error');
            return;
        }

        // Show loading
        const submitBtn = document.querySelector('#checkoutForm button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Processing...';
        submitBtn.disabled = true;

        // Simulate API call
        setTimeout(() => {
            Utils.showNotification('Order placed successfully!', 'success');
            // Redirect to payment page
            window.location.href = '/orders/payment?orderId=' + this.generateOrderId();
        }, 2000);
    }

    validateForm() {
        const requiredFields = document.querySelectorAll('#checkoutForm [required]');
        let isValid = true;

        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                field.classList.add('is-invalid');
                isValid = false;
            } else {
                field.classList.remove('is-invalid');
            }
        });

        return isValid;
    }

    generateOrderId() {
        return 'ORD-' + Date.now();
    }
}

// Initialize order manager
const orderManager = new OrderManager();