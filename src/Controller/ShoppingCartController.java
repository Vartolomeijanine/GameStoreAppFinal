package Controller;

import Model.Customer;
import Model.Game;
import Model.Order;
import Model.ShoppingCart;
import Service.OrderService;
import Service.ShoppingCartService;

import java.util.List;

/**
 * Controller for managing shopping cart and order operations.
 */
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    /**
     * Constructs the ShoppingCartController with ShoppingCartService and OrderService instances.
     *
     * @param shoppingCartService The service used for shopping cart operations.
     * @param orderService        The service used for order operations.
     */
    public ShoppingCartController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    /**
     * Retrieves all games available in the system.
     *
     * @return A list of all games.
     */
    public List<Game> getAllGames() {
        return shoppingCartService.getAllGames();
    }


    /**
     * Retrieves a shopping cart by its ID.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @return The shopping cart with the specified ID.
     */
    public ShoppingCart getShoppingCart(int shoppingCartId) {
        return shoppingCartService.getShoppingCart(shoppingCartId);
    }

    /**
     * Adds a game to the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @param gameId         The ID of the game to add.
     */
    public void addGameToCart(int shoppingCartId, int gameId) {
        shoppingCartService.addGameToCart(shoppingCartId, gameId);
    }

    /**
     * Removes a game from the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @param gameId         The ID of the game to remove.
     */
    public void removeGameFromCart(int shoppingCartId, int gameId) {
        shoppingCartService.removeGameFromCart(shoppingCartId, gameId);
    }

    /**
     * Calculates the total price of all games in the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @return The total price of the games in the cart.
     */
    public float getCartTotalPrice(int shoppingCartId) {
        return shoppingCartService.getCartTotalPrice(shoppingCartId);
    }

    /**
     * Completes the checkout process for the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     */
    public void checkout(int shoppingCartId) {
        shoppingCartService.checkout(shoppingCartId);
    }

    /**
     * Clears all games from the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     */
    public void clearCart(int shoppingCartId) {
        shoppingCartService.clearCart(shoppingCartId);
    }

    /**
     * Resets the shopping cart to an active state.
     *
     * @param shoppingCartId The ID of the shopping cart.
     */
    public void resetCartForCustomer(int shoppingCartId) {
        shoppingCartService.resetCartForCustomer(shoppingCartId);
    }

    /**
     * Retrieves the order history for all customers.
     *
     * @return A list of all orders in the system.
     */
    public List<Order> getOrderHistory() {
        return shoppingCartService.getOrderHistory();
    }

    /**
     * Retrieves all orders placed by a specific customer.
     *
     * @param customer The customer whose orders are to be retrieved.
     * @return A list of orders placed by the customer.
     */
    public List<Order> getAllOrdersByCustomer(Customer customer) {
        return orderService.getAllOrdersByCustomer(customer);
    }
}
