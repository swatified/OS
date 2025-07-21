# Input memory blocks
n = int(input("Enter number of memory blocks: "))
blocks = []
print("Enter size of each block:")
for i in range(n):
    size = int(input(f"Block {i+1}: "))
    blocks.append(size)

# Input processes
m = int(input("Enter number of processes: "))
processes = []
print("Enter size of each process:")
for i in range(m):
    size = int(input(f"Process {i+1}: "))
    processes.append(size)

# Calculate fragmentation
internal_frag = 0
total_mem = 0
used_mem = 0

# Allocate processes to blocks (one-to-one mapping)
for i in range(min(n, m)):  # Process only available pairs
    if blocks[i] >= processes[i]:  # Process fits in block
        internal_frag += (blocks[i] - processes[i])  # Wasted space in block
        used_mem += processes[i]  # Memory actually used by process
    total_mem += blocks[i]  # Add to total available memory

# External fragmentation = unallocated memory
external_frag = total_mem - used_mem - internal_frag

# Output results
print(f"Internal Fragmentation: {internal_frag} KB")
print(f"External Fragmentation: {external_frag} KB")