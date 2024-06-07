class Main {
    public static void main(String[] args) {
        int weights[] = {3, 1, 6, 10, 1, 4, 9, 1, 7, 2, 6, 1, 6, 2, 2, 4, 8, 1, 7, 3, 6, 2, 9, 5, 3, 3, 4, 7, 3, 5, 30, 50};
        int values[] = {7, 4, 9, 18, 9, 15, 4, 2, 6, 13, 18, 12, 12, 16, 19, 19, 10, 16, 14, 3, 14, 4, 15, 7, 5, 10, 10, 13, 19, 9, 8, 5};
        int size = 32;
        int W_max = 75;
        long n = (long) Math.pow(2, size);
        int b_w = 0;
        int b_v = 0;
        long b_in = 0;

        for (long i = 0; i < n; i++) {
            int w = 0;
            int v = 0;
            for (int j = 0; j < size; j++) {
                if ((i & (1L << j)) != 0) {
                    w += weights[j];
                    v += values[j];
                    if (w > W_max) {
                        break;
                    }
                }
            }
            if (w <= W_max && (v > b_v || (v == b_v && w < b_w))) {
                b_w = w;
                b_v = v;
                b_in = i;
            }
        }

        System.out.println(b_w + " " + b_v + " " + Long.toBinaryString(b_in));
    }
}
