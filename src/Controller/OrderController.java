package Controller;

import Model.Order;
import Service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void createOrder(Order order) {
        orderService.createOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public Order getOrderById(int orderId) {
        return orderService.getOrderById(orderId);
    }
}

