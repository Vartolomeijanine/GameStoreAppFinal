package Controller;

import Model.Customer;
import Model.Game;
import Model.Order;
import Model.ShoppingCart;
import Service.OrderService;
import Service.ShoppingCartService;

import java.util.List;

public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    public List<Game> getAllGames() {
        return shoppingCartService.getAllGames();
    }

    public ShoppingCart getShoppingCart(int shoppingCartId) {
        return shoppingCartService.getShoppingCart(shoppingCartId);
    }

    public void addGameToCart(int shoppingCartId, int gameId) {
        shoppingCartService.addGameToCart(shoppingCartId, gameId);
    }

    public void removeGameFromCart(int shoppingCartId, int gameId) {
        shoppingCartService.removeGameFromCart(shoppingCartId, gameId);
    }

    public float getCartTotalPrice(int shoppingCartId) {
        return shoppingCartService.getCartTotalPrice(shoppingCartId);
    }

    public void checkout(int shoppingCartId) {
        shoppingCartService.checkout(shoppingCartId);
    }

    public void clearCart(int shoppingCartId) {
        shoppingCartService.clearCart(shoppingCartId);
    }

    public void resetCartForCustomer(int shoppingCartId) {
        shoppingCartService.resetCartForCustomer(shoppingCartId);
    }

    public List<Order> getOrderHistory() {
        return shoppingCartService.getOrderHistory();
    }

    public List<Order> getAllOrdersByCustomer(Customer customer) {
        return orderService.getAllOrdersByCustomer(customer);
    }


}
