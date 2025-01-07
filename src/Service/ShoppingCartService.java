package Service;

import Model.Customer;
import Model.Game;
import Model.Order;
import Model.ShoppingCart;
import Repository.IRepository;
import Exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService {
    private final IRepository<ShoppingCart> shoppingCartRepository;;
    private final IRepository<Game> gameRepository;
    private final IRepository<Order> orderRepository;
    private final IRepository<Customer> customerRepository;

    public ShoppingCartService(IRepository<ShoppingCart> shoppingCartRepository, IRepository<Game> gameRepository, IRepository<Order> orderRepository, IRepository<Customer> customerRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
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
        Customer customer = cart.getCustomer();

        if (customer.getGamesLibrary().stream().anyMatch(game -> game.getGameId() == gameId)) {
            throw new IllegalArgumentException("The game is already in your library.");
        }

        if (cart.getListOfGames().stream().anyMatch(game -> game.getGameId() == gameId)) {
            throw new IllegalArgumentException("The game is already in your cart.");
        }

        if ("CHECKED_OUT".equals(cart.getStatus())) {
            cart.setStatus("ACTIVE");
            cart.getListOfGames().clear();
            shoppingCartRepository.update(cart);
        }

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

        if (cart == null) {
            throw new IllegalArgumentException("Shopping cart not found.");
        }

        if (cart.getListOfGames().isEmpty()) {
            throw new IllegalArgumentException("Your cart is empty.");
        }

        Customer customer = customerRepository.get(cart.getCustomer().getId());
        if (customer == null) {
            throw new EntityNotFoundException("No customer associated with this shopping cart.");
        }

        //float totalPrice = getCartTotalPrice(shoppingCartId);
        float totalPrice = 0.0f;
        for (Game game : cart.getListOfGames()) {
            totalPrice += game.getDiscountedPrice();
        }

        if (customer.getFundWallet() < totalPrice) {
            throw new IllegalArgumentException("Insufficient funds in your wallet.");
        }

        customer.setFundWallet(customer.getFundWallet() - totalPrice);

        List<Game> gamesInCart = new ArrayList<>(cart.getListOfGames());
        customer.getGamesLibrary().addAll(gamesInCart);

        Order order = new Order(generateOrderId(), customer, gamesInCart);
        orderRepository.create(order);

        //customer.getGamesLibrary().addAll(gamesInCart);

        cart.getListOfGames().clear();
        cart.setStatus("CHECKED_OUT");
        customerRepository.update(customer);
        shoppingCartRepository.update(cart);

        System.out.println("Checkout completed successfully!");

    }


    public void clearCart(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);

        if (!cart.getStatus().equals("ACTIVE")) {
            throw new IllegalArgumentException("Cannot clear a checked-out cart.");
        }

        cart.getListOfGames().clear();
        shoppingCartRepository.update(cart);
    }
    public void resetCartForCustomer(int shoppingCartId) {
        ShoppingCart cart = getShoppingCart(shoppingCartId);

        if (cart.getStatus().equals("CHECKED_OUT")) {
            cart.getListOfGames().clear();
            cart.setStatus("ACTIVE");
            shoppingCartRepository.update(cart);
        } else {
            throw new IllegalArgumentException("The shopping cart is already active.");
        }
    }

    private int generateOrderId() {
        List<Order> allOrders = orderRepository.getAll();
        return allOrders.stream()
                .mapToInt(Order::getId)
                .max()
                .orElse(0) + 1;
    }

    public ShoppingCart createNewActiveCart(Customer customer) {
        ShoppingCart newCart = new ShoppingCart(generateShoppingCartId(), customer);
        shoppingCartRepository.create(newCart);
        return newCart;
    }

    private int generateShoppingCartId() {
        return shoppingCartRepository.getAll().size() + 1;
    }

    public List<Order> getOrderHistory() {
        return orderRepository.getAll();
    }



}
