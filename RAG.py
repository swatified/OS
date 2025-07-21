def display_rag(graph, processes, resources):
    """Display Resource Allocation Graph"""
    total_nodes = processes + resources
    print("Resource Allocation Graph:")
    
    for i in range(total_nodes):
        for j in range(total_nodes):
            if graph[i][j]:
                # Determine source and destination types
                if i < processes and j >= processes:
                    print(f"P{i} --> R{j - processes}")  # Process to Resource (Request)
                elif i >= processes and j < processes:
                    print(f"R{i - processes} --> P{j}")  # Resource to Process (Allocation)

def main():
    processes = 3
    resources = 2
    total_nodes = processes + resources
    
    # Create adjacency matrix (all zeros initially)
    rag = [[0 for _ in range(total_nodes)] for _ in range(total_nodes)]
    
    # Add edges to represent RAG
    # Node indices: P0=0, P1=1, P2=2, R0=3, R1=4
    
    rag[0][3] = 1  # P0 requests R0
    rag[1][4] = 1  # P1 requests R1
    rag[4][2] = 1  # R1 allocated to P2
    rag[3][0] = 1  # R0 allocated to P0
    
    display_rag(rag, processes, resources)

# Run the program
main()