package team5.explodingkittens.view;

public class WrapperClasses {
    public static class SizeObject {
        public final double width;
        public final double height;

        public SizeObject(double width, double height) {
            this.width = width;
            this.height = height;
        }
    }

    public static class SelectInfo {
        public final String title;
        public final String header;
        public final String content;

        public SelectInfo(String title, String header, String content) {
            this.title = title;
            this.header = header;
            this.content = content;
        }
    }
}
