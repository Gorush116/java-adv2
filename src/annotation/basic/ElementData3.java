package annotation.basic;

import java.util.Arrays;

@AnnoElement("data")
public class ElementData3 {

    public static void main(String[] args) {
        AnnoElement annotation = ElementData3.class.getAnnotation(AnnoElement.class);

        String value = annotation.value();
        System.out.println("value = " + value);

        int count = annotation.count();
        System.out.println("count = " + count);

        String[] tags = annotation.tags();
        System.out.println("tags = " + Arrays.toString(tags));
    }
}
