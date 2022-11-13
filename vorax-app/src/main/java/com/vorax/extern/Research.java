package com.vorax.extern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Research {
    private boolean unlocked;
    private List<Research> prerequisites;
    private Map<Item, Integer> requirements;

    public Research() {
        this.unlocked = false;
        this.prerequisites = new ArrayList<>();
        this.requirements = new HashMap<>();
    }

    public void dependant(Research research) {
        prerequisites.add(research);
    }

    public void require(ItemStack stack) {
        requirements.put(stack.getItem(), stack.getQuantity());
    }

    public void satisfy(ItemStack stack) {
        if (!requirements.containsKey(stack.getItem())) {
            return;
        }

    }
}
