// Products related functionality
class ProductManager {
    constructor() {
        this.filters = {
            category: 'all',
            priceRange: 50000,
            searchTerm: '',
            brands: [],
            ratings: []
        };

        this.initEventListeners();
    }

    initEventListeners() {
        // Search functionality with debounce
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', Utils.debounce((e) => {
                this.filters.searchTerm = e.target.value;
                this.applyFilters();
            }, 300));
        }

        // Price range
        const priceRange = document.getElementById('priceRange');
        if (priceRange) {
            priceRange.addEventListener('input', (e) => {
                this.filters.priceRange = e.target.value;
                document.getElementById('priceRangeValue').textContent =
                    Utils.formatCurrency(e.target.value);
            });
        }
    }

    applyFilters() {
        // In real implementation, this would make an API call
        console.log('Applying filters:', this.filters);
        Utils.showNotification('Filters applied', 'success');
    }

    sortProducts(sortBy) {
        // Implementation for sorting products
        console.log('Sorting by:', sortBy);
    }

    setViewMode(mode) {
        const productsGrid = document.getElementById('productsGrid');
        if (mode === 'list') {
            productsGrid.classList.add('list-view');
        } else {
            productsGrid.classList.remove('list-view');
        }
    }
}

// Initialize product manager
const productManager = new ProductManager();

// Global functions
function filterByCategory(category) {
    productManager.filters.category = category;
    document.querySelectorAll('.category-filter li').forEach(li => {
        li.classList.remove('active');
    });
    event.target.classList.add('active');
}