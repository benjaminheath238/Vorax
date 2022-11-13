package com.vorax.extern;

import lombok.Data;

@Data
public class ItemStack {
    private Item item;
    private int quantity;

    @Override
    public boolean equals(Object obj) {
        return (obj != null)
                && (obj == this)
                || (obj instanceof ItemStack && obj.hashCode() == this.hashCode());
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }
}
