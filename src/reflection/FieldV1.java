package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Field;

public class FieldV1 {

    public static void main(String[] args) {
        Class<BasicData> helloClass = BasicData.class;

        System.out.println("===== fields() =====");
        for (java.lang.reflect.Field field : helloClass.getFields()) {
            System.out.println("field = " + field);
        }

        System.out.println("===== declaredFields() =====");
        for (Field declaredField : helloClass.getDeclaredFields()) {
            System.out.println("declaredField = " + declaredField);
        }
    }
}
