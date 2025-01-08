package Tests;

import Model.*;
import Service.*;
import Exception.*;
import Repository.FileRepository;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void TestCRUDCustomer() {
        FileRepository<Customer> customerRepository = new FileRepository<>("customersTest.dat");
        Customer customer = new Customer(1, "TestUser", "test@test.com", "password123", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);

        customerRepository.create(customer);
        assertEquals("TestUser", customerRepository.get(1).getUsername());

        Customer fetchedCustomer = customerRepository.get(1);
        assertNotNull(fetchedCustomer);
        assertEquals("test@test.com", fetchedCustomer.getEmail());

        Customer updatedCustomer = new Customer(1, "UpdatedUser", "updated@test.com", "newpass", "Customer", 200.0f, new ArrayList<>(), new ArrayList<>(), null);
        customerRepository.update(updatedCustomer);
        assertEquals("UpdatedUser", customerRepository.get(1).getUsername());

        customerRepository.delete(1);
        assertNull(customerRepository.get(1));
    }

    @Test
    public void TestCRUDAdmin() {
        FileRepository<Admin> adminRepository = new FileRepository<>("adminsTest.dat");
        Admin admin = new Admin(1, "AdminUser", "admin@test.com", "adminpass", "Admin");

        adminRepository.create(admin);
        assertEquals("AdminUser", adminRepository.get(1).getUsername());

        Admin fetchedAdmin = adminRepository.get(1);
        assertNotNull(fetchedAdmin);
        assertEquals("admin@test.com", fetchedAdmin.getEmail());

        Admin updatedAdmin = new Admin(1, "UpdatedAdmin", "updated@test.com", "newadminpass", "Admin");
        adminRepository.update(updatedAdmin);
        assertEquals("UpdatedAdmin", adminRepository.get(1).getUsername());

        adminRepository.delete(1);
        assertNull(adminRepository.get(1));
    }

    @Test
    public void TestCRUDDeveloper() {
        FileRepository<Developer> developerRepository = new FileRepository<>("developersTest.dat");
        Developer developer = new Developer(1, "DevUser", "dev@test.com", "devpass", "Developer", new ArrayList<>());

        developerRepository.create(developer);
        assertEquals("DevUser", developerRepository.get(1).getUsername());

        Developer fetchedDeveloper = developerRepository.get(1);
        assertNotNull(fetchedDeveloper);
        assertEquals("dev@test.com", fetchedDeveloper.getEmail());

        Developer updatedDeveloper = new Developer(1, "UpdatedDev", "updated@test.com", "newdevpass", "Developer", new ArrayList<>());
        developerRepository.update(updatedDeveloper);
        assertEquals("UpdatedDev", developerRepository.get(1).getUsername());

        developerRepository.delete(1);
        assertNull(developerRepository.get(1));
    }

    @Test
    public void TestCRUDGame() {
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest.dat");
        Game game = new Game(1, "TestGame", "A test game description", GameGenre.ADVENTURE, 29.99f, new ArrayList<>());

        gameRepository.create(game);
        assertEquals("TestGame", gameRepository.get(1).getGameName());

        Game fetchedGame = gameRepository.get(1);
        assertNotNull(fetchedGame);
        assertEquals("A test game description", fetchedGame.getGameDescription());

        Game updatedGame = new Game(1, "UpdatedGame", "An updated game description", GameGenre.SHOOTER, 49.99f, new ArrayList<>());
        gameRepository.update(updatedGame);
        assertEquals("UpdatedGame", gameRepository.get(1).getGameName());
        assertEquals(49.99f, gameRepository.get(1).getPrice(), 0.01);

        gameRepository.delete(1);
        assertNull(gameRepository.get(1));
    }

    @Test
    public void TestCRUDDiscount() {
        FileRepository<Discount> discountRepository = new FileRepository<>("discountsTest.dat");
        Discount discount = new Discount(1, 20.0f);

        discountRepository.create(discount);
        assertEquals(20.0f, discountRepository.get(1).getDiscountPercentage());

        Discount updatedDiscount = new Discount(1, 30.0f);
        discountRepository.update(updatedDiscount);
        assertEquals(30.0f, discountRepository.get(1).getDiscountPercentage());

        discountRepository.delete(1);
        assertNull(discountRepository.get(1));
    }

    @Test
    public void TestCRUDReview() {
        FileRepository<Review> reviewRepository = new FileRepository<>("reviewsTest.dat");
        Customer customer = new Customer(1, "Reviewer", "reviewer@test.com", "pass", "Customer", 100.0f, new ArrayList<>(), new ArrayList<>(), null);
        Game game = new Game(1, "Reviewed Game", "Great game", GameGenre.RPG, 49.99f, new ArrayList<>());
        Review review = new Review(1, 3, customer, game);

        reviewRepository.create(review);
        assertEquals(3, reviewRepository.get(1).getRating());

        Review updatedReview = new Review(1, 4, customer, game);
        reviewRepository.update(updatedReview);
        assertEquals(4, reviewRepository.get(1).getRating());

        reviewRepository.delete(1);
        assertNull(reviewRepository.get(1));
    }

    @Test
    public void TestAddGameAsDeveloper() {
        FileRepository<Developer> developerRepository = new FileRepository<>("developersTest2.dat");
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest3.dat");

        Developer developer = new Developer(1, "DevUser", "dev@test.com", "devpass123", "Developer", new ArrayList<>());
        developerRepository.create(developer);

        Game newGame = new Game(1, "New Game", "A new game description", GameGenre.ACTION, 49.99f, new ArrayList<>());

        developer.getPublishedGames().add(newGame);
        gameRepository.create(newGame);

        Game fetchedGame = gameRepository.get(1);
        assertNotNull(fetchedGame);
        assertEquals("New Game", fetchedGame.getGameName());

        assertTrue(developer.getPublishedGames().contains(newGame));
        assertEquals(1, developer.getPublishedGames().size());
    }

    @Test
    public void TestSortAndFilterGames() {
        FileRepository<Game> gameRepository = new FileRepository<>("gamesTest4.dat");
        FileRepository<User> userRepository = new FileRepository<>("usersTest4.dat");
        FileRepository<Customer> customerRepository = new FileRepository<>("customersTest5.dat");
        FileRepository<Review> reviewRepository = new FileRepository<>("reviewsTest5.dat");
        FileRepository<PaymentMethod> paymentMethodRepository = new FileRepository<>("paymentMethodsTest5.dat");
        gameRepository.create(new Game(1, "Cyber Adventure", "Explore a cyber city", GameGenre.ADVENTURE, 59.99f, new ArrayList<>()));
        gameRepository.create(new Game(2, "Space Warfare", "Epic space battles", GameGenre.SHOOTER, 49.99f, new ArrayList<>()));
        gameRepository.create(new Game(3, "Mystic Quest", "Solve magical mysteries", GameGenre.RPG, 39.99f, new ArrayList<>()));
        gameRepository.create(new Game(4, "Farm Builder", "Manage your farm", GameGenre.RPG, 19.99f, new ArrayList<>()));
        gameRepository.create(new Game(5, "Puzzle Challenge", "Solve mind-bending puzzles", GameGenre.PUZZLE, 9.99f, new ArrayList<>()));
        CustomerService customerService = new CustomerService(gameRepository, userRepository, customerRepository, reviewRepository, paymentMethodRepository);

        List<Game> sortedByName = customerService.sortGamesByNameAscending();
        assertEquals("Cyber Adventure", sortedByName.get(0).getGameName());
        assertEquals("Farm Builder", sortedByName.get(1).getGameName());
        assertEquals("Mystic Quest", sortedByName.get(2).getGameName());

        List<Game> sortedByPrice = customerService.sortGamesByPriceDescending();
        assertEquals("Cyber Adventure", sortedByPrice.get(0).getGameName());
        assertEquals("Space Warfare", sortedByPrice.get(1).getGameName());
        assertEquals("Mystic Quest", sortedByPrice.get(2).getGameName());

        List<Game> filteredByGenre = customerService.filterByGenre("RPG");
        assertEquals(2, filteredByGenre.size());
        assertTrue(filteredByGenre.stream().allMatch(game -> game.getGameGenre() == GameGenre.RPG));

        List<Game> filteredByPriceRange = customerService.filterGamesByPriceRange(10.0f, 40.0f);
        assertEquals(2, filteredByPriceRange.size());
        assertTrue(filteredByPriceRange.stream().allMatch(game -> game.getPrice() >= 10.0f && game.getPrice() <= 40.0f));
    }

}
