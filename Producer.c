#include <stdio.h>

int i = 0, E = 5, S = 1, F = 0;
int buffer[5], count = 0;

void wait(int *sem) {
    while (*sem <= 0);  // Busy wait
    (*sem)--;
}

void signal(int *sem) {
    (*sem)++;
}

void Producer() {
    i++;
    wait(&E);           // Pass address, can modify original
    wait(&S);
    buffer[count++] = i;
    printf("Produced: %d\n", i);
    signal(&S);
    signal(&F);
}

void Consumer() {
    wait(&F);
    wait(&S);
    int item = buffer[--count];
    printf("Consumed: %d\n", item);
    signal(&S);
    signal(&E);
}

int main() {
    for(int x = 0; x < 5; x++) Producer();
    for(int x = 0; x < 2; x++) Consumer();
    for(int x = 0; x < 2; x++) Producer();
    for(int x = 0; x < 5; x++) Consumer();
    return 0;
}