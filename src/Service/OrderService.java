package Service;

import Model.Customer;
import Model.Order;
import Repository.IRepository;
import Exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing orders, including creating, retrieving, and filtering orders by customer.
 */
public class OrderService {
    private final IRepository<Order> orderRepository;

    /**
     * Constructs the OrderService with the specified order repository.
     *
     * @param orderRepository The repository for managing orders.
     */
    public OrderService(IRepository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order and adds it to the repository.
     *
     * @param order The order to create.
     */
    public void createOrder(Order order) {
        orderRepository.create(order);
    }


    /**
     * Retrieves all orders from the repository.
     *
     * @return A list of all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    /**
     * Retrieves all orders associated with a specific customer.
     *
     * @param customer The customer whose orders are to be retrieved.
     * @return A list of orders for the specified customer.
     * @throws EntityNotFoundException if the provided customer is null.
     */
    public List<Order> getAllOrdersByCustomer(Customer customer) {
        if (customer == null) {
            throw new EntityNotFoundException("Customer is not logged in.");
        }

        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orderRepository.getAll()) {
            if (order.getCustomer().getId().equals(customer.getId())) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID.
     * @throws EntityNotFoundException if the order with the specified ID is not found.
     */
    public Order getOrderById(int orderId) {
        Order order = orderRepository.get(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order not found.");
        }
        return order;
    }
}
