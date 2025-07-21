def bankers_algorithm():
    # Input data
    P = 5  # Number of processes
    R = 4  # Number of resource types
    
    # Allocation matrix - currently allocated resources to each process
    alloc = [
        [2,0,0,1],
        [3,1,2,1],
        [2,1,0,3],
        [1,3,1,2],
        [1,4,3,2]
    ]
    
    # Maximum demand matrix - max resources each process may need
    max_demand = [
        [4,2,1,2],
        [5,2,5,2],
        [2,3,1,6],
        [1,4,2,4],
        [3,6,6,5]
    ]
    
    # Available resources - currently available instances of each resource
    avail = [3, 3, 2, 1] 

    # Step 1: Calculate Need matrix (Need = Max - Allocation)
    need = []
    for i in range(P):
        process_need = []
        for j in range(R):
            process_need.append(max_demand[i][j] - alloc[i][j])
        need.append(process_need)
    
    print("Need Matrix:")
    for i in range(P):
        print(f"P{i}: {need[i]}")
    print()
    
    # Step 2: Initialize variables for algorithm
    finish = [False] * P  # Track which processes are finished
    safe_seq = []         # Store safe sequence
    work = avail.copy()   # Working copy of available resources
    
    # Step 3: Main algorithm loop
    while len(safe_seq) < P:
        found = False
        
        # Try to find a process that can be safely executed
        for p in range(P):
            if not finish[p]:  # Process not yet finished
                # Check if process can be allocated (Need <= Work)
                can_allocate = True
                for r in range(R):
                    if need[p][r] > work[r]:
                        can_allocate = False
                        break
                
                # If process can be safely executed
                if can_allocate:
                    # Add allocated resources back to work (simulate process completion)
                    for k in range(R):
                        work[k] += alloc[p][k]
                    
                    # Mark process as finished and add to safe sequence
                    safe_seq.append(p)
                    finish[p] = True
                    found = True
                    print(f"Process P{p} can be safely executed. Avail becomes: {work}")
                    break
        
        # If no process can be executed, system is in unsafe state
        if not found:
            print("System is not in safe state.")
            return False
    
    # System is in safe state
    print(f"\nSystem is in safe state.")
    print("Safe sequence:", end=" ")
    for i, process in enumerate(safe_seq):
        print(f"P{process}", end="")
        if i < len(safe_seq) - 1:
            print(" -> ", end="")
    print()
    return True

# Run the algorithm
bankers_algorithm()