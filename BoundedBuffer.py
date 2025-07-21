import threading
import time
import queue
from collections import deque

class BoundedBuffer:
    def __init__(self, size):
        self.size = size
        self.buffer = deque()
        self.mutex = threading.Lock()
        self.not_full = threading.Condition(self.mutex)   # Signal when buffer not full
        self.not_empty = threading.Condition(self.mutex) # Signal when buffer not empty
    
    def produce(self, item):
        with self.not_full:
            # Wait until buffer is not full
            while len(self.buffer) >= self.size:
                self.not_full.wait()
            
            # Add item to buffer
            self.buffer.append(item)
            print(f"Produced: {item}")
            
            # Notify consumer that buffer is not empty
            self.not_empty.notify()
    
    def consume(self):
        with self.not_empty:
            # Wait until buffer is not empty
            while len(self.buffer) == 0:
                self.not_empty.wait()
            
            # Remove item from buffer
            item = self.buffer.popleft()
            print(f"Consumed: {item}")
            
            # Notify producer that buffer is not full
            self.not_full.notify()
            
            return item

def producer(buffer, items):
    for i in range(items):
        buffer.produce(i)
        time.sleep(0.1)  # Simulate production time

def consumer(buffer, items):
    for i in range(items):
        buffer.consume()
        time.sleep(0.15)  # Simulate consumption time

def main():
    # Create bounded buffer with size 5
    SIZE = 5
    buffer = BoundedBuffer(SIZE)
    
    # Create producer and consumer threads
    producer_thread = threading.Thread(target=producer, args=(buffer, 10))
    consumer_thread = threading.Thread(target=consumer, args=(buffer, 10))
    
    print(f"Buffer Size: {SIZE}")
    print("Starting Producer and Consumer...\n")
    
    # Start both threads
    producer_thread.start()
    consumer_thread.start()
    
    # Wait for both threads to complete
    producer_thread.join()
    consumer_thread.join()
    
    print("\nAll items produced and consumed!")

# Run the program
if __name__ == "__main__":
    main()