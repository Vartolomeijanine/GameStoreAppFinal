package Service;

import Model.Customer;
import Model.Game;
import Model.Order;
import Model.ShoppingCart;
import Repository.IRepository;
import Exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing shopping carts, including adding and removing games,
 * clearing the cart, and handling the checkout process.
 */
public class ShoppingCartService {
    private final IRepository<ShoppingCart> shoppingCartRepository;;
    private final IRepository<Game> gameRepository;
    private final IRepository<Order> orderRepository;
    private final IRepository<Customer> customerRepository;

    /**
     * Constructs the ShoppingCartService with the specified repositories.
     *
     * @param shoppingCartRepository Repository for managing shopping carts.
     * @param gameRepository         Repository for managing games.
     * @param orderRepository        Repository for managing orders.
     * @param customerRepository     Repository for managing customers.
     */
    public ShoppingCartService(IRepository<ShoppingCart> shoppingCartRepository, IRepository<Game> gameRepository, IRepository<Order> orderRepository, IRepository<Customer> customerRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }


    /**
     * Retrieves all available games.
     *
     * @return A list of all games.
     */
    public List<Game> getAllGames() {
        return gameRepository.getAll();
    }


    /**
     * Retrieves a shopping cart by its ID.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @return The shopping cart with the specified ID.
     * @throws EntityNotFoundException if the shopping cart is not found.
     */
    public ShoppingCart getShoppingCart(int shoppingCartId) {
        ShoppingCart cart = shoppingCartRepository.get(shoppingCartId);
        if (cart == null) {
            throw new EntityNotFoundException("Shopping cart not found.");
        }
        return cart;
    }


    /**
     * Adds a game to a customer's shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @param gameId         The ID of the game to add.
     * @throws BusinessLogicException   if the game is already in the customer's library or cart.
     * @throws EntityNotFoundException if the game or shopping cart is not found.
     */
    public void addGameToCart(int shoppingCartId, int gameId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        Customer customer = cart.getCustomer();

        if (customer.getGamesLibrary().stream().anyMatch(game -> game.getGameId() == gameId)) {
            throw new BusinessLogicException("The game is already in your library.");
        }

        if (cart.getListOfGames().stream().anyMatch(game -> game.getGameId() == gameId)) {
            throw new BusinessLogicException("The game is already in your cart.");
        }

        if ("CHECKED_OUT".equals(cart.getStatus())) {
            cart.setStatus("ACTIVE");
            cart.getListOfGames().clear();
            shoppingCartRepository.update(cart);
        }

        Game game = gameRepository.get(gameId);
        if (game == null) {
            throw new EntityNotFoundException("Game not found.");
        }

        cart.getListOfGames().add(game);
        var fundWallet = customerRepository.get(customer.getId()).getFundWallet();
        customer.setFundWallet(fundWallet);
        customerRepository.update(customer);
        shoppingCartRepository.update(cart);
    }

    /**
     * Removes a game from a shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @param gameId         The ID of the game to remove.
     * @throws EntityNotFoundException if the game is not found in the cart.
     */
    public void removeGameFromCart(int shoppingCartId, int gameId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);

        boolean removed = cart.getListOfGames().removeIf(game -> game.getGameId() == gameId);
        if (!removed) {
            throw new EntityNotFoundException("Game not found in cart.");
        }
        shoppingCartRepository.update(cart);
    }

    /**
     * Calculates the total price of all games in the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @return The total price of the games in the cart.
     */
    public float getCartTotalPrice(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);
        float totalPrice = 0.0f;

        for (Game game : cart.getListOfGames()) {
            totalPrice += game.getPrice();
        }

        return totalPrice;
    }

    /**
     * Completes the checkout process for a shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart to process.
     * @throws EntityNotFoundException if the shopping cart or associated customer is not found.
     * @throws BusinessLogicException   if the shopping cart is empty or the customer has insufficient funds.
     */
    public void checkout(int shoppingCartId) {

        ShoppingCart cart = getShoppingCart(shoppingCartId);

        if (cart == null) {
            throw new EntityNotFoundException("Shopping cart not found.");
        }

        if (cart.getListOfGames().isEmpty()) {
            throw new BusinessLogicException("Your cart is empty.");
        }

        Customer customer = customerRepository.get(cart.getCustomer().getId());
        if (customer == null) {
            throw new EntityNotFoundException("No customer associated with this shopping cart.");
        }

        float totalPrice = 0.0f;
        for (Game game : cart.getListOfGames()) {
            totalPrice += game.getDiscountedPrice();
        }

        if (customer.getFundWallet() < totalPrice) {
            throw new BusinessLogicException("Insufficient funds in your wallet.");
        }

        customer.setFundWallet(customer.getFundWallet() - totalPrice);

        List<Game> gamesInCart = new ArrayList<>(cart.getListOfGames());
        customer.getGamesLibrary().addAll(gamesInCart);

        Order order = new Order(generateOrderId(), customer, gamesInCart);
        orderRepository.create(order);

        cart.getListOfGames().clear();
        cart.setStatus("CHECKED_OUT");
        customerRepository.update(customer);
        shoppingCartRepository.update(cart);

        System.out.println("Checkout completed successfully!");
    }

    /**
     * Clears all games from the shopping cart.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @throws BusinessLogicException if the cart is not active.
     */
    public void clearCart(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);

        if (!cart.getStatus().equals("ACTIVE")) {
            throw new BusinessLogicException("Cannot clear a checked-out cart.");
        }

        cart.getListOfGames().clear();
        shoppingCartRepository.update(cart);
    }

    /**
     * Resets a checked-out cart to an active state.
     *
     * @param shoppingCartId The ID of the shopping cart.
     * @throws BusinessLogicException if the cart is already active.
     */
    public void resetCartForCustomer(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);

        if (cart.getStatus().equals("CHECKED_OUT")) {
            cart.getListOfGames().clear();
            cart.setStatus("ACTIVE");
            shoppingCartRepository.update(cart);
        } else {
            throw new BusinessLogicException("The shopping cart is already active.");
        }
    }

    /**
     * Generates a unique ID for a new order.
     *
     * @return The next unique order ID.
     */
    private int generateOrderId() {
        List<Order> allOrders = orderRepository.getAll();
        return allOrders.stream()
                .mapToInt(Order::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Retrieves the entire order history.
     *
     * @return A list of all orders.
     */
    public List<Order> getOrderHistory() {
        return orderRepository.getAll();
    }



}
