def first_fit(block_size, process_size):
    m, n = len(block_size), len(process_size)
    allocation = [-1] * n  # -1 means not allocated
    
    for i in range(n):  # For each process
        for j in range(m):  # Check each block
            if block_size[j] >= process_size[i]:
                allocation[i] = j
                block_size[j] -= process_size[i]
                break  # Take first suitable block
    
    print("First Fit Allocation:")
    for i in range(n):
        if allocation[i] != -1:
            print(f"Process {i+1}: Block {allocation[i]+1}")
        else:
            print(f"Process {i+1}: Not Allocated")

def best_fit(block_size, process_size):
    m, n = len(block_size), len(process_size)
    allocation = [-1] * n
    
    for i in range(n):  # For each process
        best_idx = -1  # Track smallest suitable block
        for j in range(m):
            if block_size[j] >= process_size[i]:
                # Find smallest block that fits
                if best_idx == -1 or block_size[j] < block_size[best_idx]:
                    best_idx = j
        
        if best_idx != -1:
            allocation[i] = best_idx
            block_size[best_idx] -= process_size[i]
    
    print("\nBest Fit Allocation:")
    for i in range(n):
        if allocation[i] != -1:
            print(f"Process {i+1}: Block {allocation[i]+1}")
        else:
            print(f"Process {i+1}: Not Allocated")

def worst_fit(block_size, process_size):
    m, n = len(block_size), len(process_size)
    allocation = [-1] * n
    
    for i in range(n):  # For each process
        worst_idx = -1  # Track largest suitable block
        for j in range(m):
            if block_size[j] >= process_size[i]:
                # Find largest block that fits
                if worst_idx == -1 or block_size[j] > block_size[worst_idx]:
                    worst_idx = j
        
        if worst_idx != -1:
            allocation[i] = worst_idx
            block_size[worst_idx] -= process_size[i]
    
    print("\nWorst Fit Allocation:")
    for i in range(n):
        if allocation[i] != -1:
            print(f"Process {i+1}: Block {allocation[i]+1}")
        else:
            print(f"Process {i+1}: Not Allocated")

# Main execution
block_size = [100, 500, 200, 300, 600]
process_size = [212, 417, 112, 426]

# Create copies for each algorithm (each will modify block sizes)
block1 = block_size.copy()
block2 = block_size.copy()
block3 = block_size.copy()

first_fit(block1, process_size)
best_fit(block2, process_size)
worst_fit(block3, process_size)