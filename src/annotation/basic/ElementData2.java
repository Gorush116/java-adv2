package annotation.basic;

import java.util.Arrays;

@AnnoElement(value = "data", tags = "t1")
public class ElementData2 {

    public static void main(String[] args) {
        Class<ElementData2> annoClass = ElementData2.class;
        AnnoElement annotation = annoClass.getAnnotation(AnnoElement.class);

        String value = annotation.value();
        System.out.println("value = " + value);

        int count = annotation.count();
        System.out.println("count = " + count);

        String[] tags = annotation.tags();
        System.out.println("tags = " + Arrays.toString(tags));
    }
}
