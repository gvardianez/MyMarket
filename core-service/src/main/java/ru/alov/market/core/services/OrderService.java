package ru.alov.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alov.market.api.dto.CartDto;
import ru.alov.market.api.dto.OrderDetailsDto;
import ru.alov.market.api.exception.ResourceNotFoundException;
import ru.alov.market.core.exceptions.FieldValidationException;
import ru.alov.market.core.integrations.CartServiceIntegration;
import ru.alov.market.core.repositories.OrderRepository;
import ru.alov.market.core.entities.Order;
import ru.alov.market.core.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public Order createNewOrder(String username, OrderDetailsDto orderDetailsDto) {
        if (orderDetailsDto.getPhone() == null || orderDetailsDto.getAddress() == null)
            throw new FieldValidationException("Необходимо указать телефон и адрес");
        CartDto cart = cartServiceIntegration.getCurrentUserCart(username);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setUsername(username);
        order.setItems(new ArrayList<>());
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        Order finalOrder = order;
        cart.getItems().forEach(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(finalOrder);
            oi.setPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setPricePerProduct(ci.getPricePerProduct());
            oi.setProduct(productService.findById(ci.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
            finalOrder.getItems().add(oi);
        });
        order.setStatus(Order.OrderStatus.CREATED.name());
        order = orderRepository.save(order);
        cartServiceIntegration.clearCart(username);
        return order;
    }

    @Transactional
    public void changeOrderStatus(Long id, Order.OrderStatus orderStatus) {
        findById(id).ifPresentOrElse(order -> order.setStatus(orderStatus.name()), () -> {
            throw new ResourceNotFoundException("Order not found");
        });
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findUserOrders(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
