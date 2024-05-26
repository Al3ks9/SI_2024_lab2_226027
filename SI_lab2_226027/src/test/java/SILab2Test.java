package test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import main.java.SILab2;
import main.java.Item;

public class SILab2Test {

    @Test
    void testNullAllItems() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, 100);
        });
        assertEquals("allItems list can't be null!", exception.getMessage());
    }

    @Test
    void testEmptyAllItems() {
        List<Item> items = new ArrayList<>();
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    void testItemWithNullNameAndValidBarcode() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(null, "123456789", 10, 0));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    void testItemWithEmptyNameAndValidBarcode() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("", "123456789", 10, 0));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    void testItemWithValidNameAndNullBarcode() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", null, 10, 0));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items, 100);
        });
        assertEquals("No barcode!", exception.getMessage());
    }

    @Test
    void testItemWithInvalidBarcode() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "1234a6789", 10, 0));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items, 100);
        });
        assertEquals("Invalid character in item barcode!", exception.getMessage());
    }

    @Test
    void testItemWithDiscountAndValidBarcode() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "123456789", 10, 0.1f));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    void testItemWithAdditionalDiscount() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "0123456789", 310, 0.1f));
        assertTrue(SILab2.checkCart(items, 100));
    }

    @Test
    void testMultipleItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "0123456789", 310, 0.1f));
        items.add(new Item("item2", "123456789", 10, 0));
        items.add(new Item("", "987654321", 20, 0.2f));
        assertTrue(SILab2.checkCart(items, 150));
    }

    @Test
    void testSumGreaterThanPayment() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "123456789", 150, 0));
        items.add(new Item("item2", "987654321", 200, 0));
        assertFalse(SILab2.checkCart(items, 300));
    }
}
