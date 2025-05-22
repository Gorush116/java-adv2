package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Method;

public class MethodV1 {

    public static void main(String[] args) {
        Class<BasicData> helloClass = BasicData.class;

        System.out.println("===== methods() =====");
        Method[] methods = helloClass.getMethods(); // public method만 출력됨
        for (Method method : methods) {
            System.out.println("method = " + method);
        }

        System.out.println("===== declaredMethods() =====");
        for (Method declaredMethod : helloClass.getDeclaredMethods()) { // 내가 선언한 클래스
            System.out.println("declaredMethod = " + declaredMethod);
        }
    }
}
