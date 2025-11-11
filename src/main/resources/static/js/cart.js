// Shopping cart functionality
class ShoppingCart {
    constructor() {
        this.items = this.loadCart();
        this.updateCartDisplay();
    }

    loadCart() {
        return JSON.parse(localStorage.getItem('cartItems')) || [];
    }

    saveCart() {
        localStorage.setItem('cartItems', JSON.stringify(this.items));
        this.updateCartCount();
    }

    addItem(productId, productName, price, quantity = 1, image = '') {
        const existingItem = this.items.find(item => item.id === productId);

        if (existingItem) {
            existingItem.quantity += quantity;
        } else {
            this.items.push({
                id: productId,
                name: productName,
                price: price,
                quantity: quantity,
                image: image
            });
        }

        this.saveCart();
        Utils.showNotification(`${productName} added to cart!`, 'success');
    }

    removeItem(productId) {
        this.items = this.items.filter(item => item.id !== productId);
        this.saveCart();
        Utils.showNotification('Item removed from cart', 'info');
    }

    updateQuantity(productId, quantity) {
        const item = this.items.find(item => item.id === productId);
        if (item) {
            item.quantity = quantity;
            if (item.quantity <= 0) {
                this.removeItem(productId);
            } else {
                this.saveCart();
            }
        }
    }

    clearCart() {
        this.items = [];
        this.saveCart();
        Utils.showNotification('Cart cleared', 'info');
    }

    getTotalPrice() {
        return this.items.reduce((total, item) => total + (item.price * item.quantity), 0);
    }

    getTotalItems() {
        return this.items.reduce((total, item) => total + item.quantity, 0);
    }

    updateCartDisplay() {
        // Update cart count in navigation
        const cartCountElements = document.querySelectorAll('.cart-count');
        const totalItems = this.getTotalItems();

        cartCountElements.forEach(element => {
            element.textContent = totalItems;
        });

        // Update cart page if exists
        if (document.getElementById('cartItems')) {
            this.renderCartItems();
        }
    }

    renderCartItems() {
        const cartContainer = document.getElementById('cartItems');
        const emptyCart = document.getElementById('emptyCart');

        if (this.items.length === 0) {
            cartContainer.style.display = 'none';
            emptyCart.style.display = 'block';
            return;
        }

        cartContainer.style.display = 'block';
        emptyCart.style.display = 'none';

        // Render cart items
        // This would be implemented based on your cart page structure
    }
}

// Initialize cart
const cart = new ShoppingCart();

// Global functions for HTML onclick attributes
function addToCart(productId, productName, price) {
    cart.addItem(productId, productName, price, 1);
}

function removeFromCart(productId) {
    if (confirm('Are you sure you want to remove this item?')) {
        cart.removeItem(productId);
    }
}