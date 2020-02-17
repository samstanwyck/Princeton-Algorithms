import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String s;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }

    }
}
