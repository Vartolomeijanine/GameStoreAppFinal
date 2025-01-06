package Service;

import Model.Game;
import Model.ShoppingCart;
import Repository.IRepository;

import java.util.List;

public class ShoppingCartService {
    private final IRepository<ShoppingCart> shoppingCartRepository;;
    private final IRepository<Game> gameRepository;

    public ShoppingCartService(IRepository<ShoppingCart> shoppingCartRepository, IRepository<Game> gameRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }

    public ShoppingCart getShoppingCart(int shoppingCartId) {
        ShoppingCart cart = shoppingCartRepository.get(shoppingCartId);
        if (cart == null) {
            throw new IllegalArgumentException("Shopping cart not found.");
        }
        return cart;
    }

    public void addGameToCart(int shoppingCartId, int gameId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        cart.getListOfGames().add(game);
        shoppingCartRepository.update(cart);
    }

    public void removeGameFromCart(int shoppingCartId, int gameId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        boolean removed = cart.getListOfGames().removeIf(game -> game.getGameId() == gameId);
        if (!removed) {
            throw new IllegalArgumentException("Game not found in cart.");
        }
        shoppingCartRepository.update(cart);
    }

    public float getCartTotalPrice(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        float totalPrice = 0.0f;

        for (Game game : cart.getListOfGames()) {
            totalPrice += game.getPrice();
        }

        return totalPrice;
    }

    public void checkout(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        if (cart.getListOfGames().isEmpty()) {
            throw new IllegalArgumentException("Your cart is empty.");
        }
        cart.getListOfGames().clear();
        shoppingCartRepository.update(cart);
    }
}
