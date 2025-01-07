package Service;

import Model.Customer;
import Model.Order;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final IRepository<Order> orderRepository;

    public OrderService(IRepository<Order> orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        orderRepository.create(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public List<Order> getAllOrdersByCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is not logged in.");
        }

        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orderRepository.getAll()) {
            if (order.getCustomer().getId().equals(customer.getId())) {
                customerOrders.add(order);
            }
        }

        return customerOrders;
    }

    public Order getOrderById(int orderId) {
        Order order = orderRepository.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }
        return order;
    }
}
