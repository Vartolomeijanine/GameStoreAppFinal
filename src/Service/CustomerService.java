package Service;

import Model.*;
import Repository.IRepository;

public class CustomerService {
    private final IRepository<Game> gameRepository;
    private final IRepository<User> userRepository;
    private final IRepository<Customer> customerRepository;
    private final IRepository<Review> reviewRepository;
    private final IRepository<PaymentMethod> paymentMethodRepository;
    private Customer loggedInCustomer;

    public CustomerService(IRepository<Game> gameRepository, IRepository<User> userRepository, IRepository<Customer> customerRepository, IRepository<Review> reviewRepository, IRepository<PaymentMethod> paymentMethodRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.reviewRepository = reviewRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }
}
