package html;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{

        Element html =
                new Html(
                    new Body(
                            new Ul(
                                    new Li("Item 1"),
                                    new Li("Item 1")
                            )
                    )
                );

        html.writeOn(System.out);
    }
}
