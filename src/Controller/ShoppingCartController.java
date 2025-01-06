package Controller;

import Model.Game;
import Model.ShoppingCart;
import Service.ShoppingCartService;

import java.util.List;

public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
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
}
