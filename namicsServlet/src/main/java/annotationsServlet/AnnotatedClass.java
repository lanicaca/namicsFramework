package annotationsServlet;

import lombok.Data;

@Data
class AnnotatedClass {
    private String path;
    private String selector;
    private String method;
    private String returns;
    private Class<?> myClass;

    AnnotatedClass(String path, String selector, String method, String returns, Class<?> myClass) {
        this.path = path;
        this.selector = selector;
        this.method = method;
        this.returns = returns;
        this.myClass = myClass;
    }

}
