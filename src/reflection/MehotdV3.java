package reflection;

import reflection.data.CalCulator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class MehotdV3 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("호출 메서드 : ");
        String methodName = scanner.nextLine();

        System.out.println("숫자1 : ");
        int num1 = scanner.nextInt();
        System.out.println("숫자2 : ");
        int num2 = scanner.nextInt();

        CalCulator calCulator = new CalCulator();
        // 호출할 메서드를 변수 이름으로 동적 선택
        Class<? extends CalCulator> aClass = calCulator.getClass();
        Method method = aClass.getMethod(methodName, int.class, int.class);

        Object returnValue = method.invoke(calCulator, num1, num2);
        System.out.println("returnValue = " + returnValue);
    }
}
