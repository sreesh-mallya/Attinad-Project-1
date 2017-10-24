package com.sreeshmallya.banking;

import java.util.UUID;

class HashCodeGenerator {
    public static int generateHash(int digits) {
        int id = UUID.randomUUID().hashCode();
        if (id < 0) id = id * -1;
        while(Integer.toString(id).length() != digits) id = id / 10;
        return id;
    }
}
