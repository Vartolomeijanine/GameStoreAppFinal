package Service;

import Model.Order;
import Repository.IRepository;

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

    public Order getOrderById(int orderId) {
        Order order = orderRepository.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found.");
        }
        return order;
    }

    public void deleteOrder(int orderId) {
        orderRepository.delete(orderId);
    }
}
