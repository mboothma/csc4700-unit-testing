package csc4700;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<CartItem> cartItems = new ArrayList<CartItem>();

    public void addItem(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        CartItem cartItem = findCartItem(item);
        if (cartItem == null) {
            cartItem = new CartItem(item);
            cartItems.add(cartItem);
        }

        cartItem.incrementCountByOne();
    }

    public void deleteItem(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        CartItem cartItem = findCartItem(item);
        if (cartItem == null) {
            return;
        }

        if (cartItem.getCount() == 1) {
            // Removing the last of this item, so remove it
            // fully from the cart
            cartItems.remove(cartItem);
        }
        else {
            cartItem.decrementCountByOne();
        }
    }

    public CartItem findCartItem(Item item) {
        for (CartItem i : cartItems) {
            if (i.getItem().equals(item)) {
                return i;
            }
        }
        return null;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
