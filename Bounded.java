import java.util.LinkedList;
import java.util.Queue;

class BoundedBuffer {
    private final int SIZE = 5;
    private Queue<Integer> buffer;
    
    public BoundedBuffer() {
        buffer = new LinkedList<>();
    }
    
    // Producer method - adds item to buffer
    public synchronized void produce(int item) throws InterruptedException {
        // Wait while buffer is full
        while (buffer.size() >= SIZE) {
            wait(); // Release lock and wait for consumer to consume
        }
        
        // Add item to buffer
        buffer.add(item);
        System.out.println("Produced: " + item);
        
        // Notify waiting consumer
        notifyAll();
    }
    
    // Consumer method - removes item from buffer
    public synchronized int consume() throws InterruptedException {
        // Wait while buffer is empty
        while (buffer.isEmpty()) {
            wait(); // Release lock and wait for producer to produce
        }
        
        // Remove item from buffer
        int item = buffer.poll();
        System.out.println("Consumed: " + item);
        
        // Notify waiting producer
        notifyAll();
        
        return item;
    }
}

class Producer extends Thread {
    private BoundedBuffer buffer;
    private int items;
    
    public Producer(BoundedBuffer buffer, int items) {
        this.buffer = buffer;
        this.items = items;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < items; i++) {
                buffer.produce(i);
                Thread.sleep(100); // Simulate production time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread {
    private BoundedBuffer buffer;
    private int items;
    
    public Consumer(BoundedBuffer buffer, int items) {
        this.buffer = buffer;
        this.items = items;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < items; i++) {
                buffer.consume();
                Thread.sleep(150); // Simulate consumption time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Bounded {
    public static void main(String[] args) {
        System.out.println("Buffer Size: 5");
        System.out.println("Starting Producer and Consumer...\n");
        
        // Create shared buffer
        BoundedBuffer buffer = new BoundedBuffer();
        
        // Create producer and consumer threads
        Producer producer = new Producer(buffer, 10);
        Consumer consumer = new Consumer(buffer, 10);
        
        // Start both threads
        producer.start();
        consumer.start();
        
        try {
            // Wait for both threads to complete
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nAll items produced and consumed!");
    }
}